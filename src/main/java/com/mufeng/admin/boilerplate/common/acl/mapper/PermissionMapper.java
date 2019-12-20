package com.mufeng.admin.boilerplate.common.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author HuangTianyu
 * @Date 2019-11-30 16:38
 * @Version 1.0
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
