package com.weizhi.redflower.service.impl;

import com.weizhi.redflower.dao.UserNetworkDao;
import com.weizhi.redflower.service.UserNetworkService;
import com.weizhi.redflower.pojo.entity.UserNetwork;
import org.springframework.beans.factory.annotation.Autowired;

public class UserNetworkServiceImpl implements UserNetworkService {

    private final UserNetworkDao userNetworkDao;

    @Autowired
    public UserNetworkServiceImpl(UserNetworkDao userNetworkDao){
        this.userNetworkDao = userNetworkDao;
    }

    @Override
    public void insert(UserNetwork userNetwork){
        userNetworkDao.save(userNetwork);
    }
}
