package com.mapper;

import com.entity.Comment;

import java.util.List;

/**
 * <p>
 *  评论 Mapper 接口
 * </p>
 *
 * @author hlt
 * @since 2019-12-21
 */
public interface CommentMapper {
    /**插入评论*/
    Integer insertComment(Comment comment);
    /**查询评论*/
    List<Comment> queryComments(String commid);
    /**查询评论中用户信息*/
    Comment queryById(String cid);
    /**删除评论*/
    Integer deleteComment(String cid);
}
