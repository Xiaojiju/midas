package com.mtfm.backend_support.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.backend_support.entity.SolarBaseInfo;
import com.mtfm.backend_support.service.UserBaseInfoManager;
import com.mtfm.backend_support.service.mapper.UserBaseInfoMapper;

public class UserBaseInfoManagerService extends ServiceImpl<UserBaseInfoMapper, SolarBaseInfo> implements UserBaseInfoManager {

    @Override
    public SolarBaseInfo getByUserId(String userId) {
        QueryWrapper<SolarBaseInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SolarBaseInfo::getuId, userId);
        return this.getOne(queryWrapper);
    }
}
