package tech.mufeng.admin.boilerplate.user.model.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.mufeng.admin.boilerplate.common.base.model.BaseQueryParam;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserListQueryParam extends BaseQueryParam {
    private Long uid;

    private String usernameLike;

    private Boolean active;

    @NotNull
    private Long page;

    @NotNull
    private Long size;
}
