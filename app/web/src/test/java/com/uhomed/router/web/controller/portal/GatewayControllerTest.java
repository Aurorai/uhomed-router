//package com.uhomed.router.web.controller.portal;
//
//import cn.hutool.http.HttpUtil;
//import com.uhomed.router.core.utils.SignatureUtils;
//import org.junit.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author ${user}
// * @version $$Id: ${file_name}, v 0.1 ${date} ${time} ${user} Exp $$
// */
//public class GatewayControllerTest {
//
//    @Test
//    public void gateway() {
//        String url = "http://localhost:8080/gateway";
//        Map<String,Object> params = new HashMap<>();
//        String method = "xkhstar.plaza.notes.list";
//        String timestamp = System.currentTimeMillis() + "";
//        String bizParams = "{\"moduleId\":\"1\",\"type\":\"ALL\",\"currPage\":\"1\",\"pageSize\":\"20\"}";
//        String version = "1.0.0";
//        String key = "xkhstar";
//        params.put("method",method);
//        params.put("version",version);
//        params.put("format","json");
//        params.put("timestamp",timestamp);
//        params.put("bizParams",bizParams);
//        params.put("sign", SignatureUtils.sign(method,version,bizParams,timestamp,key));
//        System.out.println(HttpUtil.get(url,params));
//    }
//}