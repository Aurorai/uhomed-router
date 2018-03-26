package com.uhomed.router.core.utils;

import com.uhomed.router.core.utils.encrypt.MD5;

import java.text.MessageFormat;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
public class SignatureUtils {


    /**
     * 签名规则
     * <pre></pre>
     * @param methodName
     * @param version
     * @param bizParams
     * @param key
     * @return
     */
    public static String sign(String methodName,String version,String bizParams,String timestamp,String key){
        String base = "{0}_{1}_{2}_{3}";
        return MD5.toMD5(MD5.toMD5(MessageFormat.format(methodName,version,bizParams,timestamp)) + key);
    }
}
