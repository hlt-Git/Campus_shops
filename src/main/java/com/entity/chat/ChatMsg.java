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
public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发送者id
     */
	private String senduserid;
    /**
     * 接收者id
     */
	private String reciveuserid;
    /**
     * 发送内容
     */
	private String content;
    /**
     * 发送时间
     */
	private Date sendtime;
    /**
     * 消息类型
     */
	private String msgtype;


}
