package com.example.cinema.vo;

import com.example.cinema.po.RefundStrategy;
import lombok.Data;

@Data
public class RefundStrategyVO {
    int id;

    int hoursBeforeEnd;

    double rate;

    public RefundStrategyVO(){}

    public RefundStrategyVO(RefundStrategy refundStrategy){
        this.id = refundStrategy.getId();
        this.hoursBeforeEnd = refundStrategy.getHoursBeforeEnd();
        this.rate = refundStrategy.getRate();
    }
}