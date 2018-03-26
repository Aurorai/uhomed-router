package com.uhomed.router.dal.dao.impl;

import org.springframework.stereotype.Repository;

import com.uhomed.router.core.dao.impl.BaseDAOImpl;
import com.uhomed.router.dal.dao.MethodDubboDAO;
import com.uhomed.router.model.MethodDubbo;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Repository("methodDubboDAO")
public class MethodDubboDAOImpl extends BaseDAOImpl<MethodDubbo> implements MethodDubboDAO {


    /*** 构造函数
     * @param */
    public MethodDubboDAOImpl() {
        super(MethodDubbo.class);
    }
}
