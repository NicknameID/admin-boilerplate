package com.mufeng.admin.boilerplate.common.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mufeng.admin.boilerplate.common.user.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author HuangTianyu
 * @Date 2019-11-29 16:08
 * @Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
