package com.increff.pos.util;

public class RoundUtil {
    public static Float round(Float dbl){
        if(dbl == null) return null;
        Float roundVal = (float) (Math.round(dbl*100.0)/100.0);
        return roundVal;
    }
//    public static boolean isEmpty(String s) {
//        return s == null || s.trim().length() == 0;
//    }
//
//    public static String toLowerCase(String s) {
//        return s == null ? null : s.trim().toLowerCase();
//    }

}