package com.mtfm.backend_support.provisioning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.backend_support.entity.SolarRouter;
import com.mtfm.backend_support.provisioning.authority.GrantedRouter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RouterMapper extends BaseMapper<SolarRouter> {

    List<GrantedRouter> selectRouters(@Param("group") String group);

    List<GrantedRouter> selectRoutersByUser(@Param("userId") String userId);
}
