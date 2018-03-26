package com.uhomed.router.dal.dao.impl;

import org.springframework.stereotype.Repository;

import com.uhomed.router.core.dao.impl.BaseDAOImpl;
import com.uhomed.router.dal.dao.MethodParamDAO;
import com.uhomed.router.model.MethodParam;

/**
 * The type Method param dao.
 *
 * @author
 * @version $$Id : , v 0.1 Exp $$
 */
@Repository("methodParamDAO")
public class MethodParamDAOImpl extends BaseDAOImpl<MethodParam> implements MethodParamDAO {
	/***
	 * 构造函数
	 * 
	 * @param
	 */
	public MethodParamDAOImpl() {
		super( MethodParam.class );
	}
}
