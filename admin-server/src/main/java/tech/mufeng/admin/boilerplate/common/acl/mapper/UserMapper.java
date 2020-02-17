package tech.mufeng.admin.boilerplate.common.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
