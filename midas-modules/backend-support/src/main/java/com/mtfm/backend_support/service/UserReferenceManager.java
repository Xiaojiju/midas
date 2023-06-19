package com.mtfm.backend_support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtfm.backend_support.entity.SolarUserReference;

public interface UserReferenceManager extends IService<SolarUserReference> {

    void removeUser(String userId);

    SolarUserReference getByReferenceKey(String referenceKey, String identifier);
}
