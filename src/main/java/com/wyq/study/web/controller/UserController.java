//package com.wyq.study.web.controller;
//
//import com.wyq.study.pojo.Callback;
//import com.wyq.study.service.IUserService;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//
///**
// * Created by wangyiqiang on 16/6/1.
// */
//@Controller
//@RequestMapping("/user")
//public class UserController extends BaseController {
//    @Resource
//    private IUserService userService;
//
//    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public Callback login(User user) {
//        if (user == null) {
//            return returnCallback("Error", "请输入用户名及密码...");
//        }
//        String userName = user.getUserName();
//        User userInDB = userService.selectByUserName(userName);
//        if (userInDB == null) {
//            return returnCallback("Error", "此用户不存在,请重新输入...");
//        }
//        if (user.getPassword().equals(userInDB.getPassword())) {
//            return returnCallback("Success", "登录成功!!!");
//        }
//        return returnCallback("Error", "用户密码不正确,请重新输入");
//    }
//
//    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public Callback addUser(User user) {
//        if (user == null) {
//            return returnCallback("Error", "请输入用户信息");
//        }
//        userService.insert(user);
//        return returnCallback("Success", user.getId());
//    }
//
//}
