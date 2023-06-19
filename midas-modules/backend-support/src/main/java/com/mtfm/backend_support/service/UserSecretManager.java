package com.mtfm.backend_support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtfm.backend_support.entity.SolarSecret;

public interface UserSecretManager extends IService<SolarSecret> {

    void removeUser(String userId);

    SolarSecret getOne(String userId);

}
