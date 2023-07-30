package com.mtfm.backend_support.provisioning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.backend_support.entity.SolarSecret;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserSecretMapper extends BaseMapper<SolarSecret> {

    SolarSecret selectSecretByUserId(String userId);

}
