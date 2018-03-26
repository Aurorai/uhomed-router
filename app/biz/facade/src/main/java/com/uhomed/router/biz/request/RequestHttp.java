package com.uhomed.router.biz.request;

import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.uhomed.router.biz.cache.dto.MethodCacheDTO;
import com.uhomed.router.biz.cache.dto.MethodHttpDTO;
import com.uhomed.router.biz.exception.ParamException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author
 * @version $$Id: , v 0.1 Exp $$
 */
@Component
public class RequestHttp implements Request {
	
	@Override
	public Object request(String sso, Map<String,Object> bizParams, MethodCacheDTO methodDTO, String router,
						  HttpServletRequest request) throws ParamException, RpcException {


		MethodHttpDTO http = (MethodHttpDTO) methodDTO.getMethodInfo();

		String r = null;
		if(http.getMode() == Method.GET){
			r = HttpUtil.get(http.getUrl(),bizParams,http.getTimeout().intValue());
		}else if(http.getMode() == Method.POST){
			r = HttpUtil.post(http.getUrl(),bizParams,http.getTimeout().intValue());
		}
		if(r != null){
			return JSON.parseObject(r, Map.class);
		}
		return null;
	}

}
