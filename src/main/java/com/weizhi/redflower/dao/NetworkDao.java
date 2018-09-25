package com.weizhi.redflower.dao;

import com.weizhi.redflower.pojo.entity.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetworkDao extends JpaRepository<Network,Integer> {

    Network getNetworkById(Integer id);

    List<Network> getNetworkByNid(Integer nid);
}
