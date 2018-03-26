/**
 * mario.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.uhomed.router.core.utils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author liming
 * @version $Id: CardIdData.java, v 0.1 2015年3月20日 上午10:56:47 liming Exp $
 */
public class CardIdData {
	 // 省份    
    private String province;    
    // 城市    
    private String city;    
    // 区县    
    private String region;    
    // 年份    
    private int year;    
    // 月份    
    private int month;    
    // 日期    
    private int day;    
    // 性别    
    private String gender;    
    // 出生日期    
    private Date birthday;   
    //年龄  
    private int age;  
    
    private Map<String, String> cityCodeMap = new HashMap<String, String>() {    
		/**  */
		private static final long serialVersionUID = -6424672099459677335L;
		{    
            this.put("11", "北京");    
            this.put("12", "天津");    
            this.put("13", "河北");    
            this.put("14", "山西");    
            this.put("15", "内蒙古");    
            this.put("21", "辽宁");    
            this.put("22", "吉林");    
            this.put("23", "黑龙江");    
            this.put("31", "上海");    
            this.put("32", "江苏");    
            this.put("33", "浙江");    
            this.put("34", "安徽");    
            this.put("35", "福建");    
            this.put("36", "江西");    
            this.put("37", "山东");    
            this.put("41", "河南");    
            this.put("42", "湖北");    
            this.put("43", "湖南");    
            this.put("44", "广东");    
            this.put("45", "广西");    
            this.put("46", "海南");    
            this.put("50", "重庆");    
            this.put("51", "四川");    
            this.put("52", "贵州");    
            this.put("53", "云南");    
            this.put("54", "西藏");    
            this.put("61", "陕西");    
            this.put("62", "甘肃");    
            this.put("63", "青海");    
            this.put("64", "宁夏");    
            this.put("65", "新疆");    
            this.put("71", "台湾");    
            this.put("81", "香港");    
            this.put("82", "澳门");    
            this.put("91", "国外");    
        }    
    };    
        
    private CardIdUtils validator = null;    
        
    /**  
     * 通过构造方法初始化各个成员属性  
     */    
    public CardIdData(String idcard) {    
        try {    
            validator = new CardIdUtils();    
            if (validator.isValidatedAllIdcard(idcard)) {    
                if (idcard.length() == 15) {    
                    idcard = validator.convertIdcarBy15bit(idcard);    
                }    
                // 获取省份    
                String provinceId = idcard.substring(0, 2);    
                Set<String> key = this.cityCodeMap.keySet();    
                for (String id : key) {    
                    if (id.equals(provinceId)) {    
                        this.province = this.cityCodeMap.get(id);    
                        break;    
                    }    
                }    
    
                // 获取性别    
                String id17 = idcard.substring(16, 17);    
                if (Integer.parseInt(id17) % 2 != 0) {  
                	//男
                    this.gender = "M";    
                } else {
                	//女
                    this.gender = "W";    
                }    
    
                // 获取出生日期    
                String birthday = idcard.substring(6, 14);    
                Date birthdate = new SimpleDateFormat("yyyyMMdd")    
                        .parse(birthday);    
                this.birthday = birthdate;    
                GregorianCalendar currentDay = new GregorianCalendar();    
                currentDay.setTime(birthdate);    
                this.year = currentDay.get(Calendar.YEAR);    
                this.month = currentDay.get(Calendar.MONTH) + 1;    
                this.day = currentDay.get(Calendar.DAY_OF_MONTH);    
                  
                //获取年龄  
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy");  
                String year=simpleDateFormat.format(new Date());  
                this.age=Integer.parseInt(year)-this.year;  
            }
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }    
    
    /**  
     * @return the province  
     */    
    public String getProvince() {    
        return province;    
    }    
    
    /**  
     * @return the city  
     */    
    public String getCity() {    
        return city;    
    }    
    
    /**  
     * @return the region  
     */    
    public String getRegion() {    
        return region;    
    }    
    
    /**  
     * @return the year  
     */    
    public int getYear() {    
        return year;    
    }    
    
    /**  
     * @return the month  
     */    
    public int getMonth() {    
        return month;    
    }    
    
    /**  
     * @return the day  
     */    
    public int getDay() {    
        return day;    
    }    
    
    /**  
     * @return the gender  
     */    
    public String getGender() {    
        return gender;    
    }    
    
    /**  
     * @return the birthday  
     */    
    public Date getBirthday() {    
        return birthday;    
    }    
    
    @Override    
    public String toString() {    
        return "省份：" + this.province + ",性别：" + this.gender + ",出生日期："    
                + this.birthday;    
    }    
    
    public int getAge() {  
        return age;  
    }  
  
    public void setAge(int age) {  
        this.age = age;  
    }    
}
