package com.weizhi.redflower.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.weizhi.redflower.pojo.dto.ResponseDto;
import com.weizhi.redflower.pojo.entity.Network;
import com.weizhi.redflower.pojo.entity.UserNetwork;
import com.weizhi.redflower.service.NetworkService;
import com.weizhi.redflower.service.UserNetworkService;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/network")
public class NetworkController {

    private NetworkService networkService;

    private UserNetworkService userNetworkService;

    @Autowired
    public NetworkController(NetworkService networkService,UserNetworkService userNetworkService){
        this.networkService = networkService;
        this.userNetworkService = userNetworkService;
    }

    /**
     * 创建新的人脉圈
     * @Param name
     */
    @RequestMapping(value = "/name",method = RequestMethod.POST)
    public ResponseDto AddNetwork(@RequestParam("name")String name, HttpSession httpSession){

        Integer userId = (Integer)httpSession.getAttribute("userId");
        if (userId == null){
            return ResponseDto.failed("no login");
        }

        Network network = new Network();
        network.setName(name);

        if(network.getName() == null){return ResponseDto.failed("failed");}

        networkService.insert(network);
        return ResponseDto.failed("succeed");
    }

    /**
     * 邀请更多人加入人脉网
     * @param nid
     * @param uid
     */
    @RequestMapping(value = "/invitation",method = RequestMethod.POST)
    public ResponseDto Invite(@RequestParam("nid")Integer nid,
                              @RequestParam("uid")Integer uid,HttpSession httpSession){

        Integer userId = (Integer)httpSession.getAttribute("userId");
        if (userId == null){
            return ResponseDto.failed("no login");
        }

        if(userNetworkService.getUserNetworkByUidAndNid(uid,nid) != null){
            return ResponseDto.failed("already join");
        }

        UserNetwork userNetwork = new UserNetwork();
        userNetwork.setNid(nid);
        userNetwork.setUid(uid);

        if(userNetwork.getNid() != null && userNetwork.getUid() != null){
            return ResponseDto.succeed("invite successfully");
        }
        return ResponseDto.failed("failed");
    }

    /**
     * 查看我的人脉圈
     * @param uid
     */
    @RequestMapping(value = "/myNetworks")
    public ResponseDto myNetworks(HttpSession httpSession){
        Integer userId = (Integer)httpSession.getAttribute("userId");
        if (userId == null){
            return ResponseDto.failed("no login");
        }

        JSONObject response = userNetworkService.myNetworks(userId);
        if (response == null){
            return ResponseDto.failed("my networks are null");
        }

        return ResponseDto.succeed("query successfully",response);
    }
}
