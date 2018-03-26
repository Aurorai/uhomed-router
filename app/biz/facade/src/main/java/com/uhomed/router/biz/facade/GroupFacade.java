package com.uhomed.router.biz.facade;


import com.uhomed.router.Result;
import com.uhomed.router.core.utils.PageModel;
import com.uhomed.router.view.GroupView;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
public interface GroupFacade {


    /**
     * 添加分组
     * @param code
     * @param name
     * @return
     */
    Result<String> createGroup(String code, String name);

    /**
     * 删除分组
     * @param code
     * @return
     */
    Result<String> deleteGroup(String code);

    /**
     * 更新分组
     * @param code
     * @param name
     * @return
     */
    Result<String> updateGroup(String code, String name);


    /**
     * 查询分组
     * @param likeCode
     * @param likeName
     * @param currPage
     * @param pageSize
     * @return
     */
    PageModel<GroupView> groupList(String likeCode, String likeName, Integer currPage, Integer pageSize);

}
