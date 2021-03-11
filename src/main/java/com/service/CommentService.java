package com.service;

import com.entity.Comment;
import com.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  评论 服务类
 * </p>
 *
 * @author hlt
 * @since 2019-12-21
 */
@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    /**插入评论*/
    public Integer insertComment(Comment comment){
        return commentMapper.insertComment(comment);
    }
    /**查询评论*/
    public List<Comment> queryComments(String commid){
        return commentMapper.queryComments(commid);
    }
    /**查询评论中用户信息*/
    public Comment queryById(String cid){
        return commentMapper.queryById(cid);
    }
    /**删除评论*/
    public Integer deleteComment(String cid){
        return commentMapper.deleteComment(cid);
    }
}
