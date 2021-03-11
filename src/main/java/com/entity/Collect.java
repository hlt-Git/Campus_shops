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
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收藏id
     */
    private String id;
    /**
     * 商品id
     */
    private String commid;
    /**
     * 商品名
     */
    private String commname;
    /**
     * 商品描述
     */
    private String commdesc;
    /**
     * 收藏时间
     */
    private Date soldtime;
    /**
     * 0失效 1正常 2删除
     */
    private Integer collstatus;
    /**
     * 商品用户id
     */
    private String cmuserid;
    /**
     * 商品用户名
     */
    private String username;
    /**
     * 商品所在学校
     */
    private String school;
    /**
     * 收藏用户id
     */
    private String couserid;
    /**
     * 收藏操作：收藏or取消收藏
     */
    private Integer colloperate;
}
