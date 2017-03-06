package com.wyq.study.web.controller;

import com.wyq.study.pojo.Callback;
import com.wyq.study.service.ITotalRunService;
import com.wyq.study.utils.AppSessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 个人累计记录
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-05 下午2:25
 * 系统版本：1.0.0
 **/
@Controller
@RequestMapping("/total")
public class TotalRunController extends BaseController {
    @Autowired
    private ITotalRunService totalRunService;

    /**
     * 获得用户累计记录
     */
    @RequestMapping(value = "/getInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback getRunTotal(String token) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback("Error", "您还未登录，请您先登录");
        }
        

        return null;
    }
}
