package com.wyq.study.web.controller;

import com.wyq.study.constant.FriendConsts;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户接口
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private IUserService userService;
    @Resource
    private IFriendService friendService;

    /**
     * 用户登录，返回token
     *
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback login(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (user == null) {
            return returnCallback(false, null, "请输入用户名及密码...");
        }
        String userName = user.getUsername();
        User userInDB = userService.selectByUserName(userName);
        if (userInDB == null) {
            return returnCallback(false, null, "此用户不存在,请重新输入...");
        }
        try {
            if (MD5.validPassword(user.getPassword(), userInDB.getPassword())) {
                String token = AppSessionHelper.getUserSession(userInDB.getId());
                return returnCallback(true, token, null);
            }
        } catch (NoSuchAlgorithmException e) {
            return returnCallback(false, null, "用户密码不正确,请重新输入");
        } catch (UnsupportedEncodingException e) {
            return returnCallback(false, null, "用户密码不正确,请重新输入");
        }
        return returnCallback(false, null, "用户密码不正确,请重新输入");
    }

    /**
     * 注册新用户
     *
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback addUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (user == null || StringUtils.isEmpty(user.getUsername())) {
            return returnCallback(false, null, "请输入用户信息");
        }
        if (user.getBirthday() != null) {
            try {
                int age = DateUtils.getAge(user.getBirthday());
                user.setAge(age);
            } catch (Exception e) {
                e.printStackTrace();
                return returnCallback(false, null, "The birthDay is before Now.It's unbelievable!");
            }
        }
        User userInDB = userService.selectByUserName(user.getUsername());
        if (userInDB != null) {
            return returnCallback(false, null, "此用户名已存在！");
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String encryptedPwd = MD5.getEncryptedPwd(user.getPassword());
            user.setPassword(encryptedPwd);
            user.setGmtCreate(new Date());
            Integer userId = userService.insert(user);
            String token = AppSessionHelper.getUserSession(userId);
            return returnCallback(true, token, null);
        }

        return returnCallback(false, null, "用户注册失败!");
    }

    /**
     * 获得个人信息,没someOneId返回自己的信息
     *
     * @param token
     * @param someOneId
     * @return
     */
    @RequestMapping(value = "/getUserInfoById", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback getUserInfoById(String token, Integer someOneId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (someOneId == null) {
            User userVO = userService.getByUserId(userId);
            return returnCallback(true, userVO, null);
        } else {
            User someOneVO = userService.getByUserId(someOneId);
            if (someOneVO == null) {
                return returnCallback(false, null, "用户信息获取失败！");
            }
            return returnCallback(true, someOneVO, null);
        }
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
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (user == null) {
            return returnCallback(false, null, "请输入用户信息");
        }
        String resultMsg = userService.checkUser(user);
        if (resultMsg != null) {
            return returnCallback(false, null, resultMsg);
        }
        user.setGmtModified(new Date());
        userService.updateUser(user);

        return returnCallback(true, "您的信息修改很成功!", null);
    }

    /**
     * 返回好友列表
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/listFriends", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listFriends(String token) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        List<User> userList = userService.listFriends(userId);

        return returnCallback(true, userList, null);
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
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (friendUserId == null) {
            return returnCallback(false, null, "请先选择您要添加的好友!");
        }
        User user = userService.getByUserId(friendUserId);
        if (user == null) {
            return returnCallback(false, null, "找不到您要添加的好友！");
        }
        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendUserId);
        friend.setStatus(FriendConsts.ADD_FRIENDS);
        friend.setCreateTime(new Date());
        friendService.saveFriend(friend);

        return returnCallback(true, "添加好友成功！", null);
    }

    /**
     * 同意添加 成为好友
     *
     * @param token
     * @param friendUserId
     * @return
     */
    @RequestMapping(value = "/agreeAddFriend", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback agreeAddFriend(String token, Integer friendUserId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (friendUserId == null) {
            return returnCallback(false, null, "请先选择您要添加的好友!");
        }
        Friend friend = friendService.getFriendByUserId(friendUserId);
        if (friend == null) {
            return returnCallback(false, null, "找不到这个好友请求，maybe是外星人加你!");
        }
        friend.setStatus(FriendConsts.HAS_BEEN_FRIENDS);
        friend.setModifyTime(new Date());
        friendService.saveFriend(friend);

        return returnCallback(true, "同意添加好友！", null);
    }

    /**
     * 取消关注(删除)好友
     *
     * @param token
     * @param friendUserId
     * @return
     */
    @RequestMapping(value = "/deleteFriend", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback deleteFriend(String token, Integer friendUserId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (friendUserId == null) {
            return returnCallback(false, null, "请先选择您要删除的好友!");
        }
        User friendUser = userService.getByUserId(friendUserId);
        if (friendUser == null) {
            return returnCallback(false, null, "找不到您要删除的好友！");
        }
        friendService.deleteFriend(userId, friendUserId);

        return returnCallback(true, "添加好友成功！", null);
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
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (username == null) {
            return returnCallback(false, null, "请先选择您要添加的好友!");
        }
        User userQV = new User();
        userQV.setUsername(username);
        List<User> userVOList = userService.listUsersByUserNameLike(userQV);
        if (userVOList == null || userVOList.size() == 0) {
            userVOList = new ArrayList<User>();
            return returnCallback(true, userVOList, "没有搜索到对应的结果!");
        }

        return returnCallback(true, userVOList, null);
    }

}
