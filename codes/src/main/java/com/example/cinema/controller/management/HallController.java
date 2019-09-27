package com.example.cinema.controller.management;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**影厅管理
 * @author fjj
 * @date 2019/4/12 1:59 PM
 */
@RestController
@RequestMapping("/hall")
public class HallController {
    @Autowired
    private HallService hallService;

    @GetMapping("/all")
    public ResponseVO searchAllHall(){
        return hallService.searchAllHall();
    }

    @PostMapping("/addHall")
    public ResponseVO addHall(@RequestBody HallVO hallVO){
        // 【完成TODO】: 录入新影厅
        return hallService.addHall(hallVO);
    }

    @PostMapping("/updateHall")
    public ResponseVO updateHall(@RequestBody HallVO hallVO){
        // 【完成TODO】: 修改影厅信息
        return hallService.updateHall(hallVO);
    }

}
