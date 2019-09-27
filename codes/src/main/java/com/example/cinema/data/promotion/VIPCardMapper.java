package com.example.cinema.data.promotion;

import com.example.cinema.po.ChargeRecord;
import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPPromotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/14.
 */
@Mapper
public interface VIPCardMapper {

    int insertOneCard(VIPCard vipCard);

    VIPCard selectCardById(int id);

    void updateCardBalance(@Param("id") int id,@Param("balance") double balance);

    VIPCard selectCardByUserId(int userId);

    void insertPromotion(VIPPromotion vipPromotion);

    void updatePromotion(VIPPromotion vipPromotion);

    List<VIPPromotion> selectPromotion();

    int insertRecord(ChargeRecord chargeRecord);

    List<ChargeRecord> getChargeRecord(@Param("id") int id);
}
