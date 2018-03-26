package com.uhomed.router.web.controller.admin.method;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.uhomed.router.Result;
import com.uhomed.router.biz.facade.MethodFacade;
import com.uhomed.router.model.MethodParam;
import com.uhomed.router.web.controller.admin.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @version $$Id: , v 0.1 Exp $$
 */
@Controller
public class CreateMethodController extends BaseController {
	
	@Resource
	private MethodFacade methodFacade;
	
	@RequestMapping(value = "/admin/method/createDubbo", method = { RequestMethod.POST })
	public ModelAndView create(String groupCode, String apiMethodCode, String apiMethodName, String apiMethodVersion, String status, String verifiSso,
			String mode, String methodDesc, String classPath, String methodName, String params) {
        ModelAndView result = new ModelAndView();
        List<MethodParam> paramList = null;
        if(StrUtil.isNotEmpty(params)){
            paramList = JSONObject.parseArray(params,MethodParam.class);
        }

		Result<Integer> r = this.methodFacade.createMethodDubbo( groupCode, apiMethodCode, apiMethodName, apiMethodVersion, status, verifiSso, mode,
				methodDesc, classPath, methodName ,paramList);
		super.setResultModel( result, r );
		return result;
	}
}
