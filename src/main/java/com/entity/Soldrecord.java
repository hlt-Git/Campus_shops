package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class Soldrecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 售出记录id
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
     * 售价
     */
    private BigDecimal thinkmoney;
    /**
     * 售出时间
     */
    private Date soldtime;
    /**
     * 用户id
     */
    private String userid;
    /**
     * 1正常 2删除
     */
    private Integer soldstatus;
}
