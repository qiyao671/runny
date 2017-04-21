package com.wyq.study.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyq.study.constant.ApproveConsts;
import com.wyq.study.constant.CommentConsts;
import com.wyq.study.constant.MomentConsts;
import com.wyq.study.pojo.*;
import com.wyq.study.service.IApproveService;
import com.wyq.study.service.ICommentService;
import com.wyq.study.service.IMomentService;
import com.wyq.study.service.IUserService;
import com.wyq.study.utils.AppSessionHelper;
import com.xiaoleilu.hutool.io.FileUtil;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 朋友圈动态
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-12 下午1:42
 * 系统版本：1.0.0
 **/
@Controller
@RequestMapping("/moment")
public class MomentController extends BaseController {
    @Resource
    private IMomentService momentService;
    @Resource
    private IUserService userService;
    @Resource
    private ICommentService commentService;
    @Resource
    private IApproveService approveService;

/*    *//**
     * 添加朋友圈状态
     *
     * @param token
     * @param moment
     * @return
     *//*
    @RequestMapping(value = "/saveMoment", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback saveMoment(String token, @RequestBody Moment moment) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (moment == null) {
            return returnCallback(false, null, "请您先填写您的状态内容！");
        }
        moment.setUserId(userId);
        moment.setGmtCreate(new Date());
        moment.setStatus(MomentConsts.SHOW_MODEL);
        momentService.saveMoment(moment);
        return returnCallback(true, "状态保存成功！", null);
    }*/

    /**
     * 添加朋友圈
     */
    @RequestMapping(value = "/saveMoment", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback saveMoment(String token, HttpServletRequest request) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }


        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            String content = multiRequest.getParameter("content");
            Moment moment = new Moment();
            moment.setContent(content);
            moment.setUserId(userId);
            moment.setGmtCreate(new Date());
            moment.setStatus(MomentConsts.SHOW_MODEL);

