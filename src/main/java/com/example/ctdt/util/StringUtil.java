package com.example.ctdt.util;

public class StringUtil {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String trim(String str) {
        return str != null ? str.trim() : null;
    }

    public static String toUpperCaseFirstChar(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}