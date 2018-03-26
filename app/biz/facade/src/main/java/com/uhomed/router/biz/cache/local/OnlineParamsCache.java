package com.uhomed.router.biz.cache.local;

import com.google.common.base.Joiner;
import com.uhomed.router.biz.facade.OnlineParamsFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @version $$Id: , v 0.1 Exp $$
 */
@Component("onlineParamsCache")
public class OnlineParamsCache {
	
	private ConcurrentHashMap<String, String> methodCache = new ConcurrentHashMap<>();

	@Resource
	private OnlineParamsFacade onlineParamsFacade;
	
	public String getValue(String group, String code) {
		String s = methodCache.get( this.buildKey( group, code ) );
		if(s == null){
			this.onlineParamsFacade.initCache();
			s = methodCache.get(this.buildKey( group, code ));
		}
		return s;
	}
	
	public void putValue(String group, String code, String value) {
		this.methodCache.put( this.buildKey( group, code ), value );
	}
	
	private String buildKey(String group, String code) {
		return Joiner.on( "_" ).join( group, code );
	}
	
}
