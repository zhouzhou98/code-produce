package com.knowledge.common.web.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtils {
    static String format = "yyyy-MM-dd";
    /**
     * 日期转为LocalDateTime
     *
     * @param date 日期
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(final Date date) {
        if (null == date) {
            return null;
        }
        final Instant instant = date.toInstant();
        final ZoneId zoneId = ZoneId.systemDefault();
        final LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }
    /**
     * 日期转为LocalDate
     *
     * @param date 日期
     * @return LocalDateTime
     */
    public static LocalDate dateToLocalDate(final Date date) {
        if (null == date) {
            return null;
        }
        final Instant instant = date.toInstant();
        final ZoneId zoneId = ZoneId.systemDefault();
        final LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }

    /**
     * LocalDateTime转为日期
     *
     * @param localDateTime LocalDateTime
     * @return 日期
     */
    public static Date localDateTimeToDate(final LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        final ZoneId zoneId = ZoneId.systemDefault();
        final ZonedDateTime zdt = localDateTime.atZone(zoneId);
        final Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * LocalDate转为日期
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(final LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        final ZoneId zoneId = ZoneId.systemDefault();
        final ZonedDateTime zdt = localDate.atStartOfDay().atZone(zoneId);
        final Date date = Date.from(zdt.toInstant());
        return date;
    }

    public static String parseLocalDateTimeFormat(final LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        final ZoneId zoneId = ZoneId.systemDefault();
        final ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return zdt.format(DateTimeFormatter.ofPattern(format));
    }

    public static String parseLocalDateFormat(final LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        final ZoneId zoneId = ZoneId.systemDefault();
        final ZonedDateTime zdt = localDate.atStartOfDay().atZone(zoneId);
        return zdt.format(DateTimeFormatter.ofPattern(format));
    }

    public static LocalDate parseStringToLocalDate(String date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, df);
    }
}
