package com.uhomed.router.biz.request;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.common.utils.IOUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.TypeUtils;
import com.uhomed.router.biz.cache.dto.MethodCacheDTO;
import com.uhomed.router.biz.cache.dto.MethodParamCacheDTO;
import com.uhomed.router.biz.context.MethodParamContext;
import com.uhomed.router.biz.exception.ParamException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author
 * @version $$Id: , v 0.1 Exp $$
 */
public class ParamsUtil {
	
	/**
	 * 参数类型转换器
	 * 
	 * @param bizParams
	 * @param methodDTO
	 * @param request
	 * @return
	 * @throws ParamException
	 */
	public static Map<String, Object> convertParams(String bizParams, MethodCacheDTO methodDTO,
			HttpServletRequest request) throws ParamException {
		Map<String, Object> result = new HashMap<>();
		
		List<String> types = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		
		if (StrUtil.isEmpty( bizParams )) {
			bizParams = "{}";
		}
		
		if (CollectionUtil.isNotEmpty( methodDTO.getParams() )) {
			List<MethodParamCacheDTO> paramList = methodDTO.getParams();
			JSONObject json = null;
			try {
				json = JSONObject.parseObject( bizParams );
			} catch (Exception e) {
				throw new ParamException( "bizParams解析错误！" );
			}
			
			// 获取url后参数
			Map<String, String> urlParams = toMap( request.getQueryString() );
			for (MethodParamCacheDTO p : paramList) {
				types.add( p.getClazzStr() );
				// 获取到参数， 暂时忽视类型
				Object value = null;
				if (MethodParamContext.Resource.HEADERS == p.getResource()) {
					value = request.getHeader( p.getCode() );
				} else if (MethodParamContext.Resource.REQUEST_BODY == p.getResource()) {
					BufferedReader reader = null;
					try {
						if (request.getInputStream() != null) {
							reader = new BufferedReader( new InputStreamReader( request.getInputStream() ) );
							value = IOUtils.read( reader );
							
						}
					} catch (IOException e) {
						throw new ParamException( "requestBody获取失败！" );
					} finally {
						if (reader != null) {
							try {
								reader.close();
							} catch (IOException e) {
								throw new ParamException( "requestBody获取失败！" );
							}
						}
					}
				} else if (MethodParamContext.Resource.URL == p.getResource()) {
					value = urlParams.get( p.getCode() );
				} else if (MethodParamContext.Resource.BIZ_PARAMS == p.getResource()) {
					value = json.get( p.getCode() );
				}
				// 放入默认值
				if (StrUtil.isNotEmpty( p.getDefaultValue() ) && value == null) {
					value = p.getDefaultValue();
				}
				
				// 是否必传并且为空时直接抛出异常
				if (p.isRequire() && value == null) {
					throw new ParamException( p.getName() + "不能为空！" );
				}
				
				if (value != null) {
					if (p.getClazz() instanceof String) {
						// 兼容 前台传数字，后台字符串问题 {"test":123}
						String tempValue = TypeUtils.castToJavaBean( value, String.class );
						// 验证string长度
						if (p.getLength() != 0 && p.getLength() < tempValue.length()) {
							throw new ParamException( p.getName() + "长度大于" + p.getLength() + "！" );
						} else if (p.getMinLength() != 0 && tempValue.length() < p.getMinLength()) {
							throw new ParamException( p.getName() + "长度小于" + p.getLength() + "！" );
						}
						value = tempValue;
					} else if (p.getClazz() instanceof Number && !(value instanceof Number)) {
						// 兼容 {"test":"123"}
						try {
							value = TypeUtils.castToJavaBean( value, p.getClazz().getClass() );
						} catch (Exception e) {
							throw new ParamException( p.getName() + "为Number类型，请检查参数类型！" );
						}
					} else if (p.getClazz() instanceof Date) {
						// 兼容常用的时间类型
						try {
							value = TypeUtils.castToDate( value );
						} catch (Exception e) {
							throw new ParamException( p.getName() + "为时间类型，请检查参数类型！" );
						}
					} else if (p.getClazz() instanceof Boolean) {
						// 容错尝试转换0=false ，1=true
						try {
                            value = TypeUtils.castToBoolean(value);
						} catch (Exception e) {
							throw new ParamException( p.getName() + "为Boolean类型，请检查参数类型！" );
						}
					} else if (p.getClazz().getClass().getName().equalsIgnoreCase( Object.class.getName() )) {
						// 是否是object类型
						Map<String, Object> domain = JSON.parseObject( String.valueOf( value ),
								new TypeReference<Map<String, Object>>() {
								} );
						
						value = domain;
					} else if(p.getClazz() instanceof Collection){
						try {
							value = JSON.parseArray(value.toString(),Object.class);
//							value = TypeUtils.castToJavaBean(value,JSONArray.class);
						} catch (Exception e) {
							e.printStackTrace();
							throw new ParamException( p.getName() + "为ArrayList类型，请检查参数类型！" );
						}
					} else if(p.getClazz().getClass().getName().equalsIgnoreCase(Map.class.getName())){

					}
				}
				
				values.add( value );
			}
		}
		result.put( "types", types );
		result.put( "values", values );
		return result;
	}
	
	public static Map<String, String> toMap(String url) {
		Map<String, String> map = null;
		if (url != null && url.indexOf( "&" ) > -1 && url.indexOf( "=" ) > -1) {
			map = new HashMap<>();
			String[] arrTemp = url.split( "&", -1 );
			for (String str : arrTemp) {
				String[] qs = str.split( "=", -1 );
				map.put( qs[ 0 ], qs[ 1 ] );
			}
		}
		return map;
	}



}
