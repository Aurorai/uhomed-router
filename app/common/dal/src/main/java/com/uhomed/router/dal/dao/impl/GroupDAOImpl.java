package com.uhomed.router.dal.dao.impl;

import org.springframework.stereotype.Repository;

import com.uhomed.router.core.dao.impl.BaseDAOImpl;
import com.uhomed.router.dal.dao.GroupDAO;
import com.uhomed.router.model.Group;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Repository("groupDAO")
public class GroupDAOImpl extends BaseDAOImpl<Group> implements GroupDAO {


    public GroupDAOImpl() {
        super(Group.class);
    }
}
