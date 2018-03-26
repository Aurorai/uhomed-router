package com.uhomed.router.biz.service.impl;

import com.uhomed.router.biz.service.MethodInfoService;
import com.uhomed.router.core.service.impl.BaseServiceImpl;
import com.uhomed.router.dal.dao.MethodInfoDAO;
import com.uhomed.router.model.MethodInfo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Service("methodInfoService")
public class MethodInfoServiceImpl extends BaseServiceImpl<MethodInfo> implements MethodInfoService {


    @Resource
    private MethodInfoDAO methodInfoDAO;

    @PostConstruct
    public void init(){
        super.setBaseDao(methodInfoDAO);
    }

}
