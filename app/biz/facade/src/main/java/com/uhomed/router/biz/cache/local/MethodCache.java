package com.uhomed.router.biz.cache.local;

import com.alibaba.druid.util.StringUtils;
import com.google.common.base.Joiner;
import com.uhomed.router.biz.cache.GenericServiceFactory;
import com.uhomed.router.biz.cache.dto.MethodCacheDTO;
import com.uhomed.router.biz.cache.dto.MethodDTO;
import com.uhomed.router.biz.cache.dto.MethodParamCacheDTO;
import com.uhomed.router.biz.context.MethodTypeContext;
import com.uhomed.router.biz.facade.MethodFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @version $$Id: , v 0.1 Exp $$
 */
@Component("methodCache")
public class MethodCache {
	
	private ConcurrentHashMap<String, MethodCacheDTO> methodCache = new ConcurrentHashMap<>();

	@Resource
	private MethodFacade methodFacade;
	
	public MethodCacheDTO getMethod(String apiMethodCode, String apiMethodVersion) {
		MethodCacheDTO method = methodCache.get( this.buildKey( apiMethodCode, apiMethodVersion ) );
		if(method == null){
			return this.methodFacade.getMethodCacheDTO(apiMethodCode,apiMethodVersion);
		}
		return method;
	}
	
	public MethodCacheDTO putMethod(Integer id, String apiMethodCode, String apiMethodVersion, String status, String verifiSso, MethodTypeContext type,
									String mode, List<MethodParamCacheDTO> params, MethodDTO method, boolean cache, Integer second, Integer concurrent) {
		MethodCacheDTO dto = new MethodCacheDTO( id, apiMethodCode, apiMethodVersion, StringUtils.equals( status, "Y" ),
				StringUtils.equals( verifiSso, "Y" ), type, mode, params, method,cache,second ,concurrent);
		GenericServiceFactory.clear(id.toString());
		GenericServiceFactory.buildGenericService( dto );
		this.methodCache.put( this.buildKey( apiMethodCode, apiMethodVersion ), dto );
		return dto;
	}
	
	public String buildKey(String apiMethodCode, String apiMethodVersion) {
		return Joiner.on( "_" ).join( apiMethodCode, apiMethodVersion );
	}
	
}
