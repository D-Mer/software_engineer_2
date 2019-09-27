package com.example.cinema.po;

import lombok.Data;

/**
 * @author huwen
 * @date 2019/3/23
 */

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String identity = "观众";
}
