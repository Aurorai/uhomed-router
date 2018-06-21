package com.uhomed.router.web.controller.admin.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Controller
public class LogonController {


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView logon(String uname,String pwd){
        ModelAndView mv = new ModelAndView();
        System.out.println(uname);
        System.out.println(pwd);
        return mv;
    }
}
