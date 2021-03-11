package com.mapper;


import com.entity.UserInfo;
import com.entity.chat.ChatMsg;

import java.util.List;

public interface ChatmsgMapper {
    /**插入发送的消息记录*/
    void insertChatmsg(ChatMsg chatmsg);
    /**查询聊天记录*/
    List<UserInfo> LookChatMsg(ChatMsg chatMsg);
}