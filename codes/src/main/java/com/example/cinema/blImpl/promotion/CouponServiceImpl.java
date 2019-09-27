package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.po.Coupon;
import com.example.cinema.po.VIPCostInfo;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.SendCouponForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liying on 2019/4/17.
 */
@Service
public class CouponServiceImpl implements CouponService, CouponServiceForBL {

    @Autowired
    CouponMapper couponMapper;

    @Override
    public ResponseVO getCouponsByUser(int userId) {
        try {
            return ResponseVO.buildSuccess(couponMapper.selectCouponByUser(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addCoupon(CouponForm couponForm) {
        try {
            Coupon coupon=new Coupon();
            coupon.setName(couponForm.getName());
            coupon.setDescription(couponForm.getDescription());
            coupon.setTargetAmount(couponForm.getTargetAmount());
            coupon.setDiscountAmount(couponForm.getDiscountAmount());
            coupon.setStartTime(couponForm.getStartTime());
            coupon.setEndTime(couponForm.getEndTime());
            couponMapper.insertCoupon(coupon);
            return ResponseVO.buildSuccess(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO issueCoupon(int couponId, int userId) {
        try {
            couponMapper.insertCouponUser(couponId,userId);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO getVIPListByAmount(double target_amount) {
        ResponseVO response;
        try{
            List<VIPCostInfo> vipCostInfoList = couponMapper.selectByCost(target_amount);
            for (int i=0; i<vipCostInfoList.size(); i++) {
                VIPCostInfo v = vipCostInfoList.get(i);
                if (v.getCost() < target_amount){
                    vipCostInfoList = vipCostInfoList.subList(0,i);
                    break;
                }
            }
            response = ResponseVO.buildSuccess(vipCostInfoList);
            response.setMessage("获取成功");
        }catch (Exception e){
            e.printStackTrace();
            response = ResponseVO.buildFailure("获取失败，原因未知");
        }
        return response;
    }

    @Override
    public ResponseVO getCoupons() {
        ResponseVO response;
        try{
            List<Coupon> couponList = couponMapper.selectCoupon();
            response = ResponseVO.buildSuccess(couponList);
            response.setMessage("获取成功");
        }catch (Exception e){
            e.printStackTrace();
            response = ResponseVO.buildFailure("获取失败，原因未知");
        }
        return response;
    }

    @Override
    public ResponseVO sendCoupon(List<SendCouponForm> sendCouponFormList) {
        ResponseVO response;
        try{
            couponMapper.sendCoupon(sendCouponFormList);
            response = ResponseVO.buildSuccess();
            response.setMessage("赠送成功");
        }catch (Exception e){
            e.printStackTrace();
            response = ResponseVO.buildFailure("赠送失败，原因未知");
        }
        return response;
    }

    @Override
    public boolean checkCouponValidated(int userId, int couponId) {
        List<Coupon> coupons = couponMapper.selectCouponByUser(userId);
        for (Coupon c : coupons) {
            if (c.getId() == couponId){
                return true;
            }
        }
        return false;
    }

    @Override
    public Coupon selectCouponsById(int couponId) {
        return couponMapper.selectById(couponId);
    }

    @Override
    public void sendCouponUser(int couponId, int userId) {
        couponMapper.insertCouponUser(couponId, userId);
    }

    @Override
    public void delCouponUser(int userId, int couponId) {
        couponMapper.deleteCouponUser(couponId, userId);
    }
}
