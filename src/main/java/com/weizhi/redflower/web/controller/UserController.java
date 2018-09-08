package com.weizhi.redflower.web.controller;

import com.weizhi.redflower.exception.handler.UserInfoException;
import com.weizhi.redflower.pojo.dto.ResponseDto;
import com.weizhi.redflower.pojo.dto.ProfileDto;
import com.weizhi.redflower.pojo.entity.User;
import com.weizhi.redflower.util.WechatUtil;
import com.weizhi.redflower.service.UserService;
import com.weizhi.redflower.pojo.dto.WeChatResponseDto;
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


    /**
     * 用户登录
     * @param code
     * @param encryptedData
     * @param iv
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseDto login(@RequestParam(name = "code", defaultValue = "") String code,
                             @RequestParam(name = "encryptedData") String encryptedData,
                             @RequestParam(name = "iv") String iv,HttpSession session) throws Exception {
        WeChatResponseDto weChatResponseDto = wechatUtil.getOpenIdAndSessionKey(code);
        if (weChatResponseDto == null) {
            return ResponseDto.failed("login in failed, code is wrong");
        }

        User user = userService.login(weChatResponseDto.getOpenid(),encryptedData,weChatResponseDto.getSession_key(),iv);
        /**
         * 登录成功后将userId保存在session域中
         */
        session.setAttribute("userId",user.getId());

        if (user == null){
            return ResponseDto.failed("login failed");
        }

        return ResponseDto.succeed("login in successfully");

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
        ProfileDto userInfo =  new ProfileDto(user.getName(),user.getDefinition(),user.getWxid(),user.getAvatarUrl());
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
     * 修改个人信息
     * @param name
     * @param definition
     * @param wxid
     * @param session
     * @return
     */

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ResponseDto update(@RequestParam("name")String name,
                              @RequestParam("definition")String definition,
                              @RequestParam("wxid")String wxid,
                              HttpSession session){

        Integer userId = (Integer) session.getAttribute("userId");
        if(userId == null){return ResponseDto.failed("no login");}

        User user = userService.getUserById(userId);
        user.setName(name);
        user.setWxid(wxid);

        if (user.getDefinition().length()>10){
            return ResponseDto.failed("自定义信息长度不多于10个字符！");
        }
        user.setWxid(wxid);

        userService.update(user);
        return ResponseDto.succeed("modify successfully");

    }
}
