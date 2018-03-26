package com.uhomed.router.dal.dao.impl;

import com.uhomed.router.core.dao.impl.BaseDAOImpl;
import com.uhomed.router.dal.dao.GroupDAO;
import com.uhomed.router.model.Group;
import org.springframework.stereotype.Repository;

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
