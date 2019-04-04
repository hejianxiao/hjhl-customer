package com.hjhl.customer.service.member.impl;

import com.hjhl.customer.mapper.member.MemberMapper;
import com.hjhl.customer.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
@Service
public class MemberServiceImpl implements MemberService {

    private MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }
}
