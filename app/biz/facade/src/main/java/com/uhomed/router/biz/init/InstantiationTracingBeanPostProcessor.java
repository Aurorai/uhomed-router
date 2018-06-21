package com.uhomed.router.biz.init;

import com.uhomed.router.biz.facade.MethodFacade;
import com.uhomed.router.biz.facade.OnlineParamsFacade;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Component
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private MethodFacade methodFacade;

    @Resource
    private OnlineParamsFacade onlineParamsFacade;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        System.out.println("=====================   开始启动自定义服务   =====================");
        //初始化方法
        this.methodFacade.initCache();
        //初始化在线参数
        this.onlineParamsFacade.initCache();

		System.out.println("=====================   启动自定义服务完成！  =====================");

    }

}
