package com.mtfm.backend_support.provisioning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.backend_support.entity.SolarUsername;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsernameMapper extends BaseMapper<SolarUsername> {

    SolarUsername selectUsername(String username, String identifier);

}
