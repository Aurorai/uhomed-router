package com.uhomed.router.biz.service.impl;

import com.uhomed.router.biz.service.OnlineParamsService;
import com.uhomed.router.core.service.impl.BaseServiceImpl;
import com.uhomed.router.dal.dao.OnlineParamsDAO;
import com.uhomed.router.model.OnlineParams;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author
 * @version $$Id: , v 0.1 Exp $$
 */
@Service("onlineParamsService")
public class OnlineParamsServiceImpl extends BaseServiceImpl<OnlineParams> implements OnlineParamsService {
	
	@Resource
	private OnlineParamsDAO onlineParamsDAO;
	
	@PostConstruct
	public void init() {
		super.setBaseDao( onlineParamsDAO );
	}
}
