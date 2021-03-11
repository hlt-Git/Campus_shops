package com.service;

import com.entity.UserRole;
import com.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hlt
 * @since 2019-12-21
 */
@Service
@Transactional
public class UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**插入角色*/
    public Integer InsertUserRole(UserRole userRole){
        return userRoleMapper.InsertUserRole(userRole);
    }
    /**查询角色id*/
    public Integer LookUserRoleId(String userid){
        return userRoleMapper.LookUserRoleId(userid);
    }
    /**修改用户的角色*/
    public void UpdateUserRole(UserRole userRole){
        userRoleMapper.UpdateUserRole(userRole);
    }
}
