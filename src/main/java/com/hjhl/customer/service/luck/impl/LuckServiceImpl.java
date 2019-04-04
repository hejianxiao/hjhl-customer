package com.hjhl.customer.service.luck.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hjhl.customer.constant.ResultEnum;
import com.hjhl.customer.mapper.luck.*;
import com.hjhl.customer.mapper.member.MemberMapper;
import com.hjhl.customer.modal.luck.*;
import com.hjhl.customer.modal.member.Member;
import com.hjhl.customer.service.luck.LuckService;
import com.hjhl.customer.util.LotteryUtil;
import com.hjhl.customer.util.ResultVOUtil;
import com.hjhl.customer.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
@Service
public class LuckServiceImpl implements LuckService {

    private LuckDrawMapper drawMapper;
    private LuckPrizeMapper prizeMapper;
    private LuckOrderMapper orderMapper;
    private MemberMapper memberMapper;
    private LuckReceiveMapper receiveMapper;
    private AnsScoreControlMapper scoreControlMapper;

    @Autowired
    public LuckServiceImpl(LuckDrawMapper drawMapper, LuckPrizeMapper prizeMapper, LuckOrderMapper orderMapper, MemberMapper memberMapper, LuckReceiveMapper receiveMapper, AnsScoreControlMapper scoreControlMapper) {
        this.drawMapper = drawMapper;
        this.prizeMapper = prizeMapper;
        this.orderMapper = orderMapper;
        this.memberMapper = memberMapper;
        this.receiveMapper = receiveMapper;
        this.scoreControlMapper = scoreControlMapper;
    }

