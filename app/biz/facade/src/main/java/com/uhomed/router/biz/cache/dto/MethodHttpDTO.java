package com.uhomed.router.biz.cache.dto;

import cn.hutool.http.Method;

/**
 * The type Method http dto.
 *
 * @author
 * @version $$Id : , v 0.1 Exp $$
 */
public class MethodHttpDTO implements MethodDTO {
	
	private String	url;
	
	/** GET OR POST */
	private Method	mode;
	
	private Long	timeout;
	
	/**
	 * Gets url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Sets url.
	 *
	 * @param url the url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Gets mode.
	 *
	 * @return the mode
	 */
	public Method getMode() {
		return mode;
	}
	
	/**
	 * Sets mode.
	 *
	 * @param mode the mode
	 */
	public void setMode(Method mode) {
		this.mode = mode;
	}
	
	/**
	 * Gets timeout.
	 *
	 * @return the timeout
	 */
	public Long getTimeout() {
		return timeout;
	}
	
	/**
	 * Sets timeout.
	 *
	 * @param timeout the timeout
	 */
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
}
