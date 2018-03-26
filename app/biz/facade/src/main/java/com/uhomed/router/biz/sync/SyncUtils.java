package com.uhomed.router.biz.sync;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @version $$Id: , v 0.1 Exp $$
 */
public class SyncUtils {
	
	private static ConcurrentHashMap<String, Long> syncMap = null;

	/** 默认锁定时间，5s */
	private static final Long DEFAULT_TIMEOUT = 5000l;
	
	static {
		syncMap = new ConcurrentHashMap<>();
	}

	/**
	 * 本地队列锁
	 * @param key 锁key
	 * @param timeout 超时时间，单位秒
	 * @return
	 */
	public static boolean lock(String key, int timeout) {
		long out = (timeout * 1000) + System.currentTimeMillis();
		do {
			Long t = syncMap.get( key );
			if (t == null) {
				syncMap.put( key, System.currentTimeMillis() );
                return true;
			}else if(t.compareTo(System.currentTimeMillis()) <= 0){
				//超时覆盖锁
				syncMap.put( key, System.currentTimeMillis() );
				return true;
			}
			try {
				Thread.sleep( 10l );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (System.currentTimeMillis() < out);
		return false;
	}

	/**
	 * 互斥锁
	 * @param key
	 * @return
	 */
	public static boolean lock(String key){
		if (syncMap.get( key ) == null) {
			syncMap.put( key, System.currentTimeMillis() + DEFAULT_TIMEOUT );
			return true;
		}
		return false;
	}

	/**
	 * 解锁
	 * @param key
	 */
	public static void unLock(String key){
	    syncMap.remove(key);
    }
	
	public static String buildKey(String method, String version, String sso) {
		return method + "_" + version + "_" + sso;
	}
	
}
