package com.uhomed.router.biz.config;

import com.uhomed.router.core.context.RunStatusContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@Configuration
@PropertySource("file:${base.dir}/config/${spring.profiles.active}/basic.properties")
public class BasicProperties {


    /** zookeeper链接 */
    public static String ZOOKEEPER_IP;

    /** 服务运行状态 */
    public static RunStatusContext RUN_STATUS;

    @Value("${zookeeper.ip}")
    public void setZookeeperIP(String zookeeperIP){
        ZOOKEEPER_IP = zookeeperIP;
    }

    @Value("${run.status}")
    public void setRunStatus(String runStatus){
        RUN_STATUS.valueOf(runStatus);
    }




}
