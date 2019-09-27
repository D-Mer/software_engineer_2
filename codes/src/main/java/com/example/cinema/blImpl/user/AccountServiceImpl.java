package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.AccountService;
import com.example.cinema.blImpl.sales.TicketServiceForBl;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.User;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TicketServiceForBl ticketServiceForBl;

    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        User user = new User();
        try {
            user.setUsername(userForm.getUsername());
            user.setPassword(userForm.getPassword());
            accountMapper.createNewAccount(user);
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(user);
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getUsername());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return new UserVO(user);
    }

    @Override
    public ResponseVO getAllAccount() {
        ResponseVO response;
        try {
            response = ResponseVO.buildSuccess(accountMapper.getAllAccount());
            response.setMessage("获取所有影院角色信息成功");
        }catch (Exception e){
            e.printStackTrace();
            response = ResponseVO.buildFailure("获取所有影院角色信息失败，原因未知");
        }
        return response;
    }

    @Override
    public ResponseVO addAccount(UserForm userForm) {
        ResponseVO response;
        try {
            User user = new User();
            user.setUsername(userForm.getUsername());
            user.setPassword(userForm.getPassword());
            user.setIdentity(userForm.getIdentity());
            accountMapper.createNewAccount(user);
            response = ResponseVO.buildSuccess(user);
            response.setMessage("增加影院角色信息成功");
        }catch (Exception e){
            e.printStackTrace();
            response = ResponseVO.buildFailure("该用户已存在");
        }
        return response;
    }

    @Override
    public ResponseVO updateAccount(UserForm userForm) {
        ResponseVO response;
        try {
            User user = new User();
            user.setId(userForm.getId());
            user.setUsername(userForm.getUsername());
            user.setPassword(userForm.getPassword());
            user.setIdentity(userForm.getIdentity());
            accountMapper.updateAccount(user);
            response = ResponseVO.buildSuccess(user);
            response.setMessage("修改影院角色信息成功");
        }catch (Exception e){
            e.printStackTrace();
            response = ResponseVO.buildFailure("修改失败，原因未知");
        }
        return response;
    }

    @Override
    public ResponseVO deleteAccount(int id) {
        ResponseVO response;
        try {
            accountMapper.deleteAccount(id);
            response = ResponseVO.buildSuccess();
            response.setMessage("删除影院角色信息成功");
        }catch (Exception e){
            e.printStackTrace();
            response = ResponseVO.buildFailure("删除影院角色信息失败，原因未知");
        }
        return response;
    }

    @Override
    public ResponseVO getConsumption(int id) {
        ResponseVO response;
        try {
            response = ResponseVO.buildSuccess(ticketServiceForBl.getTickWithScheduleByUserId(id));
            response.setMessage("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            response = ResponseVO.buildFailure("查询失败");
        }
        return response;
    }


}
