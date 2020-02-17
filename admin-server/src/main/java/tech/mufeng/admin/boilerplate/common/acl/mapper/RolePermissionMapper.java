package tech.mufeng.admin.boilerplate.common.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author HuangTianyu
 * @Date 2019-11-30 16:43
 * @Version 1.0
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
