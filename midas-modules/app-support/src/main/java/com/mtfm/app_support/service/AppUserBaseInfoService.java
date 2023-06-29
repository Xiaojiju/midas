package com.mtfm.app_support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtfm.app_support.entity.AppUserBaseInfo;

public interface AppUserBaseInfoService extends IService<AppUserBaseInfo> {

    AppUserBaseInfo getByUserId(String userId);
}
