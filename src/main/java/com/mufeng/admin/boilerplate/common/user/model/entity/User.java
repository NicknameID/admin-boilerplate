package com.mufeng.admin.boilerplate.common.user.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mufeng.admin.boilerplate.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 17:14
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements Serializable {

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 是否激活
     */
    private Boolean active;

    /**
     * 最后登录ip
     */
    private String lastIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLogin;

    /**
     * 最后使用的token
     */
    @JsonIgnore
    private String lastToken;
}