package com.mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hlt
 * @since 2019-12-21
 */
public interface UserPermMapper {
    /**查询用户的权限*/
    List<String> LookPermsByUserid(Integer roleid);
}
