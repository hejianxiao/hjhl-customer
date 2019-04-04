package com.hjhl.customer.controller;

import com.hjhl.customer.service.luck.LuckService;
import com.hjhl.customer.vo.ResultVO;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
@RestController
@RequestMapping("/luck")
public class LuckController {

    private LuckService luckService;

    @Autowired
    public LuckController(LuckService luckService) {
        this.luckService = luckService;
    }


    @GetMapping("/getScore/{actCode}")
    public ResultVO getScore(@PathVariable("actCode") String actCode) {
        return luckService.getScore(actCode);
    }


    @GetMapping("/luckData/{actCode}")
    public ResultVO luckData(@PathVariable("actCode") String actCode) {
        return luckService.luckData(actCode);
    }

    @PostMapping("/doDraw")
    public ResultVO doDraw(@NotEmpty(message = "活动id不能为空") String actCode,
                            @NotEmpty(message = "openid不能为空") String openid) {
        return luckService.doDraw(actCode, openid);
    }

    @PostMapping("/receive")
    public ResultVO receive(String orderId, String name, String value) {
        return luckService.receive(orderId, name, value);
    }

}
