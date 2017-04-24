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
import com.xiaoleilu.hutool.io.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
     * 获得个人信息,没someOneId返回自己的信息   是否是我的好友 是：1 不是：-1 我自己：2
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
        User userVO;
        if (someOneId == null) {
            userVO = userService.getByUserId(userId);
            if (userVO == null) {
                return returnCallback(false, null, "用户信息获取失败！");
            }
            userVO.setRelationStatus(FriendConsts.IS_MYSELF);
        } else {
            userVO = userService.getByUserId(someOneId);
            if (userVO == null) {
                return returnCallback(false, null, "用户信息获取失败！");
            }
            userVO.setRelationStatus(friendService.getFriendRelationStatus(userId, someOneId));
        }
        userVO.setProfile(getImageUrl(userVO.getProfile()));

        return returnCallback(true, userVO, null);
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
    public Callback updateUser(@RequestBody User user, String token) {
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
        List<User> userList = friendService.listFriends(userId);
        for (User user : userList) {
            user.setRelationStatus(FriendConsts.HAS_BEEN_FRIENDS);
            user.setProfile(getImageUrl(user.getProfile()));
        }

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
        Friend friend = friendService.getFriendByUserIdAndFriendId(friendUserId, userId);
        if (friend != null) {
            if (friend.getStatus() == FriendConsts.ADD_FRIENDS) {
                friend.setStatus(FriendConsts.HAS_BEEN_FRIENDS);
                friend.setModifyTime(new Date());
                friendService.updateFriendByPrimaryKey(friend);
                return returnCallback(true, "添加好友成功", null);
            } else if (friend.getStatus() != FriendConsts.HAS_BEEN_FRIENDS) {
                friend.setStatus(FriendConsts.ADD_FRIENDS);
                friend.setModifyTime(new Date());
                friendService.updateFriendByPrimaryKey(friend);
                return returnCallback(true, "已发送好友请求", null);
            } else {
                return returnCallback(true, "你们已经是好友了", null);

            }
        }

        friend = friendService.getFriendByUserIdAndFriendId(userId, friendUserId);
        if (friend != null && friend.getStatus() != FriendConsts.HAS_BEEN_FRIENDS) {
            friend.setStatus(FriendConsts.ADD_FRIENDS);
            friend.setModifyTime(new Date());
            friendService.updateFriendByPrimaryKey(friend);
            return returnCallback(true, "已发送好友请求", null);
        } else if (friend != null && friend.getStatus() == FriendConsts.HAS_BEEN_FRIENDS) {
            return returnCallback(true, "你们已经是好友了", null);
        } else {
            friend = new Friend();
            friend.setUserId(userId);
            friend.setFriendId(friendUserId);
            friend.setStatus(FriendConsts.ADD_FRIENDS);
            friend.setCreateTime(new Date());
            friendService.saveFriend(friend);
        }

        return returnCallback(true, "已发送好友请求", null);
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
        Friend friend = friendService.getFriendByUserIdAndFriendId(friendUserId, userId);
        if (friend == null) {
            return returnCallback(false, null, "找不到这个好友请求，maybe是外星人加你!");
        }
        friend.setStatus(FriendConsts.HAS_BEEN_FRIENDS);
        friend.setModifyTime(new Date());
        friendService.updateFriendByPrimaryKey(friend);

        return returnCallback(true, "成功添加好友", null);
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
        userQV.setId(userId);
        List<User> userVOList = userService.listUsersByUserNameLike(userQV);
        if (userVOList == null) {
            userVOList = new ArrayList<User>();
        } else {
            for (User user : userVOList) {
                user.setRelationStatus(friendService.getFriendRelationStatus(userId, user.getId()));
                user.setProfile(getImageUrl(user.getProfile()));
            }
        }

        return returnCallback(true, userVOList, null);
    }

    /**
     * 用户头像上传
     */
    @RequestMapping(value = "/uploadFile", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback uploadFile(String token, HttpServletRequest request) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        User user = userService.getByUserId(userId);
        if (user == null) {
            return returnCallback(false, null, "找不到您要查找的用户!");
        }
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iterator = multiRequest.getFileNames();

            while (iterator.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iterator.next().toString());
                if (file != null) {
                    String fileName = file.getOriginalFilename();
                    Date currData = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    String classPath = this.getClass().getClassLoader().getResource("").getPath();
                    String projectPath = request.getServletContext().getRealPath("/");
//                    String projectName = projectPath.substring(projectPath.lastIndexOf("/") + 1);
                    String filePath = projectPath + "/images/profile/" + user.getId() + "/" + sdf.format(currData);  //文件夹存放路径
                    String relativePath = "/profile/" + user.getId() + "/" + sdf.format(currData); //文件夹存放相对路径
                    //上传
                    try {
                        if (!FileUtil.isDirectory(filePath)) {
                            FileUtil.mkdir(filePath);
                        }
                        file.transferTo(new File(filePath + "-" + fileName));
                        user.setGmtModified(new Date());
                        userService.updateUser(user);
                        userService.updateUserProfile(user.getId(), relativePath + "-" + fileName);
                        return returnCallback(true, relativePath + "-" + fileName, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return returnCallback(false, null, e.getMessage());
                    }
                }
            }
        }

        return returnCallback(false, null, "上传失败！");
    }

    @RequestMapping(value = "/listFriendRequests", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listFriendRequests(String token) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        List<User> userList = friendService.listFriendsAndRequest(userId);
        for (User user : userList) {
            user.setRelationStatus(friendService.getFriendRelationStatus(userId, user.getId()));
            user.setProfile(getImageUrl(user.getProfile()));
        }

        return returnCallback(true, userList, null);
    }

    private String getImageUrl(String imageName) {
        return "http://192.168.31.245:8080/images" + imageName;
    }
}
