package com.mtfm.backend_support.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mtfm.core.convert.LocalDateTimeDeserializer;
import com.mtfm.core.util.TimeConstants;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "solar_group", autoResultMap = true)
public class SolarGroup implements GrantedAuthority, Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("group_name")
    private String groupName;

    @JsonFormat(pattern = TimeConstants.Y_M_D_H_M_S)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @TableField("create_time")
    private LocalDateTime createTime;

    public SolarGroup() {
    }

    public SolarGroup(Long id, String groupName, LocalDateTime createTime) {
        this.id = id;
        this.groupName = groupName;
        this.createTime = createTime;
    }

    public static SolarGroup withGroupName(String groupName) {
        return new SolarGroup(null, groupName, LocalDateTime.now());
    }

    @Override
    public String getAuthority() {
        return id.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
