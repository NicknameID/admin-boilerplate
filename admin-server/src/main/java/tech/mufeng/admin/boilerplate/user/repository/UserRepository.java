package tech.mufeng.admin.boilerplate.user.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tech.mufeng.admin.boilerplate.common.acl.mapper.UserMapper;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.User;
import tech.mufeng.admin.boilerplate.user.model.param.UserListQueryParam;

@Service
public class UserRepository extends ServiceImpl<UserMapper, User> {
    public IPage<User> getUserPage(UserListQueryParam q) {
        IPage<User> userPage = new Page<>(q.getPage(), q.getSize());
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery()
                .eq(q.getUid() != null, User::getUid, q.getUid())
                .likeRight(StringUtils.isNotEmpty(q.getUsernameLike()), User::getUsername, q.getUsernameLike())
                .eq(ObjectUtils.isNotEmpty(q.getActive()), User::getActive, q.getActive())
                .ge(q.getStartTime() != null, User::getCreatedTime, q.getStartTime())
                .lt(q.getEndTime() != null, User::getCreatedTime, q.getEndTime());
        boolean idDesc = BooleanUtils.toBooleanDefaultIfNull(q.getOrderByCreatedAtDesc(), false);
        if (idDesc) {
            queryWrapper.orderByDesc(User::getCreatedTime);
        }else {
            queryWrapper.orderByAsc(User::getCreatedTime);
        }
        return this.page(userPage, queryWrapper);
    }
}
