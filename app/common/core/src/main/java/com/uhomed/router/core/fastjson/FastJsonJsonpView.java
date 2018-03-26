/**
 * mario.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.uhomed.router.core.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * jsonp扩展
 * @author liming
 * @version $Id: FastJsonJsonpView.java, v 0.1 2017年3月16日 下午6:34:19 liming Exp $
 */
public class FastJsonJsonpView extends FastJsonJsonView {
	
	private static final String	DEFAULT_CONTENT_TYPE	= "application/javascript";
	
	/**
	 * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(Map,
	 *      HttpServletRequest,
	 *      HttpServletResponse)
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String _callback = request.getParameter( "_callback" );
		if (StringUtils.isNotEmpty( _callback )) {
			String json = JSON.toJSONString( super.filterModel( model ) );
			response.getOutputStream().write( (_callback + "(" + json + ")").getBytes( getCharset() ) );
			return;
		} else {
			response.getOutputStream().write( "_callback is null...".getBytes( getCharset() ) );
		}
	}
	
	public FastJsonJsonpView() {
		setContentType( DEFAULT_CONTENT_TYPE );
		setExposePathVariables( false );
	}
}
