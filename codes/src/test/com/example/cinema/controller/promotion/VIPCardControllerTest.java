package com.example.cinema.controller.promotion;

import com.example.cinema.CinemaApplicationTest;
import com.example.cinema.po.ChargeRecord;
import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPPromotion;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.VIPPromotionForm;
import com.example.cinema.vo.VIPPromotionVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class VIPCardControllerTest extends CinemaApplicationTest {

    @Autowired
    private VIPCardController vipCardController;

    @Test
    @Transactional
    public void charge() {
        try {
            VIPCardForm form = new VIPCardForm();
            form.setVipId(8);
            form.setAmount(100);
            VIPCard vipCard = (VIPCard)vipCardController.charge(form).getContent();
            StringBuilder sb = new StringBuilder();
            sb.append(vipCard.getId()).append(":").append(vipCard.getUserId()).append(":").append(vipCard.getBalance());
            Assert.assertEquals("8:16:8457.0", sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void getVIPPromotion() {
        try {
            List<VIPPromotion> promotions = (List<VIPPromotion>)vipCardController.getVIPPromotion().getContent();
            StringBuilder sb = new StringBuilder();
            for (VIPPromotion v : promotions) {
                sb.append(v.getStandard()).append(":").append(v.getMinus()).append(System.lineSeparator());
            }
            Assert.assertEquals("500.0:100.0\r\n200.0:30.0\r\n", sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void releaseVIPPromotion() {
        try {
            VIPPromotionForm form = new VIPPromotionForm();
            form.setStandard(300);
            form.setMinus(50);
            form.setStartTime(new Date());
            form.setEndTime(new Date());
            VIPPromotionVO promotion = (VIPPromotionVO)vipCardController.releaseVIPPromotion(form).getContent();
            StringBuilder sb = new StringBuilder();
            sb.append(promotion.getStandard()).append(":").append(promotion.getMinus());
            Assert.assertEquals("300.0:50.0", sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void updateVIPPromotion() {
        try {
            VIPPromotionForm form = new VIPPromotionForm();
            form.setStandard(300);
            form.setMinus(50);
            form.setStartTime(new Date());
            SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date end= simFormat.parse("2019-06-30 22:45:56");
            form.setEndTime(end);
            VIPPromotionVO promotion = (VIPPromotionVO)vipCardController.releaseVIPPromotion(form).getContent();
            StringBuilder sb = new StringBuilder();
            sb.append(promotion.getStandard()).append(":").append(promotion.getMinus());
            Assert.assertEquals("300.0:50.0", sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void getChargeRecord() {
        try {
            List<ChargeRecord> records = (List<ChargeRecord>) vipCardController.getChargeRecord(3).getContent();
            StringBuilder sb = new StringBuilder();
            for (ChargeRecord c : records) {
                sb.append(c.getAmount()).append(":").append(c.getBalance()).append(System.lineSeparator());
            }
            Assert.assertEquals("300.0:330.0\r\n100.0:430.0\r\n", sb.toString());
        }catch (Exception e){
            fail();
            e.printStackTrace();
        }
    }
}