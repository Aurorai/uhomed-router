package com.uhomed.router.web.controller.admin.method;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.uhomed.router.biz.cache.GenericServiceFactory;
import com.uhomed.router.biz.cache.dto.MethodCacheDTO;
import com.uhomed.router.biz.cache.dto.MethodDubboDTO;
import com.uhomed.router.biz.cache.dto.MethodParamCacheDTO;
import com.uhomed.router.biz.cache.local.MethodCache;
import com.uhomed.router.biz.facade.MethodFacade;
import com.uhomed.router.model.MethodInfo;
import com.uhomed.router.web.controller.admin.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @version $$Id: , v 0.1 Exp $$
 */
@Controller
@Scope("prototype")
public class TestMethodController extends BaseController {
	
	@Resource
	private MethodFacade methodFacade;
	@Resource
	private MethodCache methodCache;
	
	@RequestMapping(value = "/admin/method/test", method = { RequestMethod.POST })
	public ModelAndView test(Integer id) {

		ModelAndView result = new ModelAndView();
		MethodInfo method = this.methodFacade.queryById( id );
		
		MethodCacheDTO cache = this.methodCache.getMethod( method.getApiMethodCode(), method.getApiMethodVersion() );
		if (cache == null) {
			super.setFailMessage( result, "该方法不存在！" );
			return result;
		}
		
		List<String> keys = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		if (cache.getParams() != null) {
			for (int i = 0; i < cache.getParams().size(); i++) {
				MethodParamCacheDTO p = cache.getParams().get( i );
				keys.add( p.getClazzStr() );
				values.add( null );
			}
		}
		
		if (cache.isVerifiSso()) {
			RpcContext.getContext().setAttachment( "sso", "test-logon" );
		}
		
		GenericService genericService = GenericServiceFactory.getInstance( cache.getId().toString() );
		try {
			MethodDubboDTO dubbo = (MethodDubboDTO) cache.getMethodInfo();
			
			String[] ks = new String[keys.size()];
			keys.toArray( ks );
			Object o = genericService.$invoke( dubbo.getMethodName(), ks, values.toArray() );
			if (o != null) {
//				super.setSuccessful( result, "该方法可用！,返回值：" + JSON.toJSONString(o) );
				super.setSuccessful( result, "该方法可用！" );
			} else {
				super.setFailMessage( result, "该方法不可用！" );
			}
		} catch (RpcException e) {
			if(e.getMessage().contains("Please check if the providers have been started and registered.")){
				super.setFailMessage(result,"未在注册中心发现该接口");
				e.printStackTrace();
			}else if(e.getMessage().contains("Failed to invoke remote method")){
				super.setFailMessage(result,"发现服务提供者，未发现该方法");
				e.printStackTrace();
			}else {
				super.setFailMessage( result, e.getMessage() );
			}

		} catch (GenericException ex) {
			super.setSuccessful( result, "该方法可用！" );
		} catch (NullPointerException e) {
			super.setFailMessage( result, "gs不存在，该方法错误，无法生成" );
		} catch (Exception e) {
			super.setFailMessage( result, "方法不可用！" );
		}
		return result;
	}
}
