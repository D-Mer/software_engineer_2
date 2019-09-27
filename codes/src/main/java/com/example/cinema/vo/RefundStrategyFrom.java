package com.example.cinema.vo;

import com.example.cinema.po.RefundStrategy;
import lombok.Data;

@Data
public class RefundStrategyFrom {

    int id;

    int hoursBeforeEnd;

    double rate;

}
