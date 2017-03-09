package com.wyq.study.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    @RequestMapping(value = {"/", "/index.htm"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String index(Model model) {
        return "index";
    }

}