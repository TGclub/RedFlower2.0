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
        /**
         *  一个用户对应一个唯一的sessionId
         */
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("sessionId",session.getId());

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

    }


    /**
     * 返回登录后用户的信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/userInfo",method = RequestMethod.GET)
    public ResponseDto getUserInfo(HttpSession session) throws UserInfoException{
        Integer userId =  (Integer) session.getAttribute("userId");
        User user = userService.getUserById(userId);
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


    /**
     * 完善修改个人信息
     * @param user  表单自动生成的user
     * @param session
     * @return
     */

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ResponseDto update(@RequestBody User user,
                              HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        User uuser = userService.getUserById(userId);
        user.setId(uuser.getId());
        user.setOpenid(uuser.getOpenid());
        if (user.getDefinition().length()>10){
            return ResponseDto.failed("自定义信息长度不多于10个字符！");
        }
        user.setState(UserInfoStateEnum.COMPLETED.getState());
        userService.update(user);
        return ResponseDto.succeed();

    }
}
