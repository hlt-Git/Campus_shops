package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 新闻id
     */
	private String id;
    /**
     * 新闻标题
     */
	private String newstitle;
    /**
     * 新闻简介
     */
	private String newsdesc;
    /**
     * 新闻内容
     */
	private String newscontent;
    /**
     * 发布时间
     */
	private Date createtime;
    /**
     * 新闻发布者
     */
	private String username;
    /**
     * 图片
     */
    private String image;
    /**
     * 1正常  2删除
     */
	private Integer newsstatus;
    /**
     * 浏览量
     */
	private Integer rednumber;


}
