/**
 * mario.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.uhomed.router.web.controller.portal;

import cn.hutool.core.util.StrUtil;
import com.uhomed.router.biz.cache.dto.MethodCacheDTO;
import com.uhomed.router.biz.cache.local.MethodCache;
import com.uhomed.router.biz.config.BasicProperties;
import com.uhomed.router.biz.context.MethodTypeContext;
import com.uhomed.router.biz.exception.ParamException;
import com.uhomed.router.biz.exception.RequestException;
import com.uhomed.router.biz.request.ParamsUtil;
import com.uhomed.router.biz.request.Request;
import com.uhomed.router.biz.sync.SyncUtils;
import com.uhomed.router.biz.util.SignUtil;
import com.uhomed.router.core.context.RunStatusContext;
import com.uhomed.router.core.utils.logger.LoggerUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author liming
 * @version $Id: GatewayController.java, v 0.1 2017年7月2日 下午2:54:08 liming Exp $
 */
@Controller
public class GatewayController implements Serializable {
	
	@Resource
	private MethodCache			methodCache;
	@Resource
	private Request				requestDubbo;
	@Resource
	private Request requestHttp;
	
	private static final String	suff	= "json,jsonp";
	
	/**
	 * 网关入口
	 * 
	 * @param method
	 * @param bizParams
	 * @param version
	 * @param format
	 * @param sso
	 * @param timestamp
	 * @return
	 */
	@RequestMapping(value = "/gateway", method = { RequestMethod.POST, RequestMethod.GET })
	public Object gateway(String method, String bizParams, String version, String format, String sso, String timestamp,
			String client, String clientVersion, String router, HttpServletRequest httpServletRequest, String sign) {
		
		ModelAndView result = new ModelAndView();
		
		if (StringUtils.isEmpty( method )) {
			setFailMessage( result, "方法名不能为空！" );
			return result;
		}
		if (StringUtils.isEmpty( version )) {
			setFailMessage( result, "接口版本号不能为空！" );
			return result;
		}
		
		if (bizParams == null) {
			bizParams = "";
		}
		
		// 非开发环境，验证签名
		if (!(BasicProperties.RUN_STATUS == RunStatusContext.DEV)) {
			boolean v = SignUtil.verifiSign( method, version, bizParams, sign, timestamp, sso );
			if (!v) {
				setFailMessage( result, "signature error", "444444" );
				return result;
			}
		}
		// 默认为json
		if (format != null && suff.contains( format.toLowerCase() )) {
			setFailMessage( result, "format error", "444445" );
			return result;
		}
		
		MethodCacheDTO methodDTO = this.methodCache.getMethod( method, version );
		if (methodDTO == null) {
			setFailMessage( result, "该方法不存在或未开放！", "444446" );
			return result;
		}
		
		if (!httpServletRequest.getMethod().equalsIgnoreCase( methodDTO.getMode() )) {
			setFailMessage( result, "方法不存在！", "444447" );
			return result;
		}
		
		if (methodDTO.isVerifiSso()) {
			if (StrUtil.isEmpty( sso )) {
				setFailMessage( result, "sso不能为空！" );
				return result;
			}
		}
		
		if (!methodDTO.isStatus()) {
			setFailMessage( result, "该方法未开放！", "444448" );
			return result;
		}

		String lockKey = "";
		try {
			
			Map<String, Object> params = ParamsUtil.convertParams( bizParams, methodDTO, httpServletRequest );
			
			if (methodDTO.getConcurrent() != null && methodDTO.getConcurrent() == 1) {
				// 并发控制
				lockKey = SyncUtils.buildKey( method, version, sso );
				
				boolean isLock = SyncUtils.lock( lockKey, 2 );
				try {
					if (isLock) {
						Map<String,?> o = this.call(sso,params,methodDTO,router,httpServletRequest);
						if (o != null) {
							result.getModelMap().addAllAttributes( o );
							return result;
						}
					} else {
						setFailMessage( result, "请勿重复提交！", "400000" );
						return result;
					}
				} finally {
					if (isLock) {
						SyncUtils.unLock( lockKey );
					}
				}
				
			} else {
				Map<String,?> o = this.call(sso,params,methodDTO,router,httpServletRequest);
				if (o != null) {
					result.getModelMap().addAllAttributes( o );
					return result;
				}
			}
			
		} catch (ParamException e) {
			setFailMessage( result, e.getMessage(), "300000" );
		} catch (Exception e) {
			if(e instanceof RequestException){
				setFailMessage(result,"返回值类型错误！","300002");
			}
			else if (e.getMessage() != null) {
				if (e.getMessage().contains( "ClassCastException" )) {
					setFailMessage( result, "参数转换异常！", "300000" );
				} else if (e.getMessage().contains( "org.apache.ibatis.exceptions.PersistenceException" )
						|| e.getMessage().contains( "NullPointerException" )) {
					setFailMessage( result, "服务异常，请检查服务报错信息！", "300001" );
				} else {
					setFailMessage( result, "网络异常，请稍候再试！", "100001" );
					LoggerUtils.defaultPrint( e, "remote error!" );
				}
			}
		}
		return result;
	}
	
	private Map<String, ?> call(String sso, Map<String, Object> params, MethodCacheDTO methodDTO, String router,
			HttpServletRequest httpServletRequest) throws RequestException {

		Request request = null;
		// 判断方法类别
		if (methodDTO.getType() == MethodTypeContext.DUBBO) {
			request = requestDubbo;
		}else if(methodDTO.getType() == MethodTypeContext.HTTP){
			request = requestHttp;
		}
		Object o = request.request( sso, params, methodDTO, router, httpServletRequest );
		try {
			if (o != null) {
				return (Map<String, ?>) o;
			}
		} catch (ClassCastException e) {
			throw new RequestException("返回值类型错误！");
		}
		return null;
	}
	
	/**
	 * set信息
	 *
	 * @param message
	 */
	protected void setFailMessage(ModelAndView result, String message, String... code) {
		result.addObject( "success", false );
		result.addObject( "message", message );
		if (code.length != 0) {
			result.addObject( "code", code[ 0 ] );
		}
	}
	
	protected void setSuccessful(ModelAndView result, String message) {
		result.addObject( "success", true );
		result.addObject( "message", message );
		result.addObject( "code", "000000" );
	}
	
}
