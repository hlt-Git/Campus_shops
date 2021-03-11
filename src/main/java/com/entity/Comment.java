package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author hlt
 * @since 2019-12-25
 */
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
@Data
@Accessors(chain = true)//链式写法
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
	private String cid;
    /**
     * 商品id
     */
	private String commid;
    /**
     * 评论者id
     */
	private String cuserid;
    /**
     * 评论者昵称
     */
	private String cusername;
    /**
     * 评论者用户头像
     */
    private String cuimage;
    /**
     * 商品发布者id
     */
	private String spuserid;
    /**
     * 评论内容
     */
	private String content;
    /**
     * 评论时间
     */
	private Date commtime;
    /**
     * 0异常 1正常 2删除
     */
	private Integer commstatus;
    /**
     * 评论对应的回复集合
     */
    private List<Reply> replyLsit;
}
