package com.weizhi.redflower.service;

import com.alibaba.fastjson.JSONObject;
import com.weizhi.redflower.pojo.entity.UserNetwork;

import java.util.List;

public interface UserNetworkService {

    void insert(UserNetwork userNetwork);

    UserNetwork getUserNetworkByUidAndNid(Integer uid,Integer nid);

    List<UserNetwork> getUserNetworksByUid(Integer uid);

    List<UserNetwork> getUserNetworksByNid(Integer nid);

    JSONObject myNetworks(Integer uid);
}
