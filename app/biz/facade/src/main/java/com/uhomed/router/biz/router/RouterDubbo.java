//package com.uhomed.router.biz.router;
//
//import com.alibaba.dubbo.common.URL;
//import com.alibaba.dubbo.common.extension.ExtensionLoader;
//import com.alibaba.dubbo.registry.Registry;
//import com.alibaba.dubbo.registry.RegistryFactory;
//import com.uhomed.router.biz.cache.dto.MethodCacheDTO;
//import com.uhomed.router.biz.cache.dto.MethodDubboDTO;
//import com.uhomed.router.biz.config.BasicProperties;
//
//import cn.hutool.core.util.StrUtil;
//import org.springframework.stereotype.Component;
//
///**
// * @author
// * @version $$Id: , v 0.1 Exp $$
// */
//@Component("routerDuubbo")
//public class RouterDubbo implements Router {
//
////	private static String ROUTER_URL = "condition://0.0.0.0/{0}?category=routers&dynamic=false&enabled=true&force=true&name=ss&priority=12&router=condition&rule==> provider.host = \" {1}\"&runtime=true\"";
//
//	@Override
//	public void router(MethodCacheDTO methodDTO, String router) {
//		Registry registry = null;
//		URL url = null;
//		if (StrUtil.isNotEmpty( router )) {
//			MethodDubboDTO dubbo = (MethodDubboDTO) methodDTO.getMethodInfo();
//			RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader( RegistryFactory.class )
//					.getAdaptiveExtension();
//			registry = registryFactory.getRegistry(
//					URL.valueOf( "zookeeper://" + BasicProperties.ZOOKEEPER_IP ) );
//			url = URL.valueOf( "condition://0.0.0.0/" + dubbo.getClassPath()
//					+ "?category=routers&dynamic=false&enabled=true&force=true&name=ss&priority=12&router=condition&rule==> provider.host = "
//					+ router + "&runtime=true" );
//			registry.register( url );
//			try {
//				//等待zookeeper响应成功
//				Thread.sleep( 100l );
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}
