package com.example.cinema.blImpl.statistics;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.bl.statistics.StatisticsService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.hall.HallServiceImpl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceImpl;
import com.example.cinema.blImpl.sales.TicketServiceForBl;
import com.example.cinema.blImpl.sales.TicketServiceImpl;
import com.example.cinema.data.statistics.StatisticsMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/16 1:34 PM
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;

    @Autowired
    HallServiceForBl hallServiceForBl;
    @Autowired
    ScheduleServiceForBl scheduleServiceForBl;
    @Autowired
    TicketServiceForBl ticketServiceForBl;

    @Override
    public ResponseVO getScheduleRateByDate(Date date) {
        try{
            Date requireDate = date;
            if(requireDate == null){
                requireDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));

            Date nextDate = getNumDayAfterDate(requireDate, 1);
            return ResponseVO.buildSuccess(movieScheduleTimeList2MovieScheduleTimeVOList(statisticsMapper.selectMovieScheduleTimes(requireDate, nextDate)));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTotalBoxOffice() {
        try {
            return ResponseVO.buildSuccess(movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(statisticsMapper.selectMovieTotalBoxOffice()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAudiencePriceSevenDays() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date startDate = getNumDayAfterDate(today, -6);
            List<AudiencePriceVO> audiencePriceVOList = new ArrayList<>();
            for(int i = 0; i < 7; i++){
                AudiencePriceVO audiencePriceVO = new AudiencePriceVO();
                Date date = getNumDayAfterDate(startDate, i);
                audiencePriceVO.setDate(date);
                List<AudiencePrice> audiencePriceList = statisticsMapper.selectAudiencePrice(date, getNumDayAfterDate(date, 1));
                double totalPrice = audiencePriceList.stream().mapToDouble(item -> item.getTotalPrice()).sum();
                audiencePriceVO.setPrice(Double.parseDouble(String.format("%.2f", audiencePriceList.size() == 0 ? 0 : totalPrice / audiencePriceList.size())));
                audiencePriceVOList.add(audiencePriceVO);
            }
            return ResponseVO.buildSuccess(audiencePriceVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getMoviePlacingRateByDate(Date date) {
        try {
            //目标信息
            int hallNum=0;
            int seatNum=0;
            int ticketNum=0;
            int scheduleNum=0;

            //计算起止日期
            Date requireDate = date;
            if (requireDate == null) {
                requireDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));
            Date nextDate = getNumDayAfterDate(requireDate, 1);

            //根据日期得到当日的排片
            List<ScheduleItem> scheduleItemList = new ArrayList<>();
            scheduleItemList = scheduleServiceForBl.getScheduleByDate(requireDate,nextDate);
            scheduleNum = scheduleItemList.size();

            //获取所有影厅
            List<Hall> hallList;
            hallList = hallServiceForBl.getAllHall();
            hallNum = hallList.size();

            //获取所有座位数
            for (Hall h : hallList){
                seatNum+=h.getColumn()*h.getRow();
            }

            //获取当天所有票数
            ticketNum = ticketServiceForBl.getTicketByDate(requireDate, nextDate).size();

            return ResponseVO.buildSuccess((0.0+ticketNum) / scheduleNum/ hallNum / seatNum );
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("查询某日上座率失败" + e.getMessage());
        }
    }

    @Override
    public ResponseVO getPopularMovies(int days, int movieNum) {
        //要求见接口说明
        List<PopularMovieVO> popularMovieVOList = new ArrayList<>();
        List<PopularMoviePO> popularMoviePOList = new ArrayList<>();

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) - days);
        SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String limitTime = sf.format(calendar.getTime());

        try{
            popularMoviePOList = statisticsMapper.selectPopularMoviesByNumberAndDay(movieNum, limitTime);
            for (PopularMoviePO po : popularMoviePOList) {
                popularMovieVOList.add(po.getVO());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("获取最受欢迎的电影失败"+ e .getMessage());
        }
        return ResponseVO.buildSuccess(popularMovieVOList);
    }


    /**
     * 获得num天后的日期
     * @param oldDate
     * @param num
     * @return
     */
    Date getNumDayAfterDate(Date oldDate, int num){
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(oldDate);
        calendarTime.add(Calendar.DAY_OF_YEAR, num);
        return calendarTime.getTime();
    }



    private List<MovieScheduleTimeVO> movieScheduleTimeList2MovieScheduleTimeVOList(List<MovieScheduleTime> movieScheduleTimeList){
        List<MovieScheduleTimeVO> movieScheduleTimeVOList = new ArrayList<>();
        for(MovieScheduleTime movieScheduleTime : movieScheduleTimeList){
            movieScheduleTimeVOList.add(new MovieScheduleTimeVO(movieScheduleTime));
        }
        return movieScheduleTimeVOList;
    }


    private List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(List<MovieTotalBoxOffice> movieTotalBoxOfficeList){
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOList = new ArrayList<>();
        for(MovieTotalBoxOffice movieTotalBoxOffice : movieTotalBoxOfficeList){
            movieTotalBoxOfficeVOList.add(new MovieTotalBoxOfficeVO(movieTotalBoxOffice));
        }
        return movieTotalBoxOfficeVOList;
    }


    private List<ProjectionSituationVO> projectionSituationList2projectionSituationVOList(List<ProjectionSituation> projectionSituationList) {
        List<ProjectionSituationVO> projectionSituationVOList = new ArrayList<>();
        for(ProjectionSituation projectionSituation: projectionSituationList){
            projectionSituationVOList.add(new ProjectionSituationVO(projectionSituation));
        }
        return projectionSituationVOList;
    }

}
