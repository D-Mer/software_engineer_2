package com.example.cinema.controller.sales;

import com.example.cinema.CinemaApplicationTest;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.ChargeRecord;
import com.example.cinema.po.RefundStrategy;
import com.example.cinema.po.Ticket;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.RefundStrategyFrom;
import com.example.cinema.vo.RefundStrategyVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

public class TicketControllerTest extends CinemaApplicationTest {

    @Autowired
    private TicketController ticketController;

    @Autowired
    private TicketMapper ticketMapper;

    @Test
    @Transactional
    public void getRefundStrategy() {
        try {
            List<RefundStrategy> strategies = (List<RefundStrategy>) ticketController.getRefundStrategy().getContent();
            StringBuilder sb = new StringBuilder();
            for (RefundStrategy r : strategies) {
                sb.append(r.getHoursBeforeEnd()).append(":").append(r.getRate()).append(System.lineSeparator());
            }
            Assert.assertEquals("2:0.5\r\n3:0.75\r\n4:0.9\r\n", sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void addRefundStrategy() {
        try {
            RefundStrategyFrom form = new RefundStrategyFrom();
            form.setHoursBeforeEnd(5);
            form.setRate(0.95);
            RefundStrategyVO strategy = (RefundStrategyVO)ticketController.addRefundStrategy(form).getContent();
            StringBuilder sb = new StringBuilder();
            sb.append(strategy.getHoursBeforeEnd()).append(":").append(strategy.getRate());
            assertTrue(strategy.getId()!=0);
            Assert.assertEquals("5:0.95", sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void updateRefundStrategy() {
        try {
            RefundStrategyFrom form = new RefundStrategyFrom();
            form.setId(3);
            form.setHoursBeforeEnd(5);
            form.setRate(0.95);
            RefundStrategyVO strategy = (RefundStrategyVO)ticketController.updateRefundStrategy(form).getContent();
            StringBuilder sb = new StringBuilder();
            sb.append(strategy.getHoursBeforeEnd()).append(":").append(strategy.getRate());
            Assert.assertEquals("5:0.95", sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void refundTicket() {
        try {
            RefundForm form = new RefundForm();
            form.setUserId(16);
            form.setTicketId(186);
            double balance = (double)ticketController.refundTicket(form).getContent();
            assertEquals(100.0, balance, 0.0);
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }
}