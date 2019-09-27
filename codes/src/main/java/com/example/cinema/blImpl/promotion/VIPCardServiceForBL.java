package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.VIPCard;

public interface VIPCardServiceForBL {
    void pay(int userId, double totalFare) throws Exception;

    void addRefundBalance(int userId, double balance);
}
