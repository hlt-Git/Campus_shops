package com.mapper;

import com.entity.Reply;

import java.util.List;

/**
 * <p>
 *  评论回复 Mapper 接口
 * </p>
 *
 * @author hlt
 * @since 2019-12-21
 */
public interface ReplyMapper {
    /**插入回复*/
    Integer insetReply(Reply reply);
    /**查询回复*/
    List<Reply> queryReplys(String cid);
    /**查询回复中用户信息*/
    Reply queryById(String rid);
    /**删除回复*/
    Integer deleteReply(Reply reply);
}
