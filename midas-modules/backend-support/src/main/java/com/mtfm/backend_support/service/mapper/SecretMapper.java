package com.mtfm.backend_support.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.backend_support.entity.SolarSecret;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecretMapper extends BaseMapper<SolarSecret> {
}
