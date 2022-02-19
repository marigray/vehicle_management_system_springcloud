package com.dra.pojo.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessage implements Serializable {
    private String id;
    private String username;
    private List<Role> roles;

    public static void main(String[] args) {
        // 肯定是同一个人 分类分析
        List<Role> roles = new UserMessage().roles;

    }
}
