package com.uhomed.router.web.controller;

import com.uhomed.router.biz.service.GroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@RestController
public class TestController {

    @Resource
    private GroupService groupService;

    @RequestMapping("/get")
    public String get(){
        System.out.println(this.groupService.queryByCount(null));
        System.out.println(this.groupService.queryByPage(new HashMap<>(),2,10));
        return "success";
    }
}
