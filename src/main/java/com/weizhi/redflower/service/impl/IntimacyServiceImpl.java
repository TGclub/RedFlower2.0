package com.weizhi.redflower.service.impl;

import com.weizhi.redflower.dao.IntimacyDao;
import com.weizhi.redflower.pojo.entity.Intimacy;
import com.weizhi.redflower.service.IntimacyService;
import org.springframework.stereotype.Component;

@Component
public class IntimacyServiceImpl implements IntimacyService{

    private final IntimacyDao intimacyDao;

    public IntimacyServiceImpl(IntimacyDao intimacyDao){
        this.intimacyDao = intimacyDao;
    }

    @Override
    public Intimacy getIntimacyByUid1AndUid2(Integer uid1, Integer uid2){
        return intimacyDao.getIntimacyByUid1AndUid2(uid1,uid2);
    }
}