    @Override
    public ResultVO getScore(String actCode) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTIme = sdf.format(new Date());
        LuckDraw draw = drawMapper.selectOneByAct(actCode, nowTIme);
        if (draw == null) {
            return ResultVOUtil.Error(ResultEnum.LUCK_DRAW_EXPIRE_EMPTY);
        }
        AnsScoreControl scoreControl = scoreControlMapper.selectOne(new AnsScoreControl(draw.getId()));
        if (scoreControl == null) {
            return ResultVOUtil.Error(ResultEnum.ANS_SCORE_ERROR);
        }
        return ResultVOUtil.Success(scoreControl.getScore());
    }

    @Override
    public ResultVO luckData(String actCode) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTIme = sdf.format(new Date());
        LuckDraw draw = drawMapper.selectOneByAct(actCode, nowTIme);
        if (draw == null) {
            return ResultVOUtil.Error(ResultEnum.LUCK_DRAW_EXPIRE_EMPTY);
        }
        List<LuckPrize> luckPrize = prizeMapper.selectList(new EntityWrapper<>(new LuckPrize())
                .setSqlSelect("id", "prize_name", "prize_pic")
                .where("luck_draw_id={0}", draw.getId())
                .and("or_delete={0}", 1));

        if (CollectionUtils.isEmpty(luckPrize)) {
            return ResultVOUtil.Error(ResultEnum.LUCK_PRIZE_EMPTY);
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("receiveInfo", draw.getReceiveInfo());
        resMap.put("draw", draw.getActName());
        resMap.put("luckPrize", luckPrize);
        return ResultVOUtil.Success(resMap);
    }

    @Override
    @Transactional
    public ResultVO doDraw(String actCode, String openid) {
        Member member = memberMapper.selectOne(new Member(openid));
        if (member == null) {
            return ResultVOUtil.Error(ResultEnum.MEMBER_EMPTY);
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTIme = sdf.format(date);
        LuckDraw draw = drawMapper.selectOneByAct(actCode, nowTIme);
        if (draw == null) {
            return ResultVOUtil.Error(ResultEnum.LUCK_DRAW_EXPIRE_EMPTY);
        }

        List<LuckPrize> luckPrizes = prizeMapper.selectList(new EntityWrapper<>(new LuckPrize())
                .where("luck_draw_id={0}", draw.getId())
                .and("or_delete={0}", 1));

        if (CollectionUtils.isEmpty(luckPrizes)) {
            return ResultVOUtil.Error(ResultEnum.LUCK_PRIZE_EMPTY);
        }

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf1.format(date) + " 00:00:00";
        String endTime = sdf1.format(date) + " 23:59:59";

        Integer orderCount = orderMapper.selectCount(new EntityWrapper<>(new LuckOrder())
                .where("create_time >={0}", startTime)
                .and("create_time <={0}", endTime)
                .and("member_id={0}", member.getId())
                .and("luck_draw_id={0}", draw.getId()));

        //每日抽奖次数必须<=当前活动设定每天抽奖次数上限
        if (orderCount >= draw.getDrawCount()) {
            return ResultVOUtil.Error(ResultEnum.LUCK_DRAW_COUNT_ERROR);
        }

        Integer index = null;
        Integer orderCount2 = orderMapper.selectCount(new EntityWrapper<>(new LuckOrder())
                .where("member_id={0}", member.getId())
                .and("luck_draw_id={0}", draw.getId())
                .and("or_prize={0}", "1"));
        //当前活动最多抽中奖励次数
        if (orderCount2 >= draw.getMaxCount()) {
            index = notPrizeIndex(luckPrizes);
            if (index.equals(-1)) {
                return ResultVOUtil.Error(ResultEnum.SYS_ERROR);
            }
        }

        Integer orderCount3 = orderMapper.selectCount(new EntityWrapper<>(new LuckOrder())
                .where("create_time >={0}", startTime)
                .and("create_time <={0}", endTime)
                .and("member_id={0}", member.getId())
                .and("luck_draw_id={0}", draw.getId())
                .and("or_prize={0}", "1"));

        //当前活动每天最多抽中奖励次数
        if (orderCount3 >= draw.getDayMaxCount()) {
            index = notPrizeIndex(luckPrizes);
            if (index.equals(-1)) {
                return ResultVOUtil.Error(ResultEnum.SYS_ERROR);
            }
        }

        if (index == null) {
            index = prizeIndex(luckPrizes);
        }

        LuckPrize prize = luckPrizes.get(index);
        LuckOrder order = new LuckOrder();
        order.setCreateTime(date);
        order.setLuckDrawId(draw.getId());
        order.setLuckPrizeId(prize.getId());
        order.setMemberId(member.getId());
        if (prize.getPrizeType().equals("1")) {
            order.setOrPrize("0");
        } else {
            prize.setPrizeAmount(prize.getPrizeAmount() - 1);
            prizeMapper.updateById(prize);
            order.setOrPrize("1");
        }
        orderMapper.insert(order);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("index", index + 1);
        resMap.put("prizeType", prize.getPrizeType());
        resMap.put("orderId", order.getId());
        return ResultVOUtil.Success(resMap);
    }

    private Integer notPrizeIndex(List<LuckPrize> luckPrizes) {
        List<Double> orignalRates = new ArrayList<>();
        luckPrizes.forEach(e -> {
            //非奖品才可抽到。
            if (!e.getPrizeType().equals("1")) {
                orignalRates.add(0D);
            } else {
                orignalRates.add(e.getPrizeWeight());
            }
        });
        return LotteryUtil.lottery(orignalRates);
    }

    private Integer prizeIndex(List<LuckPrize> luckPrizes) {
        List<Double> orignalRates = new ArrayList<>();
        luckPrizes.forEach(e -> {
            //奖品数量不足，直接抽不到(业务暂时处理)
            if (e.getPrizeAmount() == 0) {
                orignalRates.add(0D);
            } else {
                orignalRates.add(e.getPrizeWeight());
            }
        });
        return LotteryUtil.lottery(orignalRates);
    }


    @Override
    @Transactional
    public ResultVO receive(String orderId, String name, String value) {
        LuckOrder order = orderMapper.selectById(orderId);
        if (order == null) {
            return ResultVOUtil.Error(ResultEnum.LUCK_ORDER_EMPTY);
        }
        if (StringUtils.isEmpty(name)) {
            return ResultVOUtil.Error(ResultEnum.HANDLE_ERROR);
        }
        if (StringUtils.isEmpty(value)) {
            return ResultVOUtil.Error(ResultEnum.HANDLE_ERROR);
        }

        String[] names = name.split(",");
        String[] values = value.split(",");

        for (int i=0; i<names.length; i++) {
            LuckReceive receive = new LuckReceive();
            receive.setLuckOrderId(orderId);
            receive.setReceiveName(names[i]);
            receive.setReceiveValue(values[i]);
            receiveMapper.insert(receive);
        }
        return ResultVOUtil.Success();
    }

}
