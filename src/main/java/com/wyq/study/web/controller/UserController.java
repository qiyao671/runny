package com.wyq.study.web.controller;

import com.wyq.study.pojo.Callback;
import com.wyq.study.pojo.User;
import com.wyq.study.service.IUserService;
import com.wyq.study.utils.AppSessionHelper;
import com.wyq.study.utils.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangyiqiang on 16/6/1.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private IUserService userService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback login(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (user == null) {
            return returnCallback("Error", "请输入用户名及密码...");
        }
        String userName = user.getUsername();
        User userInDB = userService.selectByUserName(userName);
        if (userInDB == null) {
            return returnCallback("Error", "此用户不存在,请重新输入...");
        }
        if (MD5.validPassword(user.getPassword(), userInDB.getPassword())) {
            String token = AppSessionHelper.getUserSession(userInDB.getId());
            return returnCallback("Success", token);
        }
        return returnCallback("Error", "用户密码不正确,请重新输入");
    }

    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback addUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (user == null) {
            return returnCallback("Error", "请输入用户信息");
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String encryptedPwd = MD5.getEncryptedPwd(user.getPassword());
            user.setPassword(encryptedPwd);
            userService.insert(user);
            return returnCallback("Success", "用户注册成功!");
        }
        return returnCallback("Error", "用户注册失败!");
    }

}
