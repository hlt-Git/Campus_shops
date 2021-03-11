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
public class ImData {
    private UserInfo mine;
    private List<Friend> friend;
    private List<Groups> group;
}
