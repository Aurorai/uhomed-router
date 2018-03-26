package com.uhomed.router.biz.service.impl;

import com.uhomed.router.biz.service.GroupService;
import com.uhomed.router.core.service.impl.BaseServiceImpl;
import com.uhomed.router.dal.dao.GroupDAO;
import com.uhomed.router.model.Group;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Service("groupService")
public class GroupServiceImpl extends BaseServiceImpl<Group> implements GroupService {


    @Resource
    private GroupDAO groupDAO;


    @PostConstruct
    public void init(){
        super.setBaseDao(groupDAO);
    }


}
