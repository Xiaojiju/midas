package com.mtfm.backend_support.service.secret;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.backend_support.entity.SolarSecret;
import com.mtfm.backend_support.service.UserSecretManager;
import com.mtfm.backend_support.service.mapper.SecretMapper;

public class UserSecretManagerService extends ServiceImpl<SecretMapper, SolarSecret> implements UserSecretManager {

    @Override
    public void removeUser(String userId) {
        QueryWrapper<SolarSecret> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SolarSecret::getuId, userId);
        this.remove(queryWrapper);
    }

    @Override
    public SolarSecret getOne(String userId) {
        return this.getOne(new QueryWrapper<SolarSecret>()
                .lambda()
                .eq(SolarSecret::getuId, userId));
    }
}
