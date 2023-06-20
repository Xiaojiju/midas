package com.mtfm.backend_support.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.backend_support.entity.SolarUserReference;
import com.mtfm.backend_support.service.UserReferenceManager;
import com.mtfm.backend_support.service.mapper.UserReferenceMapper;
import com.mtfm.tools.enums.Judge;
import org.springframework.util.StringUtils;

public class UserReferenceManagerService extends ServiceImpl<UserReferenceMapper, SolarUserReference> implements UserReferenceManager {

    @Override
    public void removeUser(String userId) {
        QueryWrapper<SolarUserReference> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SolarUserReference::getuId, userId);
        this.remove(queryWrapper);
    }

    @Override
    public SolarUserReference getByReferenceKey(String referenceKey, String identifier) {
        QueryWrapper<SolarUserReference> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SolarUserReference::getReferenceKey, referenceKey)
                .eq(SolarUserReference::getDeleted, Judge.NO)
                .eq(StringUtils.hasText(identifier), SolarUserReference::getIdentifier, identifier);
        return this.getOne(queryWrapper);
    }

    @Override
    public SolarUserReference getByUserId(String userId, String identifier) {
        QueryWrapper<SolarUserReference> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SolarUserReference::getuId, userId)
                .eq(SolarUserReference::getDeleted, Judge.NO)
                .eq(StringUtils.hasText(identifier), SolarUserReference::getIdentifier, identifier);
        return this.getOne(queryWrapper);
    }
}
