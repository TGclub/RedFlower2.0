package com.weizhi.redflower.service.impl;

import com.weizhi.redflower.pojo.entity.Network;
import com.weizhi.redflower.dao.NetworkDao;
import com.weizhi.redflower.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

@Service
public class NetworkServiceImpl implements NetworkService {

    private NetworkDao networkDao;

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
