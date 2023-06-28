package com.mtfm.app_support.service.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.app_support.entity.AppAccount;
import com.mtfm.app_support.mapper.AppUserMapper;
import com.mtfm.app_support.service.AppUserAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AppUserAccountServiceImpl extends ServiceImpl<AppUserMapper, AppAccount> implements AppUserAccountService {
}
