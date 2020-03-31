package tech.mufeng.admin.boilerplate.common.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-11-30 16:38
 * @Version 1.0
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("SELECT\n" +
                "\tpermission.`code` AS `code`,\n" +
                "\tpermission.permission_name AS permissionName,\n" +
                "\tpermission.parent,\n" +
                "\tpermission.created_time AS createdTime,\n" +
                "\tpermission.updated_time AS updatedTime \n" +
            "FROM\n" +
                "\tuser_role\n" +
                "\tLEFT JOIN role_permission ON user_role.role_code = role_permission.role_code\n" +
                "\tLEFT JOIN permission ON permission.`code` = role_permission.permission_code \n" +
            "WHERE\n" +
                "\tuser_role.uid = #{uid} AND permission.`code` IS NOT NULL;")
    List<Permission> listByUid(@Param("uid") Long uid);
}
