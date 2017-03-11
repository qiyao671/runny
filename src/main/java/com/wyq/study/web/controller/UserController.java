package com.wyq.study.web.controller;

import com.github.pagehelper.PageInfo;
import com.wyq.study.pojo.Callback;
import com.wyq.study.pojo.Friend;
import com.wyq.study.pojo.User;
import com.wyq.study.service.IFriendService;
import com.wyq.study.service.IUserService;
import com.wyq.study.utils.AppSessionHelper;
import com.wyq.study.utils.DateUtils;
import com.wyq.study.utils.MD5;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by wangyiqiang on 16/6/1.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private IUserService userService;
    @Resource
    private IFriendService friendService;

    /**
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
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
        try {
            if (MD5.validPassword(user.getPassword(), userInDB.getPassword())) {
                String token = AppSessionHelper.getUserSession(userInDB.getId());
                return returnCallback("Success", token);
            }
        } catch (NoSuchAlgorithmException e) {
            return returnCallback("Error", "用户密码不正确,请重新输入");
        } catch (UnsupportedEncodingException e) {
            return returnCallback("Error", "用户密码不正确,请重新输入");
        }
        return returnCallback("Error", "用户密码不正确,请重新输入");
    }

    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback addUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (user == null || StringUtils.isEmpty(user.getUsername())) {
            return returnCallback("Error", "请输入用户信息");
        }
        if (user.getBirthday() != null) {
            try {
                int age = DateUtils.getAge(user.getBirthday());
                user.setAge(age);
            } catch (Exception e) {
                e.printStackTrace();
                return returnCallback("Error", "The birthDay is before Now.It's unbelievable!");
            }
        }
        User userInDB = userService.selectByUserName(user.getUsername());
        if (userInDB != null) {
            return returnCallback("Error", "此用户名已存在！");
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String encryptedPwd = MD5.getEncryptedPwd(user.getPassword());
            user.setPassword(encryptedPwd);
            Integer userId = userService.insert(user);
            String token = AppSessionHelper.getUserSession(userId);
            return returnCallback("Success", token);
        }

        return returnCallback("Error", "用户注册失败!");
    }

    /**
     * 获得个人信息
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback getUserInfo(String token) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        User userVO = userService.getByUserId(userId);
        if (userVO != null) {
            return returnCallback("Success", userVO);
        }

        return returnCallback("Error", "用户信息获取失败！");
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @param token
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback updateUser(User user, String token) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        if (user == null) {
            return returnCallback("Error", "请输入用户信息");
        }
        String resultMsg = userService.checkUser(user);
        if (resultMsg != null) {
            return returnCallback("Error", resultMsg);
        }
        userService.updateUser(user);

        return returnCallback("Success", "您的信息修改很成功!");
    }

    /**
     * 返回好友列表
     *
     * @param token
     * @param num
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/listFriends", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listFriends(String token, Integer num, Integer pageSize) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        PageInfo pageInfo = userService.listFriends(userId, num, pageSize);
        return returnCallback("success", pageInfo);
    }

    /**
     * 添加好友
     *
     * @param token
     * @param friendUserId
     * @return
     */
    @RequestMapping(value = "/saveFriend", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback saveFriend(String token, Integer friendUserId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        if (friendUserId == null) {
            return returnCallback("Error", "请先选择您要添加的好友!");
        }
        User user = userService.getByUserId(friendUserId);
        if (user == null) {
            return returnCallback("Error", "找不到您要添加的好友！");
        }
        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendUserId);
        friendService.saveFriend(friend);
        return returnCallback("success", "添加好友成功！");
    }


    /**
     * 通过用户名获取用户信息 --模糊搜索
     *
     * @param token
     * @param username
     * @return
     */
    @RequestMapping(value = "/listUsersByUsername", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listUsersByUsername(String token, String username) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        if (username == null) {
            return returnCallback("Error", "请先选择您要添加的好友!");
        }
        User userQV = new User();
        userQV.setUsername(username);
        List<User> userVOList = userService.listUsersByUserNameLike(userQV);
        if (userVOList == null || userVOList.size() == 0) {
            return returnCallback("Error", "没有搜索到对应的结果!");
        }
        return returnCallback("success", userVOList);
    }


}
