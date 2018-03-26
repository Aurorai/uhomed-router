package com.uhomed.router.biz.request;

import com.alibaba.dubbo.rpc.RpcException;
import com.uhomed.router.biz.cache.dto.MethodCacheDTO;
import com.uhomed.router.biz.exception.ParamException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author
 * @version $$Id: , v 0.1 Exp $$
 */
public interface Request {
	
	/**
	 * 调用
	 * 
	 * @param sso
	 * @param bizParams
	 * @param methodDTO
	 * @return
	 * @throws ParamException
	 * @throws RpcException
	 */
	Object request(String sso, Map<String, Object> bizParams, MethodCacheDTO methodDTO, String router,
			HttpServletRequest request) throws ParamException, RpcException;
}
