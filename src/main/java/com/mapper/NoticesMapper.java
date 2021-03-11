package com.mapper;

import com.entity.Notices;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  消息通知Mapper 接口
 * </p>
 *
 * @author hlt
 * @since 2019-12-25
 */
public interface NoticesMapper {
    /**发出通知消息*/
    Integer insertNotices(Notices notices);
    /**用户已读通知消息*/
    Integer updateNoticesById(String id);
    /**查询前10条通知*/
    List<Notices> queryNotices(String userid);
    /**取消新通知标志*/
    Integer CancelLatest(String userid);
    /**分页查询用户所有通知消息*/
    List<Notices> queryAllNotices(@Param("page") Integer page, @Param("count") Integer count, @Param("userid") String userid);
    /**查询用户所有通知消息的数量*/
    Integer queryNoticesCount(@Param("userid") String userid);
}
