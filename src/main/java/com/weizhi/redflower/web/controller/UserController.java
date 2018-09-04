package com.weizhi.redflower.web.controller;

import com.weizhi.redflower.enums.UserInfoStateEnum;
import com.weizhi.redflower.exception.handler.UserInfoException;
import com.weizhi.redflower.pojo.dto.ResponseDto;
import com.weizhi.redflower.pojo.dto.ProfileDto;
import com.weizhi.redflower.pojo.entity.User;
import com.weizhi.redflower.util.WechatUtil;
import com.weizhi.redflower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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


    /**
     * 用户登录
     * @param code
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseDto login(@RequestParam(name = "code", defaultValue = "") String code,
                             HttpSession session) throws Exception {
        String openid = wechatUtil.getOpenId(code);
        if (openid == null) {
            return ResponseDto.failed("login in failed, code is wrong");
        }

        User user = userService.login(openid);
        /**
         * 登录成功后将userId保存在session域中
         */
        session.setAttribute("userId",user.getId());
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("user",user);

        /**
         * 用户信息是否完善
         */
        if (user.getState().equals(UserInfoStateEnum.INCOMPLETED.getState())){
            userMap.put("incompleted",0);
            return ResponseDto.succeed("not complete user info",userMap);
        }else {
            userMap.put("completetd",1);
            return ResponseDto.succeed("login in successfully",userMap);
        }

        return ResponseDto.succeed("login in successfully",userMap)

    }


    /**
     * 返回登录后用户的信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/userInfo",method = RequestMethod.GET)
    public ResponseDto getUserInfo(HttpSession session){
        String userId =  session.getId();
        User user = userService.getUserById(userId);
        if (user.getDefinition().length()>10){
            new UserInfoException("自定义信息长度不多于10个字符！");
        }
        ProfileDto userInfo =  new ProfileDto(user.getName(),user.getDefinition(),user.getWxid());
        return ResponseDto.succeed("userInfo",userInfo);
    }

    /**
     * 用户退出
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseDto logout(HttpSession session) {
        session.invalidate();
        return ResponseDto.succeed();
    }
}
