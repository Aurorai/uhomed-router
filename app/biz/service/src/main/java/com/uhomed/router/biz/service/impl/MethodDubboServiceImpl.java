package com.uhomed.router.biz.service.impl;

import com.uhomed.router.biz.service.MethodDubboService;
import com.uhomed.router.core.service.impl.BaseServiceImpl;
import com.uhomed.router.dal.dao.MethodDubboDAO;
import com.uhomed.router.model.MethodDubbo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Service("methodDubboService")
public class MethodDubboServiceImpl extends BaseServiceImpl<MethodDubbo> implements MethodDubboService {

    @Resource
    private MethodDubboDAO methodDubboDAO;


    @PostConstruct
    public void init(){
        super.setBaseDao(methodDubboDAO);
    }
}
