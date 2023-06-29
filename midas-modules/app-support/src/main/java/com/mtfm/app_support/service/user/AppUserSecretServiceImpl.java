package com.mtfm.app_support.service.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.app_support.entity.AppUserSecret;
import com.mtfm.app_support.mapper.AppUserSecretMapper;
import com.mtfm.app_support.service.AppUserSecretService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AppUserSecretServiceImpl extends ServiceImpl<AppUserSecretMapper, AppUserSecret> implements AppUserSecretService {

    @Override
    public AppUserSecret getByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        return null;
    }
}