            StringBuilder sb = new StringBuilder();
            //获取multiRequest 中所有的文件名
            Iterator iterator = multiRequest.getFileNames();
            while (iterator.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iterator.next().toString());
                if (file != null) {
                    String fileName = file.getOriginalFilename();
                    Date currData = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String classPath = this.getClass().getClassLoader().getResource("").getPath();
                    String projectPath = classPath.substring(0, classPath.length() - "lib/".length()) + "webapps/WEB-INF/data";
                    String projectName = projectPath.substring(projectPath.lastIndexOf("/") + 1);
                    String filePath = projectPath + "/moment/" + userId + "/" + sdf.format(currData);  //文件夹存放路径
                    String relativePath = "/" + projectName + "/moment/" + userId + "/" + sdf.format(currData); //文件夹存放相对路径
                    //上传
                    try {
                        if (!FileUtil.isDirectory(filePath)) {
                            FileUtil.mkdir(filePath);
                        }
                        file.transferTo(new File(filePath + "-" + fileName));

                        if (sb.length() > 0){
                            sb.append(",");
                        }
                        sb.append(relativePath + "-" + fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return returnCallback(false, null, e.getMessage());
                    }
                }
            }
            moment.setPicture(sb.toString());
            momentService.saveMoment(moment);

        }

        return returnCallback(true, "发表成功", null);
    }


    /**
     * 删除个人动态
     *
     * @param token
     * @param momentId
     * @return
     */
    @RequestMapping(value = "/deleteMoment", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback deleteMoment(String token, Integer momentId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        Moment moment = momentService.getMomentById(momentId);
        if (moment == null) {
            return returnCallback(false, null, "找不到您要删除的动态");
        }
        momentService.deleteMoment(userId, momentId);
        approveService.deleteApprove(userId, momentId);
        commentService.deleteComment(userId, momentId);
        return returnCallback(true, "状态保存成功！", null);
    }

    /**
     * 隐藏个人动态
     *
     * @param token
     * @param momentId
     * @return
     */
    @RequestMapping(value = "/hideMoment", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback hideMoment(String token, Integer momentId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        Moment moment = momentService.getMomentById(momentId);
        if (moment == null) {
            return returnCallback(false, null, "找不到您要隐藏的动态");
        }
        moment.setStatus(MomentConsts.HIDE_MODEL);
        moment.setGmtModified(new Date());
        momentService.updateMoment(moment);
        return returnCallback(true, "隐藏个人动态成功成功！", null);
    }

//    /**
//     * 获得动态信息
//     *
//     * @param token
//     * @param momentId
//     * @return
//     */
//    @RequestMapping(value = "/getMomentById", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public Callback getMomentById(String token, Integer momentId) {
//        Integer userId = AppSessionHelper.getAppUserId(token);
//        if (userId == null) {
//            return returnCallback(false, null, "您还未登录，请您先登录!");
//        }
//        Moment momentVO = momentService.getMomentById(momentId);
//        if (momentVO == null) {
//            return returnCallback(false, null, "找不到您要查看的动态信息");
//        }
//        return returnCallback(true, momentVO, null);
//    }
/*
    *//**
     * 获得某个好友动态列表
     *
     * @param token
     * @return
     *//*
    @RequestMapping(value = "/listUserMoments", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listUserMoments(String token, Integer someOneId, Integer minId, Integer maxId, Integer pageSize) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        //minId：上拉加载更多，maxId下拉刷新，加载新数据
        if (minId != null && maxId != null || pageSize == null && minId == null && maxId == null) {
            return returnCallback(false, null, "参数配置错误！");
        }
        List<Moment> resultMomentListVO = new ArrayList<Moment>();
        if (someOneId == null) {
            someOneId = userId;
        }
        if (maxId == null && minId == null && pageSize != null) {
            //当minId,maxId都为空的时请求pageSize条最新数据
            Moment momentQry = new Moment();
            momentQry.setUserId(someOneId);
            PageHelper.startPage(1, pageSize);
            resultMomentListVO = momentService.listUserMoments(momentQry);
        }
        if (maxId != null) {
            Moment momentQry = new Moment();
            momentQry.setUserId(someOneId);
            momentQry.setMaxId(maxId);
            resultMomentListVO = momentService.listUserMoments(momentQry);
        }
        if (minId != null && pageSize != null) {
            Moment momentQry = new Moment();
            momentQry.setUserId(someOneId);
            momentQry.setMinId(minId);
            PageHelper.startPage(1, pageSize);
            resultMomentListVO = momentService.listUserMoments(momentQry);
        }
        for (Moment moment : resultMomentListVO) {
            List<Comment> commentList = commentService.listCommentsByMomentId(moment.getId());
            List<Approve> approveList = approveService.listApprovesByMomentId(moment.getId());
            Approve approveQry = new Approve();
            approveQry.setMomentId(moment.getId());
            approveQry.setUserId(userId);
            approveQry.setStatus(MomentConsts.SHOW_MODEL);
            Boolean isApproved = approveService.isApprove(approveQry);

            moment.setCommentList(commentList);
            moment.setApproveList(approveList);
            moment.setApproved(isApproved);
        }
        return returnCallback(true, resultMomentListVO, null);
    }*/

    /**
     * 朋友圈动态列表显示
     *
     * @param token
     * @param minId
     * @param maxId
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/listNewestMoments", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listNewestMoments(String token, Integer minId, Integer maxId, Integer pageSize) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        //minId：上拉加载更多pageSize条，maxId下拉刷新，加载新数据
        if (minId != null && maxId != null || pageSize == null && minId == null && maxId == null) {
            return returnCallback(false, null, "参数配置错误！");
        }
        List<Moment> resultMomentListVO = new ArrayList<Moment>();
/*        if (maxId == null && minId == null && pageSize != null) {
            //当minId,maxId都为空的时请求pageSize条数据
            Moment momentQry = new Moment();
            momentQry.setUserId(userId);
            momentQry.setMinId(pageSize + 1);
            resultMomentListVO = momentService.listNewestMoments(momentQry);
        }
        if (maxId != null) {
            Moment momentQry = new Moment();
            momentQry.setUserId(userId);
            momentQry.setMaxId(maxId);
            resultMomentListVO = momentService.listNewestMoments(momentQry);
        }
        if (minId != null && pageSize != null) {
            Moment momentQry = new Moment();
            momentQry.setUserId(userId);
            momentQry.setMinId(minId);
            resultMomentListVO = momentService.listMoreMoments(momentQry);
        }
        for (Moment moment : resultMomentListVO) {
            List<Comment> commentList = commentService.listCommentsByMomentId(moment.getId());
            List<Approve> approveList = approveService.listApprovesByMomentId(moment.getId());
            Approve approveQry = new Approve();
            approveQry.setMomentId(moment.getId());
            approveQry.setUserId(userId);
            approveQry.setStatus(MomentConsts.SHOW_MODEL);
            Boolean isApproved = approveService.isApprove(approveQry);

            moment.setCommentList(commentList);
            moment.setApproveList(approveList);
            moment.setApproved(isApproved);
        }*/

        return returnCallback(true, resultMomentListVO, null);
    }

    @RequestMapping(value = "/listFriendsMoments", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listFriendsMoments(String token, Integer minId, Integer maxId, Integer pageSize) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        //minId：上拉加载更多pageSize条，maxId下拉刷新，加载新数据
        if (maxId == null && pageSize == null || minId != null && maxId != null) {
            return returnCallback(false, null, "参数配置错误！");
        }
        List<Moment> resultMomentListVO = momentService.listFriendsMoment(userId, maxId, minId, pageSize);
        for (Moment moment : resultMomentListVO) {
            Approve approveQry = new Approve();
            approveQry.setMomentId(moment.getId());
            approveQry.setUserId(userId);
            approveQry.setStatus(MomentConsts.APPROVED);
            Boolean isApproved = approveService.isApprove(approveQry);

            moment.setApproved(isApproved);
        }

        return returnCallback(true, resultMomentListVO, null);
    }

    /**
     * 查看某个人的动态
     */
    @RequestMapping(value = "/listSomeOneMoments", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listSomeOneMoments(String token, Integer someOneId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        User someOne = userService.getByUserId(someOneId);
        if (someOne == null) {
            return returnCallback(false, null, "找不到您要查看的用户!");
        }
        List<Moment> momentList = momentService.listUserMomentByUserId(userId);
        return returnCallback(true, momentList, null);
    }

    /**
     * 朋友圈状态点赞(取消点赞) isApprove = true (false)
     *
     * @param token
     * @param momentId
     * @param isApprove
     * @return
     */
    @RequestMapping(value = "/approve", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback approve(String token, Integer momentId, Boolean isApprove) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        Moment moment = momentService.getMomentById(momentId);
        if (moment == null) {
            return returnCallback(false, null, "找不到您要点赞的动态!");
        }
        //点赞
        if (isApprove) {
            Approve approve = new Approve();
            approve.setGmtCreate(new Date());
            approve.setUserId(userId);
            approve.setMomentId(momentId);
            approve.setStatus(ApproveConsts.NORMAL_MODEL);
            approveService.saveApprove(approve);
            return returnCallback(true, "点赞成功!", null);
        }
        //取消点赞
        approveService.deleteApprove(userId, momentId);
        return returnCallback(true, "成功取消点赞!", null);
    }

    /**
     * 获得点赞好友信息
     *
     * @param token
     * @param momentId
     * @return
     */
    @RequestMapping(value = "/listApproveUser", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listApproveUser(String token, Integer momentId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        Moment moment = momentService.getMomentById(momentId);
        if (moment == null) {
            return returnCallback(false, null, "找不到动态!");
        }
        List<User> userList = approveService.listApproveUser(momentId);
        return returnCallback(true, userList, null);
    }

    /**
     * 评论好友动态
     */
    @RequestMapping(value = "/comment", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback comment(String token, @RequestBody Comment comment) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        Moment moment = momentService.getMomentById(comment.getMomentId());
        if (moment == null) {
            return returnCallback(false, null, "找不到动态!");
        }
        comment.setUserId(userId);
        comment.setStatus(CommentConsts.NORMAL_MODEL);
        comment.setGmtCreate(new Date());
        commentService.saveComment(comment);
        return returnCallback(true, "评论成功！", null);
    }

    /**
     * 删除对好友动态的评论
     *
     * @param token
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/deleteComment", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback deleteComment(String token, Integer commentId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            return returnCallback(false, null, "找不到要删除的评论!");
        }
        commentService.deleteById(commentId);
        return returnCallback(true, "删除成功！", null);
    }

    /**
     * 回复好友评论
     *
     * @param token
     * @param commentId
     * @param comment
     * @return
     */
    @RequestMapping(value = "/replyComment", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback replyComment(String token, Integer commentId, Comment comment) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        Comment commentDO = commentService.getCommentById(commentId);
        if (commentDO == null) {
            return returnCallback(false, null, "找不到要删除的评论!");
        }
        comment.setRepliedUserId(userId);
        comment.setGmtCreate(new Date());
        comment.setStatus(CommentConsts.NORMAL_MODEL);
        commentService.replyComment(comment);
        return returnCallback(true, "回复成功！", null);
    }

    /**
     * 删除回复好友评论
     *
     * @param token
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/deleteReplyComment", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback deleteReplyComment(String token, Integer commentId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        Comment commentDO = commentService.getCommentById(commentId);
        if (commentDO == null) {
            return returnCallback(false, null, "找不到要删除的评论!");
        }
        commentService.deleteReplyComment(commentId);
        return returnCallback(true, "删除成功！", null);
    }


}
