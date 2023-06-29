package com.mtfm.app_support.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.app_support.entity.AppUserReference;
import com.mtfm.app_support.mapper.AppUserReferenceMapper;
import com.mtfm.app_support.service.AppUserReferenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional(rollbackFor = Exception.class)
public class AppUserReferenceServiceImpl extends ServiceImpl<AppUserReferenceMapper, AppUserReference> implements AppUserReferenceService {

    @Override
    public AppUserReference getOneByUsername(String username) {
        return this.getOne(
                new QueryWrapper<AppUserReference>().lambda().eq(AppUserReference::getUsername, username)
        );
    }

    @Override
    public AppUserReference getOneByUserIdentifier(@NotNull String userId,@NotNull String identifier) {
        return this.getOne(
                new QueryWrapper<AppUserReference>().lambda()
                        .eq(AppUserReference::getUserId, userId)
                        .eq(AppUserReference::getIdentifier, identifier)
        );
    }
}
