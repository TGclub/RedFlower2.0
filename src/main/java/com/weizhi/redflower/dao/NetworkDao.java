package com.weizhi.redflower.dao;

import com.weizhi.redflower.pojo.entity.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkDao extends JpaRepository<Network,Integer> {
}
