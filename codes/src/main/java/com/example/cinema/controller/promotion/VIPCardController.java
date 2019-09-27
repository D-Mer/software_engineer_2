package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPPromotionForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;

    @PostMapping("/add")
    public ResponseVO addVIP(@RequestParam int userId){
        return vipService.addVIPCard(userId);
    }
    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId){
        return vipService.getCardByUserId(userId);
    }

    @GetMapping("/getVIPInfo")
    public ResponseVO getVIPInfo(){
        return vipService.getVIPInfo();
    }

    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPCardForm vipCardForm){
        return vipService.charge(vipCardForm);
    }

    @GetMapping("/getVIPPromotion")
    public ResponseVO getVIPPromotion(){
        // 【完成TODO】: 获取用户会员卡充值优惠数据
        return vipService.getVIPPromotion();
    }

    @PostMapping("/releaseVIPPromotion")
    public ResponseVO releaseVIPPromotion(@RequestBody VIPPromotionForm vipPromotionForm){
        // 【完成TODO】: 发布用户会员卡充值优惠数据
        return vipService.releaseVIPPromotion(vipPromotionForm);
    }

    @PostMapping("/updateVIPPromotion")
    public ResponseVO updateVIPPromotion(@RequestBody VIPPromotionForm vipPromotionForm){
        // 【完成TODO】: 更新用户会员卡充值优惠数据
        return vipService.updateVIPPromotion(vipPromotionForm);
    }

    @GetMapping("/getChargeRecord")
    public ResponseVO getChargeRecord(@RequestParam("id") int id){
        //【完成TODO】: 获取会员卡充值记录，和用户历史消费记录是分离的，等待前端
        return vipService.getChargeRecord(id);
    }

}
