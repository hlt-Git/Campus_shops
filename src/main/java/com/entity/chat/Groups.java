package com.entity.chat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Groups {
    private String id;//群组ID
    private String groupname;//群组名
    private String avatar;//群组头像
    private String userid;//用户id
    private Date intime;//加入时间
    private String grpowner; //群主
}
