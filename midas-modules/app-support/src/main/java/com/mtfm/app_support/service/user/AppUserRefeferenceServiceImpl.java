package com.mtfm.app_support.service.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.app_support.entity.AppUserReference;
import com.mtfm.app_support.mapper.AppUserReferenceMapper;
import com.mtfm.app_support.service.AppUserReferenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AppUserRefeferenceServiceImpl extends ServiceImpl<AppUserReferenceMapper, AppUserReference> implements AppUserReferenceService {
}
