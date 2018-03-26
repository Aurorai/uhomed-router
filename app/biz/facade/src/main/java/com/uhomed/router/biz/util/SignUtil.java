package com.uhomed.router.biz.util;

import com.uhomed.router.core.utils.SignatureUtils;

import cn.hutool.core.util.StrUtil;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
public class SignUtil {

    public static boolean verifiSign(String method,String version,String bizParams,String sign,String timestamp,String key){
        if(StrUtil.isEmpty(sign) || sign.length() != 32){
//            setFailMessage(result,"signature error","444446");
//            return result;
            return false;
        }else {

            if(System.currentTimeMillis() - Long.valueOf(timestamp) > (60 * 1000)){
//                setFailMessage(result,"request error","444445");
//                return result;
                return false;
            }

            String k = key;
            if(StrUtil.isEmpty(k)){
                k = "xkhstar";
            }
            String verifi = SignatureUtils.sign(method,version,bizParams,timestamp,k);
            if(!StrUtil.equalsIgnoreCase(verifi,sign)){
//                setFailMessage(result,"signature error","444444");
//                return result;
                return false;
            }

        }
        return true;
    }
}
