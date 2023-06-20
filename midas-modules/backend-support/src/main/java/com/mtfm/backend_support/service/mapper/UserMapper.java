package com.mtfm.backend_support.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtfm.backend_support.entity.SolarUser;
import com.mtfm.backend_support.service.provisioning.UserInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<SolarUser> {

    Page<UserInformation> selectUsers(IPage<UserInformation> page, @Param("username") String username);

    UserInformation selectInformation(@Param("userId") String userId);

}
