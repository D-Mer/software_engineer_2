package com.example.cinema.bl.user;

import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     * @return
     */
    public ResponseVO registerAccount(UserForm userForm);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     * @return
     */
    public UserVO login(UserForm userForm);

    /**
     * 管理员增删改查影院角色信息
     * */
    ResponseVO getAllAccount();
    ResponseVO addAccount(UserForm userForm);
    ResponseVO updateAccount(UserForm userForm);
    ResponseVO deleteAccount(int id);

    ResponseVO getConsumption(int id);
}
