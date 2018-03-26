package com.uhomed.router.biz.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uhomed.router.biz.service.MethodParamService;
import com.uhomed.router.core.service.impl.BaseServiceImpl;
import com.uhomed.router.dal.dao.MethodParamDAO;
import com.uhomed.router.model.MethodParam;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Service("methodParamService")
public class MethodParamServiceImpl extends BaseServiceImpl<MethodParam> implements MethodParamService {


    @Resource
    private MethodParamDAO methodParamDAO;

    @PostConstruct
    public void init(){
        super.setBaseDao(this.methodParamDAO);
    }
}
