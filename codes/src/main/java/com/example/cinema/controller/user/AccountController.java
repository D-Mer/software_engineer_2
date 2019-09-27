package com.example.cinema.controller.user;

import com.example.cinema.blImpl.user.AccountServiceImpl;
import com.example.cinema.config.InterceptorConfiguration;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author huwen
 * @date 2019/3/23
 */
@RestController()
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR="用户名或密码错误";
    @Autowired
    private AccountServiceImpl accountService;
    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm, HttpSession session){
        UserVO user = accountService.login(userForm);
        if(user==null){
           return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        //注册session
        session.setAttribute(InterceptorConfiguration.SESSION_KEY,userForm);
        return ResponseVO.buildSuccess(user);
    }
    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserForm userForm){
        return accountService.registerAccount(userForm);
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return "index";
    }

    @GetMapping("/admin/getAllUser")
    public ResponseVO getAllAccount(){
        //【完成TODO】: 获取所有影院角色
        return accountService.getAllAccount();
    }

    @PostMapping("/admin/addUser")
    public ResponseVO addUser(@RequestBody UserForm userForm){
        //【完成TODO】: 增加影院角色，和注册差不多
        return accountService.addAccount(userForm);
    }

    @PostMapping("/admin/updateUser")
    public ResponseVO updateUser(@RequestBody UserForm userForm){
        //【完成TODO】: 更新某个影院角色信息
        return accountService.updateAccount(userForm);
    }

    @PostMapping("/admin/delUser")
    public ResponseVO delUser(@RequestParam("id")int id){
        //【完成TODO】: 删除指定影院角色
        return accountService.deleteAccount(id);
    }

    @GetMapping("/user/member/getConsumption")
    public ResponseVO getConsumption(@RequestParam("id") int id){
        //【完成TODO】: 获取用户历史消费记录(和会员卡充值记录是分离的)，等待前端
        return accountService.getConsumption(id);
    }

}
