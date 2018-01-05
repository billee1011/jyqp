package com.zk.netty.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    private static final int[] dayArray = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * 应用日期格式，如为空则使用默认日期格式
     *
     * @param pattern
     * @return SimpleDateFormat
     */
    public static SimpleDateFormat applyPattern(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        if (pattern != null) { // 如果不为空则使用指定日期格式
            dateFormat.applyPattern(pattern);
        } else { // 否则使用默认日期格式
            dateFormat.applyPattern(DEFAULT_DATE_PATTERN);
        }
        return dateFormat;
    }

    /**
     * 把日期字符串转换为指定格式(指定日期格式，否则使用默认格式)
     *
     * @param strDate
     * @param oldPattern
     * @param newPattern
     * @return String
     */
    public static String stringFormat(String strDate, String oldPattern, String newPattern) {
        String str = null;
        try {
            if (strDate != null) {
                str = dateToStringFormat(stringToDateFormat(strDate, oldPattern), newPattern);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 把日期转换为字符串格式(指定日期格式，否则使用默认格式)
     *
     * @param date
     * @param pattern
     * @return String
     */
    public static String dateToStringFormat(Date date, String pattern) {
        String str = null;
        try {
            SimpleDateFormat dateFormat = applyPattern(pattern); // 应用日期格式
            str = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 把日期字符串转换为日历格式(指定日期格式，否则使用默认格式)
     *
     * @param strDate
     * @param pattern
     * @return Calendar
     */
    public static Calendar stringToCalendarFormat(String strDate, String pattern) {
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat dateFormat = applyPattern(pattern); // 应用日期格式
            Date date = dateFormat.parse(strDate);
            cal.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal;
    }

    /**
     * 把日期字符串转换为日期格式(指定日期格式，否则使用默认格式)
     *
     * @param strDate
     * @param pattern
     * @return Date
     */
    public static Date stringToDateFormat(String strDate, String pattern) {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = applyPattern(pattern); // 应用日期格式
            date = dateFormat.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 把日期转为中文数字格式
     *
     * @param date 日期字符串
     * @return String 中文日期
     */
    public static String dateToStringFormatCN(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String year = numToUpper(calendar.get(Calendar.YEAR));
        String month = monthToUppder(calendar.get(Calendar.MONTH) + 1);
        String day = dayToUppder(calendar.get(Calendar.DAY_OF_MONTH));
        return year + "年" + month + "月" + day + "日";
    }

    /**
     * 返回指定月份的最后一天
     *
     * @param month
     * @return int
     */
    public static int getLastDayOfMonth(int month) {
        if (month < 1 || month > 12) {
            return -1;
        }
        int retn = 0;
        if (month == 2) {
            if (isLeapYear()) {
                retn = 29;
            } else {
                retn = dayArray[month - 1];
            }
        } else {
            retn = dayArray[month - 1];
        }
        return retn;
    }

    /**
     * 返回指定年份、月份的最后一天
     *
     * @param year
     * @param month
     * @return int
     */
    public static int getLastDayOfMonth(int year, int month) {
        if (month < 1 || month > 12) {
            return -1;
        }
        int retn = 0;
        if (month == 2) {
            if (isLeapYear(year)) {
                retn = 29;
            } else {
                retn = dayArray[month - 1];
            }
        } else {
            retn = dayArray[month - 1];
        }
        return retn;
    }

    /**
     * 判断当前年份是否是闰年
     *
     * @return boolean
     */
    public static boolean isLeapYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 根据指定年份判断是否是闰年
     *
     * @param year
     * @return boolean
     */
    public static boolean isLeapYear(int year) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年 3.能被4整除同时能被100整除则不是闰年
         */
        if ((year % 400) == 0) {
            return true;
        } else {
            return (year % 4) == 0 && (year % 100) != 0;
        }
    }

    /**
     * 判断指定日期的年份是否是闰年
     *
     * @param date 指定日期。
     * @return 是否闰年
     */
    public static boolean isLeapYear(Date date) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年 3.能被4整除同时能被100整除则不是闰年
         */
        // int year = date.getYear();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        int year = gc.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 得到指定日期的前一个工作日
     *
     * @param date 指定日期。
     * @return 指定日期的前一个工作日
     */
    public static Date getPreviousWeekDay(Date date) {
        /**
         * 详细设计： 1.如果date是星期日，则减3天 2.如果date是星期六，则减2天 3.否则减1天
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return getPreviousWeekDay(gc);
    }

    public static Date getPreviousWeekDay(Calendar cal) {
        /**
         * 详细设计： 1.如果date是星期日，则减3天 2.如果date是星期六，则减2天 3.否则减1天
         */
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case (Calendar.MONDAY):
                cal.add(Calendar.DATE, -3);
                break;
            case (Calendar.SUNDAY):
                cal.add(Calendar.DATE, -2);
                break;
            default:
                cal.add(Calendar.DATE, -1);
                break;
        }
        return cal.getTime();
    }

    /**
     * 得到指定日期的后一个工作日
     *
     * @param date 指定日期。
     * @return 指定日期的后一个工作日
     */
    public static Date getNextWeekDay(Date date) {
        /**
         * 详细设计： 1.如果date是星期五，则加3天 2.如果date是星期六，则加2天 3.否则加1天
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        switch (gc.get(Calendar.DAY_OF_WEEK)) {
            case (Calendar.FRIDAY):
                gc.add(Calendar.DATE, 3);
                break;
            case (Calendar.SATURDAY):
                gc.add(Calendar.DATE, 2);
                break;
            default:
                gc.add(Calendar.DATE, 1);
                break;
        }
        return gc.getTime();
    }

    public static Calendar getNextWeekDay(Calendar gc) {
        /**
         * 详细设计： 1.如果date是星期五，则加3天 2.如果date是星期六，则加2天 3.否则加1天
         */
        switch (gc.get(Calendar.DAY_OF_WEEK)) {
            case (Calendar.FRIDAY):
                gc.add(Calendar.DATE, 3);
                break;
            case (Calendar.SATURDAY):
                gc.add(Calendar.DATE, 2);
                break;
            default:
                gc.add(Calendar.DATE, 1);
                break;
        }
        return gc;
    }

    /**
     * 取得指定日期的下一个月的最后一天
     *
     * @param date 指定日期。
     * @return 指定日期的下一个月的最后一天
     */
    public static Date getLastDayOfNextMonth(Date date) {
        /**
         * 详细设计： 1.调用getNextMonth设置当前时间 2.以1为基础，调用getLastDayOfMonth
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.setTime(DateUtils.getNextMonth(gc.getTime()));
        gc.setTime(DateUtils.getLastDayOfMonth(gc.getTime()));
        return gc.getTime();
    }

    /**
     * 取得指定日期的下一个星期的最后一天
     *
     * @param date 指定日期。
     * @return 指定日期的下一个星期的最后一天
     */
    public static Date getLastDayOfNextWeek(Date date) {
        /**
         * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getLastDayOfWeek
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.setTime(DateUtils.getNextWeek(gc.getTime()));
        gc.setTime(DateUtils.getLastDayOfWeek(gc.getTime()));
        return gc.getTime();
    }

    /**
     * 取得指定日期的下一个月的第一天
     *
     * @param date 指定日期。
     * @return 指定日期的下一个月的第一天
     */
    public static Date getFirstDayOfNextMonth(Date date) {
        /**
         * 详细设计： 1.调用getNextMonth设置当前时间 2.以1为基础，调用getFirstDayOfMonth
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.setTime(DateUtils.getNextMonth(gc.getTime()));
        gc.setTime(DateUtils.getFirstDayOfMonth(gc.getTime()));
        return gc.getTime();
    }

    public static Calendar getFirstDayOfNextMonth(Calendar gc) {
        /**
         * 详细设计： 1.调用getNextMonth设置当前时间 2.以1为基础，调用getFirstDayOfMonth
         */
        gc.setTime(DateUtils.getNextMonth(gc.getTime()));
        gc.setTime(DateUtils.getFirstDayOfMonth(gc.getTime()));
        return gc;
    }

    /**
     * 取得指定日期的下一个星期的第一天
     *
     * @param date 指定日期。
     * @return 指定日期的下一个星期的第一天
     */
    public static Date getFirstDayOfNextWeek(Date date) {
        /**
         * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getFirstDayOfWeek
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.setTime(DateUtils.getNextWeek(gc.getTime()));
        gc.setTime(DateUtils.getFirstDayOfWeek(gc.getTime()));
        return gc.getTime();
    }

    public static Calendar getFirstDayOfNextWeek(Calendar gc) {
        /**
         * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getFirstDayOfWeek
         */
        gc.setTime(DateUtils.getNextWeek(gc.getTime()));
        gc.setTime(DateUtils.getFirstDayOfWeek(gc.getTime()));
        return gc;
    }

    /**
     * 取得指定日期的下一个月
     *
     * @param date 指定日期。
     * @return 指定日期的下一个月
     */
    public static Date getNextMonth(Date date) {
        /**
         * 详细设计： 1.指定日期的月份加1
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.MONTH, 1);
        return gc.getTime();
    }

    public static Calendar getNextMonth(Calendar gc) {
        /**
         * 详细设计： 1.指定日期的月份加1
         */
        gc.add(Calendar.MONTH, 1);
        return gc;
    }

    /**
     * 取得指定日期的下一天
     *
     * @param date 指定日期。
     * @return 指定日期的下一天
     */
    public static Date getNextDay(Date date) {
        /**
         * 详细设计： 1.指定日期加1天
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.DATE, 1);
        return gc.getTime();
    }

    public static Calendar getNextDay(Calendar gc) {
        /**
         * 详细设计： 1.指定日期加1天
         */
        gc.add(Calendar.DATE, 1);
        return gc;
    }

    /**
     * 取得指定日期的下一个星期
     *
     * @param date 指定日期。
     * @return 指定日期的下一个星期
     */
    public static Date getNextWeek(Date date) {
        /**
         * 详细设计： 1.指定日期加7天
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.DATE, 7);
        return gc.getTime();
    }

    public static Calendar getNextWeek(Calendar gc) {
        /**
         * 详细设计： 1.指定日期加7天
         */
        gc.add(Calendar.DATE, 7);
        return gc;
    }

    /**
     * 取得指定日期的所处星期的最后一天
     *
     * @param date 指定日期。
     * @return 指定日期的所处星期的最后一天
     */
    public static Date getLastDayOfWeek(Date date) {
        /**
         * 详细设计： 1.如果date是星期日，则加6天 2.如果date是星期一，则加5天 3.如果date是星期二，则加4天 4.如果date是星期三，则加3天 5.如果date是星期四，则加2天 6.如果date是星期五，则加1天 7.如果date是星期六，则加0天
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        switch (gc.get(Calendar.DAY_OF_WEEK)) {
            case (Calendar.SUNDAY):
                gc.add(Calendar.DATE, 6);
                break;
            case (Calendar.MONDAY):
                gc.add(Calendar.DATE, 5);
                break;
            case (Calendar.TUESDAY):
                gc.add(Calendar.DATE, 4);
                break;
            case (Calendar.WEDNESDAY):
                gc.add(Calendar.DATE, 3);
                break;
            case (Calendar.THURSDAY):
                gc.add(Calendar.DATE, 2);
                break;
            case (Calendar.FRIDAY):
                gc.add(Calendar.DATE, 1);
                break;
            case (Calendar.SATURDAY):
                gc.add(Calendar.DATE, 0);
                break;
        }
        return gc.getTime();
    }

    /**
     * 取得指定日期的所处星期的第一天
     *
     * @param date 指定日期。
     * @return 指定日期的所处星期的第一天
     */
    public static Date getFirstDayOfWeek(Date date) {
        /**
         * 详细设计： 1.如果date是星期日，则减0天 2.如果date是星期一，则减1天 3.如果date是星期二，则减2天 4.如果date是星期三，则减3天 5.如果date是星期四，则减4天 6.如果date是星期五，则减5天 7.如果date是星期六，则减6天
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        switch (gc.get(Calendar.DAY_OF_WEEK)) {
            case (Calendar.SUNDAY):
                gc.add(Calendar.DATE, 0);
                break;
            case (Calendar.MONDAY):
                gc.add(Calendar.DATE, -1);
                break;
            case (Calendar.TUESDAY):
                gc.add(Calendar.DATE, -2);
                break;
            case (Calendar.WEDNESDAY):
                gc.add(Calendar.DATE, -3);
                break;
            case (Calendar.THURSDAY):
                gc.add(Calendar.DATE, -4);
                break;
            case (Calendar.FRIDAY):
                gc.add(Calendar.DATE, -5);
                break;
            case (Calendar.SATURDAY):
                gc.add(Calendar.DATE, -6);
                break;
        }
        return gc.getTime();
    }

    public static Calendar getFirstDayOfWeek(Calendar gc) {
        /**
         * 详细设计： 1.如果date是星期日，则减0天 2.如果date是星期一，则减1天 3.如果date是星期二，则减2天 4.如果date是星期三，则减3天 5.如果date是星期四，则减4天 6.如果date是星期五，则减5天 7.如果date是星期六，则减6天
         */
        switch (gc.get(Calendar.DAY_OF_WEEK)) {
            case (Calendar.SUNDAY):
                gc.add(Calendar.DATE, 0);
                break;
            case (Calendar.MONDAY):
                gc.add(Calendar.DATE, -1);
                break;
            case (Calendar.TUESDAY):
                gc.add(Calendar.DATE, -2);
                break;
            case (Calendar.WEDNESDAY):
                gc.add(Calendar.DATE, -3);
                break;
            case (Calendar.THURSDAY):
                gc.add(Calendar.DATE, -4);
                break;
            case (Calendar.FRIDAY):
                gc.add(Calendar.DATE, -5);
                break;
            case (Calendar.SATURDAY):
                gc.add(Calendar.DATE, -6);
                break;
        }
        return gc;
    }

    /**
     * 取得指定日期的所处月份的最后一天
     *
     * @param date 指定日期。
     * @return 指定日期的所处月份的最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        /**
         * 详细设计： 1.如果date在1月，则为31日 2.如果date在2月，则为28日 3.如果date在3月，则为31日 4.如果date在4月，则为30日 5.如果date在5月，则为31日 6.如果date在6月，则为30日 7.如果date在7月，则为31日 8.如果date在8月，则为31日 9.如果date在9月，则为30日 10.如果date在10月，则为31日
         * 11.如果date在11月，则为30日 12.如果date在12月，则为31日 1.如果date在闰年的2月，则为29日
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        switch (gc.get(Calendar.MONTH)) {
            case 0:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 1:
                gc.set(Calendar.DAY_OF_MONTH, 28);
                break;
            case 2:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 3:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 4:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 5:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 6:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 7:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 8:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 9:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 10:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 11:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
        }
        // 检查闰年
        if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY) && (isLeapYear(gc.get(Calendar.YEAR)))) {
            gc.set(Calendar.DAY_OF_MONTH, 29);
        }
        return gc.getTime();
    }

    public static Calendar getLastDayOfMonth(Calendar gc) {
        /**
         * 详细设计： 1.如果date在1月，则为31日 2.如果date在2月，则为28日 3.如果date在3月，则为31日 4.如果date在4月，则为30日 5.如果date在5月，则为31日 6.如果date在6月，则为30日 7.如果date在7月，则为31日 8.如果date在8月，则为31日 9.如果date在9月，则为30日 10.如果date在10月，则为31日
         * 11.如果date在11月，则为30日 12.如果date在12月，则为31日 1.如果date在闰年的2月，则为29日
         */
        switch (gc.get(Calendar.MONTH)) {
            case 0:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 1:
                gc.set(Calendar.DAY_OF_MONTH, 28);
                break;
            case 2:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 3:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 4:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 5:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 6:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 7:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 8:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 9:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
            case 10:
                gc.set(Calendar.DAY_OF_MONTH, 30);
                break;
            case 11:
                gc.set(Calendar.DAY_OF_MONTH, 31);
                break;
        }
        // 检查闰年
        if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY) && (isLeapYear(gc.get(Calendar.YEAR)))) {
            gc.set(Calendar.DAY_OF_MONTH, 29);
        }
        return gc;
    }

    /**
     * 取得指定日期的所处月份的第一天
     *
     * @param date 指定日期。
     * @return 指定日期的所处月份的第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        /**
         * 详细设计： 1.设置为1号
         */
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        return gc.getTime();
    }

    public static Calendar getFirstDayOfMonth(Calendar gc) {
        /**
         * 详细设计： 1.设置为1号
         */
        gc.set(Calendar.DAY_OF_MONTH, 1);
        return gc;
    }

    /**
     * 根据给定日期获得当天最少时间
     *
     * @param date
     * @return Date
     */
    public static Date getMinDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date minDate = cal.getTime();
        return minDate;
    }

    /**
     * 根据给定日期获得当天最大时间
     *
     * @param date
     * @return Date
     */
    public static Date getMaxDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date maxDate = cal.getTime();
        return maxDate;
    }

    /**
     * 判断2个日期时间相差的秒数
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 计算结果
     */
    public static Long timeDiffSecond(Date startTime, Date endTime) {
        long start = startTime.getTime();
        long end = endTime.getTime();
        long second = (end - start) / 1000;
        return second;
    }

    /**
     * 判断2个毫秒时间相差的秒数
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 计算结果
     */
    public static Long timeDiffSecond(long startTime, long endTime) {
        long second = (endTime - startTime) / 1000;
        return second;
    }

    /**
     * 判断是否为日期时间
     *
     * @param strDateTime
     * @return boolean
     */
    public static boolean isDateTime(String strDateTime) {
        // 12小时制
        // String regex =
        // "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))((\\s(((0?[1-9])|(1[0-2]))\\:([0-5][0-9])((\\s)|(\\:([0-5][0-9])\\s))([AM|PM|am|pm]{2,2}))))?$";
        // 24小时制
        String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|(1[0-9])|(2[0-3]))\\:([0-5][0-9])((\\s)|(\\:([0-5][0-9])))))?$";
        boolean result = strDateTime.matches(regex);
        return result;
    }

    /**
     * 判断是否为日期
     *
     * @param strDate
     * @return boolean
     */
    public static boolean isDate(String strDate) {
        String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        boolean result = strDate.matches(regex);
        return result;
    }

    /**
     * 计算日期加天数得到新日期
     *
     * @param date 日期
     * @param day 天数
     * @return 日期
     */
    public static Date addDay(Date date, Integer day) {
        Long time = date.getTime();
        time += day.longValue() * 24 * 60 * 60 * 1000;
        return new Date(time);
    }

    /**
     * 计算日期加秒数得到新日期
     *
     * @param date 日期
     * @param seconde 秒数
     * @return 日期
     */
    public static Date addSecond(Date date, Integer seconde) {
        Long time = date.getTime();
        time += seconde * 1000;
        return new Date(time);
    }
    
    /**
     * 计算日期加分数得到新日期
     *
     * @param date 日期
     * @param seconde 秒数
     * @return 日期
     */
    public static Date addMin(Date date, Integer minute) {
        Long time = date.getTime();
        time += minute*60* 1000;
        return new Date(time);
    }

    /**
     * 将数字转化为中文数字
     *
     * @param num 阿拉伯数字
     * @return 中文数字
     */
    public static String numToUpper(int num) {
        // String cnNum[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        String cnNum[] = {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        char[] oldStr = String.valueOf(num).toCharArray();
        String newStr = "";
        for (char element : oldStr) {
            newStr = newStr + cnNum[Integer.parseInt(element + "")];
        }
        return newStr;
    }

    /**
     * 月份转化为中文数字
     *
     * @param month 阿拉伯数字月份
     * @return 中文数字
     */
    public static String monthToUppder(int month) {
        if (month < 10) {
            return numToUpper(month);
        } else if (month == 10) {
            return "十";
        } else {
            return "十" + numToUpper(month - 10);
        }
    }

    /**
     * 天数转化为中文数字
     *
     * @param day 阿拉伯数字日
     * @return 中文数字
     */
    public static String dayToUppder(int day) {
        if (day < 20) {
            return monthToUppder(day);
        } else {
            char[] str = String.valueOf(day).toCharArray();
            if (str[1] == '0') {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十";
            } else {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十" + numToUpper(Integer.parseInt(str[1] + ""));
            }
        }
    }

    /**
     * 比较两个时间大小
     *
     * @param strDate1
     * @param strDate2
     * @param pattern
     * @return 结果(0.相等、1.大于、-1.小于)
     */
    public static int compareTime(String strDate1, String strDate2, String pattern) {
        Calendar c1 = stringToCalendarFormat(strDate1, pattern);
        Calendar c2 = stringToCalendarFormat(strDate2, pattern);
        int result = c1.compareTo(c2);
        return result;
    }
    
    /**
     * 
     */
    public static void main(String []args){
    	for(int i=100;i>0;i--){
    		System.out.println((Math.random()+"").substring(2, 5));
    		//System.out.println(DateUtils.dateToStringFormat(new Date(), "yyyyMMddHHmmss"));
    	}
    }
    
    /**
     * 天数转化为中文数字
     *
     * @param day 阿拉伯数字日
     * @return 中文数字
     */
    public static int getUnit(int unit) {
    	Calendar gc=Calendar.getInstance();
    
    	 switch (unit) {
         case Calendar.HOUR_OF_DAY:
        	 return gc.get(unit);
  
     }
		return 0;
    	 
    }
}
