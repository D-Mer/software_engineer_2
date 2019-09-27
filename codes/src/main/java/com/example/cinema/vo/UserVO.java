package com.example.cinema.vo;

import com.example.cinema.po.User;
import lombok.Data;

/**
 * @author fjj
 * @date 2019/4/11 3:22 PM
 */
@Data
public class UserVO {
    private Integer id;
    private String username;
    private String password;
    private String identity;

    public UserVO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.identity = user.getIdentity();
    }
}
