package com.example.cinema.data.sales;

import com.example.cinema.po.RefundStrategy;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.po.Ticket;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketWithScheduleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Mapper
public interface TicketMapper {

    int insertTicket(Ticket ticket);

    int insertTickets(List<Ticket> tickets);

    void deleteTicket(int ticketId);

    void updateTicketState(@Param("ticketId") int ticketId, @Param("state") int state);

    List<Ticket> selectTicketsBySchedule(int scheduleId);

    Ticket selectTicketByScheduleIdAndSeat(@Param("scheduleId") int scheduleId, @Param("column") int columnIndex, @Param("row") int rowIndex);

    Ticket selectTicketById(int id);

    List<Ticket> selectTicketByUser(int userId);

    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredTicket();

    List<Ticket> selectTicketByDate(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    List<RefundStrategy> selectRefundStrategy();

    void insertRefundStrategy(RefundStrategy refundStrategy);

    void updateRefundStrategy(RefundStrategy refundStrategy);

    ScheduleItem selectScheduleByTicketId(@Param("id") int id);

    void completeTicket(@Param("id") int id,@Param("payAmount") double payAmount);

    List<TicketWithScheduleVO> selectTicketWithScheduleByUser(int userId);
}

