package com.mtfm.backend_support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtfm.backend_support.entity.SolarBaseInfo;

public interface UserBaseInfoManager extends IService<SolarBaseInfo> {

    SolarBaseInfo getByUserId(String userId);
}
