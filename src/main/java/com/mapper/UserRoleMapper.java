package com.mapper;

import com.entity.UserRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hlt
 * @since 2019-12-21
 */
public interface UserRoleMapper {
    /**插入角色*/
    Integer InsertUserRole(UserRole userRole);
    /**查询角色id*/
    Integer LookUserRoleId(String userid);
    /**修改用户的角色*/
    void UpdateUserRole(UserRole userRole);
}
