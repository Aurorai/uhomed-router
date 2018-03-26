package com.uhomed.router.web.controller.admin.method;

import com.uhomed.router.biz.facade.MethodFacade;
import com.uhomed.router.core.utils.PageModel;
import com.uhomed.router.view.MethodInfoView;
import com.uhomed.router.web.controller.admin.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;


/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Controller
@Scope("prototype")
public class MethodListController extends BaseController {


    @Resource
    private MethodFacade methodFacade;


    @RequestMapping(value = "/admin/method/list", method = { RequestMethod.GET })
    public ModelAndView list(String code,String likeApiMethodCode,String likeApiMethodName,String status,Integer currPage,Integer pageSize) {
        ModelAndView result = new ModelAndView();
        PageModel<MethodInfoView> page = this.methodFacade.methodList(code,likeApiMethodCode, likeApiMethodName, status, currPage, pageSize);
        result.addObject("data",page.getDatas());
        result.addObject("count",page.getCount());
        return result;
    }

}
