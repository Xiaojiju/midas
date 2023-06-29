package com.mtfm.app_support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtfm.app_support.entity.AppUserSecret;

public interface AppUserSecretService extends IService<AppUserSecret> {
    /**
     * 获取当前用户的账号密码
     * @return 密码
     */
    AppUserSecret getByUserId();
}
