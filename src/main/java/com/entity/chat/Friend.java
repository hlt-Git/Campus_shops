package com.entity.chat;

import com.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Friend {
    private String groupname;//好友分组名
    private String id;//分组ID
    private List<UserInfo> list;//分组下的好友列表
}
