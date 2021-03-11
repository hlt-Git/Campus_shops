package com.entity.chat;

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
public class Friends implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 好友表id
     */
	private String id;
    /**
     * 用户id
     */
	private String userid;
    /**
     * 好友id
     */
	private String fuserid;
    /**
     * 时间
     */
	private Date addtime;


}
