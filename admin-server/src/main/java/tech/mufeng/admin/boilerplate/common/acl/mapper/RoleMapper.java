package tech.mufeng.admin.boilerplate.common.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author HuangTianyu
 * @Date 2019-11-29 17:21
 * @Version 1.0
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
