package com.hjhl.customer.service.luck;

import com.hjhl.customer.vo.ResultVO;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
public interface LuckService {

    ResultVO getScore(String actCode);

    ResultVO luckData(String actCode);

    ResultVO doDraw(String actCode, String openid);

    ResultVO receive(String orderId, String name, String value);

}
