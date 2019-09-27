package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    @Autowired
    private HallMapper hallMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public Hall getHallById(int id) {
        try {
            return hallMapper.selectHallById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public List<Hall> getAllHall() {
        return hallMapper.selectAllHall();
    }

    private List<HallVO> hallList2HallVOList(List<Hall> hallList) {
        List<HallVO> hallVOList = new ArrayList<>();
        for (Hall hall : hallList) {
            hallVOList.add(new HallVO(hall));
        }
        return hallVOList;
    }

    public ResponseVO addHall(HallVO hallVO) {
        Hall hall = new Hall();
        try {
            hall.setName(hallVO.getName());
            hall.setRow(hallVO.getRow());
            hall.setColumn(hallVO.getColumn());
            hallMapper.addHall(hall);
            hall = hallMapper.selectHallById(hall.getId());
            ResponseVO response = ResponseVO.buildSuccess(hall);
            response.setMessage("影厅录入成功");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("影厅已存在");
        }

    }

    public ResponseVO updateHall(HallVO hallVO){
            Hall hall = new Hall();
            hall.setColumn(hallVO.getColumn());
            hall.setRow(hallVO.getRow());
            hall.setId(hallVO.getId());
            hall.setName(hallVO.getName());
            try{
                if(isEngaged(hall))
                    throw new Exception();
                hallMapper.updateHall(hall);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseVO.buildFailure("更新失败,该影厅已有排片");
            }
            return ResponseVO.buildSuccess(hall);
    }

    public boolean isEngaged(Hall hall){
        int id = hall.getId();
        Date date = new Date();
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,scheduleMapper.getDay());
        Hall tempHall = hallMapper.isEngaged(id,date);
        if(tempHall ==null)
            return false;
        return true;

    }
}
