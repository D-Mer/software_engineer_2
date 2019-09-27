package com.example.cinema.data.user;

import com.example.cinema.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Repository
@Mapper
public interface AccountMapper {

    /**
     * 创建一个新的账号
     * @param user
     * @return
     */
    int createNewAccount(User user);

    /**
     * 根据用户名查找账号
     * @param username
     * @return
     */
    User getAccountByName(@Param("username") String username);

    /**
     * 获取所有影院角色信息
     * */
    List<User> getAllAccount();

    /**
     * 更新某一角色信息
     * */
    int updateAccount(User user);

    /**
     * 根据id删除某一角色
     * */
    int deleteAccount(@Param("id") int id);
}
