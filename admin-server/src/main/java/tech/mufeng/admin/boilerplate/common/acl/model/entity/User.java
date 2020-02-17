package tech.mufeng.admin.boilerplate.common.acl.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.mufeng.admin.boilerplate.common.model.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseTimeEntity {
    private Long uid;

    private String username;

    private String password;

    private Boolean active;

    private String lastIp;

    private LocalDateTime lastLogin;
}
