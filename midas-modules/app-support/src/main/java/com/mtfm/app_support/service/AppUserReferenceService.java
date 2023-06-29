package com.mtfm.app_support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtfm.app_support.entity.AppUserReference;

import javax.validation.constraints.NotNull;

public interface AppUserReferenceService extends IService<AppUserReference> {

    AppUserReference getOneByUsername(@NotNull String username);

    AppUserReference getOneByUserIdentifier(@NotNull String userId,@NotNull String identifier);
}
