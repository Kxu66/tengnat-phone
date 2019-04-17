package com.tengnat.imapi.service.impl;

import com.tengnat.mybatis.entity.ComDbc1;
import com.tengnat.mybatis.mapper.ComDbc1Mapper;
import com.tengnat.imapi.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private ComDbc1Mapper comDbc1Mapper;

    public void test(Integer dbcId){

        ComDbc1 comDbc1 = comDbc1Mapper.selectByPrimaryKey(dbcId);
        System.out.println(comDbc1.getDbcid());

    }

}
