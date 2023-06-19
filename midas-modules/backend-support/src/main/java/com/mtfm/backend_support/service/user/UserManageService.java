package com.mtfm.backend_support.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtfm.backend_support.entity.SolarSecret;
import com.mtfm.backend_support.entity.SolarUser;
import com.mtfm.backend_support.entity.SolarUserReference;
import com.mtfm.backend_support.service.UserRoleManager;
import com.mtfm.backend_support.service.mapper.SecretMapper;
import com.mtfm.backend_support.service.mapper.UserMapper;
import com.mtfm.backend_support.service.mapper.UserReferenceMapper;
import com.mtfm.backend_support.service.provisioning.UserDetailSample;
import com.mtfm.backend_support.service.provisioning.UserSample;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.security.SecurityCode;
import com.mtfm.tools.enums.Judge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collection;

@Transactional(rollbackFor = Exception.class)
public class UserManageService implements UserDetailsManager, InitializingBean, MessageSourceAware {

    private static final String DEFAULT_PASSWORD = "midas888888";
    private static final Logger logger = LoggerFactory.getLogger(UserManageService.class);
    private UserDetailsService userDetailsService;
    private UserReferenceMapper userReferenceMapper;
    private SecretMapper secretMapper;
    private UserMapper userMapper;
    private UserRoleManager userRoleManager;
    private MessageSourceAccessor messageSource;

    public UserManageService(UserDetailsService userDetailsService,
                             UserReferenceMapper userReferenceMapper,
                             SecretMapper secretMapper,
                             UserMapper userMapper,
                             UserRoleManager userRoleManager) {
        this.userDetailsService = userDetailsService;
        this.userReferenceMapper = userReferenceMapper;
        this.secretMapper = secretMapper;
        this.userMapper = userMapper;
        this.userRoleManager = userRoleManager;
    }

    @Override
    public void createUser(UserDetails user) {
        Assert.isInstanceOf(UserSample.class, user, "parameter class must extends UserSample.class");
        UserSample userSample = ( UserSample ) user;
        String username = userSample.getUsername();
        LocalDateTime expiredTime = userSample.getExpiredTime();
        SolarUser solarUser = createUser(username, expiredTime);
        SolarUserReference.UserReferenceBuilder userReferenceBuilder = SolarUserReference.withUId(solarUser.getId());
        SolarUserReference userReference = userReferenceBuilder.withReferenceKey(username)
                .allowable(Judge.YES)
                .identifyBy("DEFAULT")
                .build();
        this.userReferenceMapper.insert(userReference);
        SolarSecret secret = SolarSecret.builder(solarUser.getId())
                .makeItSecret(StringUtils.hasText(userSample.getPassword()) ? userSample.getPassword() : DEFAULT_PASSWORD,
                        null, PasswordEncoderFactories.createDelegatingPasswordEncoder())
                .build();
        this.secretMapper.insert(secret);
        this.userRoleManager.modifyRoles(solarUser.getId(), userSample.getAuthorities());
    }

