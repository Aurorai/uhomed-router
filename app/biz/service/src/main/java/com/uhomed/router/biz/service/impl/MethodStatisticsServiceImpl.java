package com.uhomed.router.biz.service.impl;

import com.uhomed.router.biz.service.MethodStatisticsService;
import com.uhomed.router.core.service.impl.BaseServiceImpl;
import com.uhomed.router.dal.dao.MethodStatisticsDAO;
import com.uhomed.router.model.MethodStatistics;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Service("methodStatistics")
public class MethodStatisticsServiceImpl extends BaseServiceImpl<MethodStatistics> implements MethodStatisticsService {

    @Resource
    private MethodStatisticsDAO methodStatisticsDAO;

    @PostConstruct
    public void init(){
        super.setBaseDao(methodStatisticsDAO);
    }

}
