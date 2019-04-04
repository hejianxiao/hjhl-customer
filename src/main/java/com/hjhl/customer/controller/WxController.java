package com.hjhl.customer.controller;

import com.hjhl.customer.service.WxService;
import com.hjhl.customer.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
@RestController
@RequestMapping("/wx")
public class WxController {

    private WxService wxService;

    @Autowired
    public WxController(WxService wxService) {
        this.wxService = wxService;
    }

    @GetMapping("/getUrl/{actCode}")
    public String getUrl(@PathVariable("actCode") String actCode) {
        return wxService.getCodeUrl(actCode);
    }

    @GetMapping("/getAuth/{code}")
    public ResultVO getAuth(@PathVariable("code") String code) {
        return wxService.getAuth(code);
    }
}
