package com.uhomed.router.dal.dao.impl;

import org.springframework.stereotype.Repository;

import com.uhomed.router.core.dao.impl.BaseDAOImpl;
import com.uhomed.router.dal.dao.MethodStatisticsDAO;
import com.uhomed.router.model.MethodStatistics;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Repository("methodStatisticsDAO")
public class MethodStatisticsDAOImpl extends BaseDAOImpl<MethodStatistics> implements MethodStatisticsDAO {


    public MethodStatisticsDAOImpl() {
        super(MethodStatistics.class);
    }
}
