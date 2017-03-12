package com.wyq.study.web.controller;

import com.github.pagehelper.PageInfo;
import com.wyq.study.constant.ApproveConsts;
import com.wyq.study.constant.MomentConsts;
import com.wyq.study.pojo.Approve;
import com.wyq.study.pojo.Callback;
import com.wyq.study.pojo.Moment;
import com.wyq.study.service.IApproveService;
import com.wyq.study.service.ICommentService;
import com.wyq.study.service.IMomentService;
import com.wyq.study.service.IUserService;
import com.wyq.study.utils.AppSessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
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

    /**
     * 添加朋友圈状态
     *
     * @param token
     * @param moment
     * @return
     */
    @RequestMapping(value = "/saveMoment", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback saveMoment(String token, Moment moment) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        if (moment == null) {
            return returnCallback("Error", "请您先填写您的状态内容！");
        }
        moment.setUserId(userId);
        moment.setGmtCreate(new Date());
        moment.setStatus(MomentConsts.SHOW_MODEL);
        momentService.saveMoment(moment);
        return returnCallback("Success", "状态保存成功！");
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
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        Moment moment = momentService.getMomentById(momentId);
        if (moment == null) {
            return returnCallback("Error", "找不到您要删除的动态");
        }
        momentService.deleteMoment(momentId);
        return returnCallback("Success", "状态保存成功！");
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
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        Moment moment = momentService.getMomentById(momentId);
        if (moment == null) {
            return returnCallback("Error", "找不到您要隐藏的动态");
        }
        moment.setStatus(MomentConsts.HIDE_MODEL);
        moment.setGmtModified(new Date());
        momentService.updateMoment(moment);
        return returnCallback("Success", "隐藏个人动态成功成功！");
    }

    /**
     * 获得好友动态信息
     *
     * @param token
     * @param momentId
     * @return
     */
    @RequestMapping(value = "/getMomentById", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback getMomentById(String token, Integer momentId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        Moment momentVO = momentService.getMomentById(momentId);
        if (momentVO == null) {
            return returnCallback("Error", "找不到您要查看的动态信息");
        }
        return returnCallback("Success", momentVO);
    }

    /**
     * 获得好友最新动态信息
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/getNewestMoment", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback getMomentById(String token) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        Moment newestMomentVO = momentService.getNewestMoment(userId);
        return returnCallback("Success", newestMomentVO);
    }

    /**
     * 朋友圈动态列表显示
     *
     * @param token
     * @param minId
     * @param maxId
     * @param num
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/listNewestMoments", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listNewestMoments(String token, Integer minId, Integer maxId, Integer num, Integer pageSize) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        //minId：上拉加载更多，maxId下拉刷新，加载新数据
        if (minId != null && maxId != null) {
            return returnCallback("Error", "参数配置错误！");
        }
        if (minId != null && maxId == null || minId == null && maxId != null) {
            Moment momentQry = new Moment();
            momentQry.setUserId(userId);
            momentQry.setMinId(minId);
            momentQry.setMaxId(maxId);
            List<Moment> newestMomentListVO = momentService.listNewestMoments(momentQry);
            return returnCallback("Success", newestMomentListVO);
        }
        //常规加载更多
        if (num != null && pageSize != null) {
            Moment momentQry = new Moment();
            momentQry.setUserId(userId);
            momentQry.setNum(num);
            momentQry.setPageSize(pageSize);
            PageInfo pageInfoVO = momentService.listPageMoments(momentQry);
            return returnCallback("Success", pageInfoVO);
        }

        return returnCallback("Error", "参数配置错误!");
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
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        Moment moment = momentService.getMomentById(momentId);
        if (moment == null) {
            return returnCallback("Error", "找不到您要点赞的动态!");
        }
        //点赞
        if (isApprove) {
            Approve approve = new Approve();
            approve.setGmtCreate(new Date());
            approve.setApproveUserId(moment.getUserId());
            approve.setUserId(userId);
            approve.setMomentId(momentId);
            approve.setStatus(ApproveConsts.NORMAL_MODEL);
            approveService.saveApprove(approve);
            return returnCallback("Success", "点赞成功!");
        }
        //取消点赞
        approveService.deleteApprove(momentId);
        return returnCallback("Success", "成功取消点赞!");
    }

//    /**
//     * 获得点赞好友信息
//     */
//    @RequestMapping(value = "/listApproveUser", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public Callback listApproveUser(String token, Integer momentId) {
//        Integer userId = AppSessionHelper.getAppUserId(token);
//        if (userId == null) {
//            return returnCallback("Error", "您还未登录，请您先登录!");
//        }
//        Moment moment = momentService.getMomentById(momentId);
//        if (moment == null) {
//            return returnCallback("Error", "找不到动态!");
//        }
//        approveService.listApproveUser(momentId);
//
//        return returnCallback("Error", null);
//    }

    /**
     * 评论好友动态
     */

    /**
     * 删除好友动态评论
     */

    /**
     * 回复好友评论
     */

    /**
     * 删除回复好友评论
     */

}
