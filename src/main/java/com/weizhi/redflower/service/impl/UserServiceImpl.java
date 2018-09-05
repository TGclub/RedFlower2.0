package com.weizhi.redflower.service.impl;

import com.weizhi.redflower.dao.UserDao;
import com.weizhi.redflower.pojo.entity.User;
import com.weizhi.redflower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class UserServiceImpl implements UserService{
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao){this.userDao = userDao;}

    @Override
    public User login(String openid) {
        User user = userDao.getUserByOpenid(openid);
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            user= userDao.save(user);
        }
        return user;
    }

    /**
     * 更新完善个人信息
     * @param user 修改后的user
     */
    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.findById(id).get();
    }
}
