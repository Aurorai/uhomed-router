package com.uhomed.router.web.controller.admin.method;

import cn.hutool.core.util.StrUtil;
import com.uhomed.router.biz.context.MethodTypeContext;
import com.uhomed.router.biz.facade.MethodFacade;
import com.uhomed.router.model.MethodInfo;
import com.uhomed.router.view.MethodParamView;
import com.uhomed.router.web.controller.admin.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Controller
@Scope("prototype")
public class MethodInfoController extends BaseController {

    @Resource
    private MethodFacade methodFacade;

    @RequestMapping(value = "/admin/method/info", method = { RequestMethod.GET })
    public ModelAndView info(Integer id) {
        ModelAndView result = new ModelAndView();
        MethodInfo methodInfo = this.methodFacade.queryById(id);
        result.addObject("data",methodInfo);
        if(StrUtil.equals(methodInfo.getType(), MethodTypeContext.DUBBO.toString())){
            result.addObject("dubbo",this.methodFacade.queryMethodDubboById(methodInfo.getId()));
        }

        List<MethodParamView> params = this.methodFacade.queryParams(id);
        result.addObject("params",params);

        return result;
    }
}
