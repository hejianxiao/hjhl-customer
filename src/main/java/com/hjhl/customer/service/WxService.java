package com.hjhl.customer.service;

import com.alibaba.fastjson.JSON;
import com.hjhl.customer.mapper.member.MemberMapper;
import com.hjhl.customer.modal.member.Member;
import com.hjhl.customer.util.ResultVOUtil;
import com.hjhl.customer.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
@Service
public class WxService {

//    private static final String REDIRECT_URL = "www.phoenix1991.com";
    private static final String REDIRECT_URL = " q786ga.natappfree.cc";

    private RestTemplate restTemplate;
    private MemberMapper memberMapper;

    @Autowired
    public WxService(RestTemplate restTemplate, MemberMapper memberMapper) {
        this.restTemplate = restTemplate;
        this.memberMapper = memberMapper;
    }

    public String getCodeUrl(String actCode) {
        return "https://open.weixin.qq.com/connect/oauth2/authorize" +
                "?appid=wx7e0ace40d1aa5213" +
                "&redirect_uri=https://" + REDIRECT_URL + "/view/luckDraw.html?actCode="+ actCode +
                "&response_type=code" +
                "&scope=snsapi_base" +
                "&state=STATE#wechat_redirect";
    }



    public ResultVO getAuth(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=wx7e0ace40d1aa5213" +
                "&secret=f9a452028d7f6605d1710d8da0fedde9" +
                "&code=" + code +
                "&grant_type=authorization_code";
        String resp = restTemplate.getForObject(url, String.class);
        Map map = JSON.parseObject(resp, Map.class);
        if (map.containsKey("openid")) {
            String openid = map.get("openid").toString();
            Member member = memberMapper.selectOne(new Member(openid));
            if (member == null) {
                member = new Member(openid, new Date());
                memberMapper.insert(member);
            }
            return ResultVOUtil.Success(member.getOpenid());
        } else {
            return ResultVOUtil.Error(Integer.valueOf(map.get("errcode").toString()), map.get("errmsg").toString());
        }
    }
}
