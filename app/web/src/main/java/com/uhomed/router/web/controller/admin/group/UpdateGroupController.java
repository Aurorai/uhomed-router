package com.uhomed.router.web.controller.admin.group;

import com.uhomed.router.Result;
import com.uhomed.router.biz.facade.GroupFacade;
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
public class UpdateGroupController extends BaseController {

    @Resource
    private GroupFacade groupFacade;

    @RequestMapping(value = "/admin/group/update", method = { RequestMethod.POST })
    public ModelAndView update(String code, String name) {
        ModelAndView result = new ModelAndView();
        Result<String> r = this.groupFacade.updateGroup(code,name);
        super.setResultModel(result,r);
        return result;
    }
}
