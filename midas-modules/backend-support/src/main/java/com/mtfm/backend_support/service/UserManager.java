package com.mtfm.backend_support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtfm.backend_support.entity.SolarUser;
import com.mtfm.backend_support.service.provisioning.UserInformation;
import com.mtfm.backend_support.service.query.ValuePageQuery;
import com.mtfm.core.util.page.PageTemplate;

public interface UserManager extends IService<SolarUser> {

    UserInformation getInformation(String userId);

    PageTemplate<UserInformation> pageList(ValuePageQuery query);

}
