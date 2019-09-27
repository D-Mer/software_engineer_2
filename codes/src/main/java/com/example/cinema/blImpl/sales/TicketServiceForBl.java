package com.example.cinema.blImpl.sales;

import com.example.cinema.po.Ticket;
import com.example.cinema.vo.TicketWithScheduleVO;

import java.util.Date;
import java.util.List;

public interface TicketServiceForBl {

    List<Ticket> getTicketByDate(Date startDate, Date endDate);

    List<TicketWithScheduleVO> getTickWithScheduleByUserId(int userId);

}
