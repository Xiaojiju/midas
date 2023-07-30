package com.mtfm.backend_support.provisioning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.backend_support.entity.SolarUserProfile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserProfileMapper extends BaseMapper<SolarUserProfile> {

    SolarUserProfile selectProfileByUsername(String username);

}
