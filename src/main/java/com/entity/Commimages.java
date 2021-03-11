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
public class Commimages implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片id
     */
	private String id;
    /**
     * 商品id
     */
	private String commid;
    /**
     * 图片
     */
	private String image;
    /**
     * 发布时间
     */
	private Date createtime;

    /**
     *  图片状态
     */
    private Integer imagestatus;
}
