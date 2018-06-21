package com.uhomed.router.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * baseService基础类
 * @author lim
 * @param <P>
 */
public interface BaseService<P> {
	/**
	 * 创建po
	 * @return
	 * @author lim
	 */
    Serializable create(P p);
	
	/**
	 * 批量添加
	 */
	int batchCreate(List<P> p);
	
	int batchUpdate(List<P> p);
	
	int batchDelete(List<P> p);
	/**
	 * 删除po
	 * @param soft
	 * @return 
	 * @author lim
	 */
	 Integer delete(P p, boolean soft);

	 /**
	  * 删除po
	  * @param params
	  * @return
	  */
	 int delete(Map<String, Object> params);
	/**
	 * 更新po
	 * @author lim
	 */
	 Integer update(P p);
	/**
	 * 查询分页
	 * @param params
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @author lim
	 */
	 List<P> queryByPage(Map<String, Object> params, int currPage, int pageSize);
	 /**
	  * 调用指定sql查询分页
	  * @param params
	  * @param sqlId
	  * @param currPage
	  * @param pageSize
	  * @return
	  */
	 List<P> queryByPage(Map<String, Object> params, String sqlId, int currPage, int pageSize);
	/**
	 * 查询总数
	 * @param params
	 * @return
	 * @author lim
	 */
	 Integer queryByCount(Map<String, Object> params);

	 /**
	  * 调用指定sql查询总数
	  * @param params
	  * @param sqlId
	  * @return
	  */
	 Integer queryByCount(Map<String, Object> params, String sqlId);
	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 * @author lim
	 */
	 P findById(Serializable id);
	 /**
	  * 恢复删除的
	  * @return
	  */
	 int restoreDel(P vo);

	 P queryByPO(Map<String, Object> params);
	 
	 String queryDual();
}
