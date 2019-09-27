package com.example.cinema.controller.user;

import com.example.cinema.CinemaApplicationTest;
import com.example.cinema.po.User;
import com.example.cinema.vo.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

public class AccountControllerTest extends CinemaApplicationTest {

    @Autowired
    private AccountController accountController;

    @Test
    @Transactional
    public void getAllAccount() {
        try {
            List<User> users = (List<User>)accountController.getAllAccount().getContent();
            StringBuilder sb = new StringBuilder();
            for (User u : users) {
                sb.append(u.getUsername()).append(":").append(u.getIdentity()).append(System.lineSeparator());
            }
            assertEquals("root:管理员\r\n" +
                            "employee:员工\r\n" +
                            "test:观众\r\n" +
                            "test1:观众\r\n" +
                            "test121:观众\r\n" +
                            "root2:员工\r\n" +
                            "roottt:观众\r\n" +
                            "zhourui:观众\r\n" +
                            "abc123:观众\r\n" +
                            "dd:观众\r\n" +
                            "814775538:观众\r\n"
                    , sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void addUser() {
        try {
            UserForm form = new UserForm();
            form.setUsername("123456789");
            form.setPassword("123123123");
            form.setIdentity("员工");
            User user = (User) accountController.addUser(form).getContent();
            StringBuilder sb = new StringBuilder();
            sb.append(user.getUsername()).append(":").append(user.getPassword()).append(":").append(user.getIdentity());
            assertNotNull(user.getId());
            assertEquals("123456789:123123123:员工", sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void updateUser() {
        try {
            UserForm form = new UserForm();
            form.setId(16);
            form.setUsername("814775538");
            form.setPassword("123456789");
            form.setIdentity("员工");
            User user = (User) accountController.updateUser(form).getContent();
            StringBuilder sb = new StringBuilder();
            sb.append(user.getUsername()).append(":").append(user.getPassword()).append(":").append(user.getIdentity());
            assertNotNull(user.getId());
            assertEquals("814775538:123456789:员工", sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void delUser() {
        try {
            ResponseVO response = accountController.delUser(16);
            assertTrue(response.getSuccess());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void getConsumption() {
        try {
            List<TicketWithScheduleVO> ticketWithScheduleVOS = (List<TicketWithScheduleVO>)accountController.getConsumption(16).getContent();
            StringBuilder sb = new StringBuilder();
            for (TicketWithScheduleVO t : ticketWithScheduleVOS) {
                sb.append("列:").append(t.getColumnIndex()).append(":行:").append(t.getRowIndex()).append(":电影名:").append(t.getSchedule().getMovieName()).append(System.lineSeparator());
            }
            assertEquals("列:11:行:4:电影名:鬼灭之刃\r\n" +
                    "列:1:行:4:电影名:鬼灭之刃\r\n" +
                    "列:9:行:5:电影名:鬼灭之刃\r\n" +
                    "列:3:行:6:电影名:鬼灭之刃\r\n" +
                    "列:5:行:6:电影名:鬼灭之刃\r\n"
                    , sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }
}