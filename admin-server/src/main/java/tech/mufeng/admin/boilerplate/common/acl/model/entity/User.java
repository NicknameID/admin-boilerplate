package tech.mufeng.admin.boilerplate.common.acl.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.mufeng.admin.boilerplate.common.model.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseTimeEntity {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    private String username;

    @JsonIgnore
    private String password;

    private Boolean active;

    private String lastIp;

    private LocalDateTime lastLogin;
}
