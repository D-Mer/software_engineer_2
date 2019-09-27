package com.example.cinema.controller.promotion;

import com.example.cinema.CinemaApplicationTest;
import com.example.cinema.po.Coupon;
import com.example.cinema.po.VIPCostInfo;
import com.example.cinema.vo.SendCouponForm;
import com.example.cinema.vo.TicketWithScheduleVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

public class CouponControllerTest extends CinemaApplicationTest {

    @Autowired
    private CouponController couponController;


    @Test
    @Transactional
    public void getVIPListByAmount() {
        try {
            List<VIPCostInfo> vipCostInfoList = (List<VIPCostInfo>) couponController.getVIPListByAmount(100).getContent();
            StringBuilder sb = new StringBuilder();
            for (VIPCostInfo costInfo : vipCostInfoList) {
                sb.append(costInfo.getUsername()).append(":").append(costInfo.getCost()).append(System.lineSeparator());
            }
            assertEquals("814775538:460.0\r\n"+
                    "test:240.0\r\n"
                    , sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void getAllCoupon() {
        try {
            List<Coupon> couponList = (List<Coupon>) couponController.getAllCoupon().getContent();
            StringBuilder sb = new StringBuilder();
            for (Coupon coupon : couponList) {
                sb.append(coupon.getName()).append(":").append(coupon.getDescription()).append(System.lineSeparator());
            }
            assertEquals("春季电影节:测试优惠券\r\n" +
                            "品质联盟:测试优惠券\r\n" +
                            "电影节优惠券:春节电影节优惠券\r\n" +
                            "测试用优惠券:测试优惠券\r\n" +
                            "辉哥的优惠券:辉哥的优惠券\r\n" +
                            "刘涛的优惠券:刘涛的优惠券\r\n"
                    , sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

}