    /**
     * 修改用户信息，该方法主要偏向于具有修改用户权限的用户进行修改其他用户的信息，但是不能用于修改认证方式的校验情况
     * {@link SolarUserReference#getValidated()}以及{@link SolarUserReference#getIdentifier()}，因为identifier在
     * 创建用户的时候就初始化为DEFAULT。其也作为用户默认用户名，其他的认证方式都为副认证方式；
     * {@link UserDetailSample#getAuthorities()}该方式还可以进行修改用户关联角色
     * @param user 此参数{@link UserDetailSample}必须是继承或其本身
     */
    @Override
    public void updateUser(UserDetails user) {
        Assert.isInstanceOf(UserDetailSample.class, user,"parameter class must extends UserDetailSample.class");
        UserDetailSample sample = (UserDetailSample) user;
        String identifier = sample.getIdentifier();
        if (!StringUtils.hasText(identifier)) {
            identifier = "DEFAULT";
        }
        SolarUser solarUser = this.userMapper.selectById(sample.getId());
        if (solarUser == null) {
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.nonExist"),
                    SecurityCode.USER_NOT_FOUND.getCode());
        }
        solarUser.setLocked(sample.getLocked());
        this.userMapper.updateById(solarUser);
        SolarUserReference userReference = loadUser(user.getUsername(), identifier);
        if (userReference != null && !userReference.getuId().equals(sample.getId())) {
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.hadExist"),
                    SecurityCode.USERNAME_EXIST.getCode());
        }
        SolarUserReference solarUserReference = SolarUserReference.withUId(sample.getId())
                .withReferenceKey(sample.getUsername()).build();
        this.userReferenceMapper.updateById(solarUserReference);

        if (StringUtils.hasText(sample.getPassword())) {
            SolarSecret secret = SolarSecret.builder(sample.getId())
                    .makeItSecret(sample.getPassword(), null, PasswordEncoderFactories.createDelegatingPasswordEncoder())
                    .build();
            this.secretMapper.updateById(secret);
        }
        Collection<? extends GrantedAuthority> authorities = sample.getAuthorities();
        if (authorities != null) {
            this.userRoleManager.modifyRoles(sample.getId(), sample.getAuthorities());
        }
    }

    @Override
    public void deleteUser(String username) {
        SolarUser solarUser = this.userMapper.selectById(username);
        if (solarUser == null || solarUser.getDeleted() == Judge.YES.getCode()) {
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.nonExist"),
                    SecurityCode.USER_NOT_FOUND.getCode());
        }
        this.userMapper.deleteById(username);
        this.userRoleManager.modifyRoles(username, null);
        QueryWrapper<SolarUserReference> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SolarUserReference::getuId, username);
        this.userReferenceMapper.delete(queryWrapper);
        this.secretMapper.delete(new QueryWrapper<SolarSecret>().lambda()
                .eq(SolarSecret::getuId, username));
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String self = (String) authentication.getPrincipal();
        // 优先校验旧密码是否正确
        SolarSecret solarSecret = this.secretMapper.selectOne(
                new QueryWrapper<SolarSecret>().lambda().eq(SolarSecret::getuId, self));
        if (solarSecret == null) {
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.nonExist"),
                    SecurityCode.USER_NOT_FOUND.getCode());
        }
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (passwordEncoder.matches(oldPassword, solarSecret.getSecret())) {
            solarSecret.setSecret(passwordEncoder.encode(newPassword));
            this.secretMapper.updateById(solarSecret);
            return ;
        }
        throw new ServiceException(this.messageSource.getMessage("ReturnResponseAuthenticationFailHandler.bad_credential"),
                SecurityCode.BAD_CREDENTIAL.getCode());
    }

    @Override
    public boolean userExists(String username) {
        QueryWrapper<SolarUserReference> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SolarUserReference::getReferenceKey, username)
                .eq(SolarUserReference::getDeleted, Judge.NO);
        SolarUserReference userReference = this.userReferenceMapper.selectOne(queryWrapper);
        return userReference != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userDetailsService.loadUserByUsername(username);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messageSource, "message source could not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

    protected SolarUser createUser(String username, LocalDateTime expiredTime) {
        SolarUserReference userReference = loadUser(username, "DEFAULT");
        if (userReference != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("user reference key {} had exist", username);
            }
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.hadExist"),
                    SecurityCode.USERNAME_EXIST.getCode());
        }
        SolarUser solarUser = SolarUser.expiredUser(expiredTime);
        this.userMapper.insert(solarUser);
        return solarUser;
    }

    private SolarUserReference loadUser(String username, String identifier) {
        QueryWrapper<SolarUserReference> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SolarUserReference::getReferenceKey, username)
                .eq(StringUtils.hasText(identifier), SolarUserReference::getIdentifier, identifier)
                .eq(SolarUserReference::getDeleted, Judge.NO);
        return this.userReferenceMapper.selectOne(queryWrapper);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserReferenceMapper getUserReferenceMapper() {
        return userReferenceMapper;
    }

    public void setUserReferenceMapper(UserReferenceMapper userReferenceMapper) {
        this.userReferenceMapper = userReferenceMapper;
    }

    public SecretMapper getSecretMapper() {
        return secretMapper;
    }

    public void setSecretMapper(SecretMapper secretMapper) {
        this.secretMapper = secretMapper;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserRoleManager getUserRoleManager() {
        return userRoleManager;
    }

    public void setUserRoleManager(UserRoleManager userRoleManager) {
        this.userRoleManager = userRoleManager;
    }
}
