package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.Coupon;

public interface CouponServiceForBL {

    boolean checkCouponValidated(int userId, int couponId);

    Coupon selectCouponsById(int couponId);

    void sendCouponUser(int couponId, int userId);

    void delCouponUser(int userId, int couponId);
}
