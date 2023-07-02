package com.mtfm.app_support.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.app_support.entity.AppAccount;
import com.mtfm.security.AppUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppUserMapper extends BaseMapper<AppAccount> {
    /**
     * 查询用户详情
     * @param referenceKey 用户关系key
     * @return {@link UserDto} 用户详细信息
     */
    AppUser selectUserByReferenceKey(@Param("referenceKey") String referenceKey);
}
