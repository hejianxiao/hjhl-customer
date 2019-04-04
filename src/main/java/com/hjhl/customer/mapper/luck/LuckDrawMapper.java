package com.hjhl.customer.mapper.luck;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hjhl.customer.modal.luck.LuckDraw;
import org.apache.ibatis.annotations.Param;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
public interface LuckDrawMapper extends BaseMapper<LuckDraw> {

    LuckDraw selectOneByAct(@Param("actCode")String actCode,
                                  @Param("nowTime")String nowTime);

}
