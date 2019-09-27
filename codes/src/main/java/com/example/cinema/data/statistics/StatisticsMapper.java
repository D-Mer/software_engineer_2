package com.example.cinema.data.statistics;

import com.example.cinema.po.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/16 1:43 PM
 */
@Mapper
public interface StatisticsMapper {
    /**
     * 查询date日期每部电影的排片次数
     * @param date
     * @return
     */
    List<MovieScheduleTime> selectMovieScheduleTimes(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询所有电影的总票房（包括已经下架的，降序排列）
     * @return
     */
    List<MovieTotalBoxOffice> selectMovieTotalBoxOffice();

    /**
     * 查询某天每个客户的购票金额
     * @param date
     * @param nextDate
     * @return
     */
    List<AudiencePrice> selectAudiencePrice(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询影院的影厅及座位数目、观众人数、放映场次
     * @param date
     * @return
     */
    List<ProjectionSituation> selectProjectionSituation(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询影院近几天票房最高的几部电影的id
     * @param limitTime 需要查询到的最早的时间范围
     * @param movieNum 需要查到第几名
     * @return 返回影院近几天票房最高的几部电影的id
     */
    List<PopularMoviePO> selectPopularMoviesByNumberAndDay(@Param("movieNum") int movieNum, @Param("limitTime") String limitTime);

    /**
     * 查询某日的所有排片信息
     *
     * */

}
