package com.jkxy.car.api.utils;

public class StringUtil {
    public static boolean isblank(String str){
        if (str==null||"".equals(str)){
            return true;
        }else{
            return false;
        }
    }
}
