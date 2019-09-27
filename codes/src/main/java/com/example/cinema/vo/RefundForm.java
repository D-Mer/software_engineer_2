package com.example.cinema.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RefundForm {
    private int userId;
    private int ticketId;

    public RefundForm() {
    }

    public RefundForm(int userId, int ticketId) {
        this.userId = userId;
        this.ticketId = ticketId;
    }


}
