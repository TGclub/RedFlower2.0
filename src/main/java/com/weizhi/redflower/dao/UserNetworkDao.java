package com.weizhi.redflower.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.weizhi.redflower.pojo.entity.UserNetwork;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNetworkDao extends JpaRepository<UserNetwork,Integer>{
}
