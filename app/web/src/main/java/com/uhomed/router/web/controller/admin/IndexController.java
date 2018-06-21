package com.uhomed.router.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("ccc","222");
        return mv;
    }

    @RequestMapping("/indexx")
    public ModelAndView indexx(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("ccc","222");
        return mv;
    }


    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView();
        return mv;
    }

}
