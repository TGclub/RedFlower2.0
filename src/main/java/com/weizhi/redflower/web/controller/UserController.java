package com.weizhi.redflower.web.controller;

import com.weizhi.redflower.pojo.dto.ResponseDto;
import com.weizhi.redflower.pojo.dto.ProfileDto;
import com.weizhi.redflower.pojo.entity.User;
import com.weizhi.redflower.util.WechatUtil;
import com.weizhi.redflower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private UserService userService;

    private WechatUtil wechatUtil;

    @Autowired
    public UserController(UserService userService, WechatUtil wechatUtil) {
        this.userService = userService;
        this.wechatUtil = wechatUtil;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseDto login(@RequestParam(name = "code", defaultValue = "") String code,
                             HttpSession session) throws Exception {
        String openid = wechatUtil.getOpenId(code);
        if (openid == null) {
            return ResponseDto.failed("log in failed, code is wrong");
        }

        User user = userService.login(openid);

        String msg = "Login success";
        ProfileDto profileDto = new ProfileDto(user.getName(),user.getDefinition(),user.getWxid());
        return ResponseDto.succeed(msg, profileDto);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseDto logout(HttpSession session) {
        session.invalidate();
        return ResponseDto.succeed();
    }
}
