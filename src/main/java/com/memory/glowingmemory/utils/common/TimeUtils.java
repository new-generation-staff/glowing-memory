package com.memory.glowingmemory.utils.common;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zc
 */
public class TimeUtils {

    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private TimeUtils() {
    }

    public static LocalDateTime cstNow() {
        return LocalDateTime.now();
    }

    public static String dateTimeFormat(LocalDateTime ldt) {
        return DEFAULT_DATE_TIME_FORMATTER.format(ldt);
    }

    public static LocalDateTime parseDateTime(String timeStr) {
        return LocalDateTime.parse(timeStr, DEFAULT_DATE_TIME_FORMATTER);
    }

    public static LocalDate parseDate(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return LocalDate.parse(str, DEFAULT_DATE_FORMATTER);
    }
}
