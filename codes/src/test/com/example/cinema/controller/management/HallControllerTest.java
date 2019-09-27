package com.example.cinema.controller.management;

import com.example.cinema.CinemaApplicationTest;
import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class HallControllerTest extends CinemaApplicationTest {

    @Autowired
    HallController hallController;

    @Test
    @Transactional
    public void searchAllHall() {
        try {
            List<HallVO> allHallVO;
            allHallVO = (List<HallVO>) hallController.searchAllHall().getContent();
            StringBuilder hallNames = new StringBuilder();
            for (HallVO vo : allHallVO) {
                hallNames.append(vo.getName()).append(System.lineSeparator());
            }
//            hallNames.append("刘涛的后宫");
//            hallNames.append("许竣博的后花园");
//            hallNames.append("周际宇的狗2窝");
            Assert.assertEquals("刘涛的后宫\r\n许竣博的后花园\r\n周际宇的狗窝\r\n",hallNames.toString());
        }catch (Exception ignored){}
    }

    @Test
    @Transactional
    public void addHall() {
        try {
            HallVO hallVO = new HallVO();
            hallVO.setName("测试影厅1");
            hallVO.setRow(5);
            hallVO.setColumn(6);
            Hall hall = (Hall)hallController.addHall(hallVO).getContent();
            assertNotNull(hall.getId());
            Assert.assertEquals("测试影厅1",hall.getName());
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void updateHall() {
        try {
            HallVO hallVO = new HallVO();
            hallVO.setId(3);
            hallVO.setName("测试影厅1");
            hallVO.setRow(5);
            hallVO.setColumn(6);
            Hall hall = (Hall)hallController.updateHall(hallVO).getContent();
            assertNotNull(hall.getId());
            Assert.assertNotEquals("周际宇的狗窝",hall.getName());
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}