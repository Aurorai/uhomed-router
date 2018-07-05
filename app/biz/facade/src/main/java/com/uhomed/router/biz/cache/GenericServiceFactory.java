package com.uhomed.router.biz.cache;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.google.common.base.Joiner;
import com.uhomed.router.biz.cache.dto.MethodCacheDTO;
import com.uhomed.router.biz.cache.dto.MethodDubboDTO;
import com.uhomed.router.core.utils.logger.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @version $$Id: , v 0.1 Exp $$
 */
public class GenericServiceFactory {

	private static ConcurrentHashMap<String, GenericService>					GENERIC_SERVICE			= null;

	private static ConcurrentHashMap<String, ReferenceConfig<GenericService>>	REFERENCE_CONFIG_CACHE	= null;

	private static final Integer												DEFAULT_TIMEOUT			= 5000;

	private static final Logger													INIT_RPC_LOGGER			= LoggerFactory
			.getLogger(GenericServiceFactory.class);

	private static final String													DUBBO_NAME				= "gateway";

//	private static final String													REGISTER_CENTER			= "zookeeper";

	// private static final String ZOOKEEPER_CONNECTION = BasisConfig.ZOOKEEPER_IP;

	static {
		REFERENCE_CONFIG_CACHE = new ConcurrentHashMap<>();
		GENERIC_SERVICE = new ConcurrentHashMap<>();
	}

	/**
	 * 初始化gs，并把gs放入缓存
	 * 
	 * @param p
	 * @return
	 */
	public static GenericService buildGenericService(MethodCacheDTO p) {

		String genericKey = p.getId().toString();
		GenericService gs = GENERIC_SERVICE.get(genericKey);
		if (gs != null) {
			return gs;
		}

		MethodDubboDTO dubbo = (MethodDubboDTO) p.getMethodInfo();

		ApplicationConfig applicationConfig = new ApplicationConfig(DUBBO_NAME);
		//使用当前配置的注册中心
		RegistryConfig registryConfig = new RegistryConfig(dubbo.getRegisterAddress());
		registryConfig.setProtocol(dubbo.getRegisterType());
		//
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setCheck(false);
		consumerConfig.setSticky(true);

		try {
			String interfaceName;
			String dubboVersion;
			interfaceName = dubbo.getClassPath();
			dubboVersion = p.getApiMethodVersion();
			String key = Joiner.on('_').join(interfaceName, dubboVersion);
			ReferenceConfig<GenericService> reference = null;
			// 防止创建过多的ReferenceConfig, 因为api是基于方法的，而 GenericService 是基于dubbo服务提供的接口和版本的
//			if (REFERENCE_CONFIG_CACHE.containsKey(key)) {
//				reference = REFERENCE_CONFIG_CACHE.get(key);
//			} else {
				reference = new ReferenceConfig<>();
				reference.setInterface(interfaceName);
				reference.setGeneric(true);
				reference.setApplication(applicationConfig);
				reference.setRegistry(registryConfig);
				reference.setConsumer(consumerConfig);
//				reference.setVersion("1.0.0");
				reference.setVersion(p.getApiMethodVersion());
				// reference.setProtocol("dubbo");
				// reference.setVersion(dubboVersion);
				// reference.setLoadbalance("uhomed-loadbalance");

//				REFERENCE_CONFIG_CACHE.put(key, reference);
//			}

			List<MethodConfig> methods = reference.getMethods();
			if (methods == null) {
				methods = new ArrayList<>();
			}
			MethodConfig methodConfig = new MethodConfig();
			methodConfig.setName(dubbo.getMethodName());
			methodConfig.setTimeout(DEFAULT_TIMEOUT);
			methods.add(methodConfig);
			reference.setMethods(methods);
			gs = reference.get();
			if (gs == null) {
				throw new Exception("初始化gs失败！");
			}
			GENERIC_SERVICE.put(genericKey, gs);

			INIT_RPC_LOGGER.info("新增rpc远程接口-->" + genericKey);
		} catch (Exception e) {
			LoggerUtils.defaultPrint(e, "初始化rpc远程调用失败，原因：");
		}
		return gs;
	}

	public static void clear(String id) {
		GENERIC_SERVICE.remove(id);
	}

	public static GenericService getInstance(String id) {
		return GENERIC_SERVICE.get(id);
	}
}
