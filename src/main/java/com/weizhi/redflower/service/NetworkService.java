package com.weizhi.redflower.service;

import com.weizhi.redflower.pojo.entity.Network;

import java.util.List;

public interface NetworkService {

    void insert(Network network);

    List<Network> getNetworkByNid(Integer integer);
}
