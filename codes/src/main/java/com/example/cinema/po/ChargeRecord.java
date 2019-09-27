package com.example.cinema.po;

import lombok.Data;

import java.util.Date;

/**
 * @author zjy
 * @data 2019/6/9
 */

@Data
public class ChargeRecord {
    private int id;
    private int userId;
    private Date time;
    private double amount;
    private double balance;

    public ChargeRecord() {
        this.time = new Date();
    }


    public ChargeRecord(int id, int userId, Date time, double amount, double balance) {
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.amount = amount;
        this.balance = balance;
    }
}
