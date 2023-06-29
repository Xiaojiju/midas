package com.mtfm.app_support.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.app_support.entity.AppUserBaseInfo;
import com.mtfm.app_support.mapper.AppUserBaseInfoMapper;
import com.mtfm.app_support.service.AppUserBaseInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AppUserBaseInfoServiceImpl extends ServiceImpl<AppUserBaseInfoMapper, AppUserBaseInfo> implements AppUserBaseInfoService {

    @Override
    public AppUserBaseInfo getByUserId(String userId) {
        return this.getOne(new QueryWrapper<AppUserBaseInfo>().lambda()
                .eq(AppUserBaseInfo::getUserId, userId));
    }
}
