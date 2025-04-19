package com.example.ctdt.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }

    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static boolean isValidDate(String dateStr) {
        try {
            parseDate(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}