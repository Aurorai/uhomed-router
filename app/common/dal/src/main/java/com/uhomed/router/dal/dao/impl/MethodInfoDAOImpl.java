package com.uhomed.router.dal.dao.impl;

import org.springframework.stereotype.Repository;

import com.uhomed.router.core.dao.impl.BaseDAOImpl;
import com.uhomed.router.dal.dao.MethodInfoDAO;
import com.uhomed.router.model.MethodInfo;



/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Repository("methodInfoDAO")
public class MethodInfoDAOImpl extends BaseDAOImpl<MethodInfo> implements MethodInfoDAO {

    public MethodInfoDAOImpl() {
        super(MethodInfo.class);
    }
}
