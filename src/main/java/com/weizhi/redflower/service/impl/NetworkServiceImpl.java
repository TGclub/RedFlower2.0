package com.weizhi.redflower.service.impl;

import com.weizhi.redflower.pojo.entity.Network;
import com.weizhi.redflower.dao.NetworkDao;
import com.weizhi.redflower.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NetworkServiceImpl implements NetworkService {

    private final NetworkDao networkDao;

    @Autowired
    public NetworkServiceImpl(NetworkDao networkDao){this.networkDao = networkDao;}

    @Override
    public void insert(Network network){
        networkDao.save(network);
    }

    @Override
    public List<Network> getNetworkByNid(Integer integer) {
        return networkDao.getNetworkByNid(integer);
    }
}
