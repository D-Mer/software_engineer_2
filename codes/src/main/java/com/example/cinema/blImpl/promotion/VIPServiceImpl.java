package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.po.ChargeRecord;
import com.example.cinema.po.VIPPromotion;
import com.example.cinema.vo.*;
import com.example.cinema.po.VIPCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService, VIPCardServiceForBL {
    @Autowired
    VIPCardMapper vipCardMapper;

    @Override
    public ResponseVO addVIPCard(int userId) {
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setBalance(0);
        try {
            int id = vipCardMapper.insertOneCard(vipCard);
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getVIPInfo() {
        VIPInfoVO vipInfoVO = new VIPInfoVO();
        List<VIPPromotion> list = vipCardMapper.selectPromotion();
        StringBuilder promotions = new StringBuilder();
        for (VIPPromotion p : list) {
            promotions.append("满 ").append(p.getStandard()).append(" 送 ").append(p.getMinus()).append(System.lineSeparator());
        }
        vipInfoVO.setDescription(promotions.toString());
        vipInfoVO.setPrice(VIPCard.price);
        return ResponseVO.buildSuccess(vipInfoVO);
    }

    @Override
    @Transactional
    public ResponseVO charge(VIPCardForm vipCardForm) {

        VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
        ChargeRecord chargeRecord = new ChargeRecord();
        chargeRecord.setUserId(vipCard.getUserId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }
        double minus = vipCard.calculate(vipCardForm.getAmount(),vipCardMapper.selectPromotion());
        vipCard.setBalance(vipCard.getBalance() + vipCardForm.getAmount() + minus);
        chargeRecord.setAmount(vipCardForm.getAmount());
        chargeRecord.setBalance(vipCard.getBalance());
        try {
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCard.getBalance());
            vipCardMapper.insertRecord(chargeRecord);
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO releaseVIPPromotion(VIPPromotionForm vipPromotionForm) {
        ResponseVO response;
        try {
            VIPPromotion vipPromotion = new VIPPromotion();
            vipPromotion.setStandard(vipPromotionForm.getStandard());
            vipPromotion.setMinus(vipPromotionForm.getMinus());
            vipPromotion.setStartTime(vipPromotionForm.getStartTime());
            vipPromotion.setEndTime(vipPromotionForm.getEndTime());
            vipCardMapper.insertPromotion(vipPromotion);
            response = ResponseVO.buildSuccess(new VIPPromotionVO(vipPromotion));
            response.setMessage("发布会员卡充值优惠策略成功");
        }catch (Exception e){
            e.printStackTrace();
            response = ResponseVO.buildFailure("发布失败，原因未知");
        }
        return response;
    }

    @Override
    public ResponseVO updateVIPPromotion(VIPPromotionForm vipPromotionForm) {
        ResponseVO response;
        try {
            VIPPromotion vipPromotion = new VIPPromotion();
            vipPromotion.setStandard(vipPromotionForm.getStandard());
            vipPromotion.setMinus(vipPromotionForm.getMinus());
            vipPromotion.setId(vipPromotionForm.getId());
            vipPromotion.setStartTime(vipPromotionForm.getStartTime());
            vipPromotion.setEndTime(vipPromotionForm.getEndTime());
            vipCardMapper.updatePromotion(vipPromotion);
            response = ResponseVO.buildSuccess(new VIPPromotionVO(vipPromotion));
            response.setMessage("修改会员卡充值优惠策略成功");
        }catch (Exception e){
            e.printStackTrace();
            response = ResponseVO.buildFailure("修改失败，原因未知");
        }
        return response;
    }

    @Override
    public ResponseVO getVIPPromotion() {
        ResponseVO response;
        try {
            response = ResponseVO.buildSuccess(vipCardMapper.selectPromotion());
            response.setMessage("获取会员卡充值优惠策略成功");
        }catch (Exception e){
            e.printStackTrace();
            response = ResponseVO.buildFailure("获取失败，原因未知");
        }
        return response;
    }

    @Override
    public ResponseVO getChargeRecord(int id) {
        List<ChargeRecord> recordList;
        try {
            recordList = vipCardMapper.getChargeRecord(id);
            if (recordList.size() == 0)
                throw new Exception();
            return ResponseVO.buildSuccess(recordList);
        } catch (Exception e) {
            return ResponseVO.buildFailure("用户不存在或没有历史消费记录");
        }
    }

    @Override
    public void pay(int userId, double totalFare) throws Exception {
        VIPCard card = vipCardMapper.selectCardByUserId(userId);
        if(card.getBalance() < totalFare) {
            throw new Exception("VIP卡余额不足");
        }
        vipCardMapper.updateCardBalance(card.getId(), card.getBalance() - totalFare);
    }

    @Override
    public void addRefundBalance(int userId, double balance) {
        VIPCard card = vipCardMapper.selectCardByUserId(userId);
        if (card != null){
            vipCardMapper.updateCardBalance(card.getId(), card.getBalance() + balance);
        }
    }
}
