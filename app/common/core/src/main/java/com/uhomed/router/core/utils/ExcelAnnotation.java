/**
 * mario.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.uhomed.router.core.utils;

import java.lang.annotation.*;

/**
 * 
 * @author jing
 * @version $Id: ExcelAnnocation.java, v 0.1 2015年3月25日 下午5:45:17 jing Exp $
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited

public @interface ExcelAnnotation{
	/**
	 * 在Excel要显示的字段名
	 * @return
	 */
	public abstract String name() default "列";
}

