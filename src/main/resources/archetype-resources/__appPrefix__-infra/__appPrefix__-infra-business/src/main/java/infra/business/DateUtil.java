#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business;


import ${package}.infra.toolkits.exception.CustomerException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author klover
 */
@UtilityClass
@Slf4j
public class DateUtil {
    /**
     * 缺省格式
     */
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 缺省格式
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 缺省格式
     */
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 缺省格式
     */
    public static final String YYYY_MM_DD_Z = "yyyy年MM月dd日";

    /**
     * 缺省格式
     */
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String YYYY_MM_DD_HH_MM_V2 = "yyyy/MM/dd HH:mm";

    /**
     * 缺省格式
     */
    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyyMMddHHmmssSSS";

    /**
     * UTC time
     */
    public static final String UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * UTC time
     */
    public static final String UTC_1 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    /**
     * 一天的毫秒数
     */
    private static final long ONE_DAY_TIME = 24 * 60 * 60 * 1000L;

    /**
     * java 8 时间格式化(yyyy-MM-dd HH:mm:ss)
     */
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern(PATTERN);

    /**
     * java 8 时间格式化(yyyy-MM-dd)
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(YYYY_MM_DD);

    /**
     * java 8 时间格式化(yyyy-MM)
     */
    public static final DateTimeFormatter YYYY_MM_FORMAT = DateTimeFormatter.ofPattern(YYYY_MM);

    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_V2_PATTERN = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_V2);

    /**
     * 默认时区
     */
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Shanghai");
    /**
     * 年月日
     */
    public static final SimpleDateFormat DATE_FORMAT_1 = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat DATE_FORMAT_4 = new SimpleDateFormat(PATTERN);
    /**
     * yyyy-MM
     */
    public static final SimpleDateFormat DATE_FORMAT_8 = new SimpleDateFormat("yyyy-MM");
    /**
     * 年月日
     */
    public static final SimpleDateFormat DATE_FORMAT_10 = new SimpleDateFormat(PATTERN);
    /**
     * 年月日
     */
    public static final SimpleDateFormat DATE_FORMAT_11 = new SimpleDateFormat(YYYYMMDD);
    public static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS_SSS_PATTERN = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS);


    /**
     * UTC时区
     */
    public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");

    /**
     * 将字符串转换为毫秒数
     *
     * @param dateStr 对应格式的时间字符串
     * @return 毫秒数
     */
    public static Long dateStrToLong(String dateStr) {
        long timeStart;
        try {
            timeStart = DATE_FORMAT_4.parse(dateStr).getTime();
        } catch (ParseException e) {
            log.error(e.getMessage());
            throw new CustomerException(e.getMessage());
        }
        return timeStart;
    }

    /**
     * yyyy-MM-dd转换成yyyyMM
     *
     * @param time 日期 如2023-07-12
     * @return 返回到月 如202307
     */
    public static LocalDate convertToYyyyMm(String time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(time, dateTimeFormatter);
    }


    /**
     * 将毫秒数转换为可显示的字符串
     *
     * @param dateLong 毫秒数
     * @return 对应格式的时间字符串
     */
    public static String dateLongToString(Long dateLong) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        Date date = new Date(dateLong);
        return sdf.format(date);
    }

    /**
     * 将字符串转成时间  只要是时间字符串
     *
     * @param dateStr dateStr
     * @return 对应格式的时间字符串
     */
    public static Date stringToTime(String dateStr) {
        LocalDate parse = LocalDate.parse(dateStr);
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(parse.atStartOfDay(zone).toInstant());
    }

    /**
     * 将 LocalDate 转换为毫秒戳
     *
     * @param localDate 日期
     * @return 毫秒戳
     */
    public static long toTimestamp(LocalDate localDate) {
        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    /**
     * 毫秒时间戳转ISO8601
     *
     * @param ts 毫秒时间戳
     * @return ISO8601格式
     */
    public static String convertToISO8601(Long ts) {
        // 使用 Instant 将毫秒时间戳转换
        Instant instant = Instant.ofEpochMilli(ts);
        // 将 Instant 转换为 ZonedDateTime，使用系统0时区
//        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        // 格式化为 ISO 8601 字符串
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return formatter.format(zonedDateTime);
    }

    /**
     * 将 LocalDateTime 转换为毫秒戳
     *
     * @param localDateTime 日期时间
     * @return 毫秒戳
     */
    public static long toTimestamp(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    /**
     * 给定日期加上指定天数
     *
     * @param date 日期
     * @param days 天数
     * @return 加上天数后的日期
     */
    public static LocalDate addDays(LocalDate date, int days) {
        return date.plusDays(days);
    }

    /**
     * 给定日期减去指定天数
     *
     * @param date 日期
     * @param days 天数
     * @return 减去天数后的日期
     */
    public static LocalDate minusDays(LocalDate date, int days) {
        return date.minusDays(days);
    }


    /**
     * 获取两个日期之间的天数差
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 天数差
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static boolean isYYYYMMDD(String str) {
        // 正则表达式
        String regex = "^${symbol_escape}${symbol_escape}d{4}-${symbol_escape}${symbol_escape}d{1,2}-${symbol_escape}${symbol_escape}d{1,2}${symbol_dollar}";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        // 匹配字符串
        Matcher matcher = pattern.matcher(str);
        // 返回结果
        return matcher.matches();
    }

    /**
     * 当前时间加/减几天
     * 加为正数，减为负数
     *
     * @param startTime
     * @return java.util.Date
     * @author martinjiang
     * @version 1.0
     * @date 2022/4/19 17:15
     */
    public static Date dateAddSub(Date startTime, Integer day) {
        return dateAddSub(startTime, day, Calendar.DATE);
    }

    /**
     * 当前时间加/减几天
     * 加为正数，减为负数
     *
     * @param startTime  时间
     * @param number     数
     * @param offsetType 时间偏移类型
     * @return
     */
    public static Date dateAddSub(Date startTime, Integer number, int offsetType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(offsetType, number);
        return calendar.getTime();
    }

    /**
     * 特殊时间格式转化
     *
     * @param beforeTime
     * @return
     */
    public static Date timeFormat(String beforeTime) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return sdf1.parse(beforeTime);
        } catch (ParseException e) {
            throw new CustomerException(e.getMessage());
        }
    }

    /**
     * 特殊时间格式转化
     *
     * @param beforeTime beforeTime
     * @param sdf        sdf
     * @return Date
     */
    public static Date timeFormat(String beforeTime, SimpleDateFormat sdf) {
        try {
            return sdf.parse(beforeTime);
        } catch (ParseException e) {
            throw new CustomerException(e.getMessage());
        }
    }

    public static Date timeFormat(String beforeTime, DateTimeFormatter dateTimeFormatter) {
        LocalDateTime localDateTime = LocalDateTime.parse(beforeTime, dateTimeFormatter);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date parseTime(String dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(PATTERN);
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        try {
            return formatter.parse(dateTime);
        } catch (ParseException e) {
            throw new CustomerException(e.getMessage());
        }
    }



    public static String getWeekStartDate(String startTime) {
        Date date;
        try {
            //定义起始日期
            date = parse(startTime);
        } catch (Exception e) {
            log.error("时间转化异常，请检查你的时间格式是否为yyyy-MM或yyyy-MM-dd");
            throw new CustomerException(e.getMessage());
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return DATE_FORMAT_10.format(cal.getTime());
    }


    /**
     * 计算时间(计算传入时间前几天或者后几天的时间)
     *
     * @param nowTime 传入时间
     * @param addNum  要增加的天数(为负数则是减少的天数)
     * @return 计算后的时间
     */
    public Long dateAddDay(Long nowTime, int addNum) {
        return nowTime + (ONE_DAY_TIME * addNum);
    }

    /**
     * 日期格式化
     *
     * @param date 时间
     * @return String
     */
    public static String dateFormat(Date date) {
        return DateUtil.dateFormat(date, PATTERN);
    }

    /**
     * 日期格式化utc
     *
     * @param date 时间
     * @return String
     */
    public static String dateFormatUtc(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        try {
            return DateUtil.dateFormat(DATE_FORMAT_1.parse(date), UTC);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 日期格式化
     *
     * @param timestamp 毫秒级时间戳
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String dateFormat(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return DateUtil.dateFormat(new Date(timestamp), PATTERN);
    }

    /**
     * 日期格式化
     *
     * @param date   时间
     * @param format 格式化
     * @return String
     */
    public static String dateFormat(Date date, String format) {
//        SimpleDateFormat formatter = new SimpleDateFormat(format);
//        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//        return formatter.format(date);
        return dateFormat(date, format, "Asia/Shanghai");
    }

    /**
     * 日期格式化
     *
     * @param date   时间
     * @param format 格式化
     * @return String
     */
    public static String dateFormat(Date date, String format, String timeZone) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        return formatter.format(date);
    }

    /**
     * 日期格式化
     *
     * @param ts  毫秒时间戳时间
     * @param format 格式化
     * @return String
     */
    public static String dateFormat(Long ts, String format) {
        Date date = new Date(ts);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 转换带时区的时间
     * 如：2025-01-13T17:26:11+08:00
     *
     * @param time 带时区的时间
     * @return yyyy-MM-dd hh:MM::ss格式时间
     */
    public static String offsetDateTimeFormat(String time) {
        return offsetDateTimeFormat(time, PATTERN);
    }

    /**
     * 转换带时区的时间
     *
     * @param time 带时区时间
     * @param format 转换的格式
     * @return 转换后的格式
     */
    public static String offsetDateTimeFormat(String time, String format) {
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        // 获取系统偏移量
        ZoneId systemDefaultZoneId = ZoneId.systemDefault();
        ZonedDateTime now = ZonedDateTime.now(systemDefaultZoneId);
        ZoneOffset offset = now.getOffset();

        // 解析输入字符串为 OffsetDateTime
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(time);
        // 转换为 LocalDateTime
        LocalDateTime localDateTime = offsetDateTime.withOffsetSameInstant(offset).toLocalDateTime();
        // 格式化为目标格式 yyyy-MM-dd hh:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.atZone(ZoneId.systemDefault()).format(formatter);
    }

    /**
     * 字符串转换成时间
     *
     * @param text 时间
     * @return Date
     */
    public static Date parse(String text, String format) {
        return parse(text, format, TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static Date parse(String text, String format, TimeZone timeZone) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        if (timeZone != null) {
            formatter.setTimeZone(timeZone);
        }
        try {
            return formatter.parse(text);
        } catch (ParseException e) {
            throw new CustomerException(e.getMessage());
        }
    }

    /**
     * 字符串转换成时间
     * 格式: yyyy-MM-dd HH:mm:ss
     * 注：不支持类型为 yyyy-MM-dd'T'HH:mm:ss 的格式 若要使用 转到 timeFormat 方法
     *
     * @param text 时间
     * @return Date
     */
    public static Date parse(String text) {
        try {
            return parse(text, PATTERN);
        } catch (Exception e) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            try {
                return formatter.parse(text);
            } catch (ParseException e2) {
                throw new CustomerException(e.getMessage());
            }
        }
    }

    /**
     * 当前时间日期加一天 或者 加一个月
     *
     * @param time   当前时间
     * @param format Calendar.DAY_OF_MONTH
     * @param index  需要加的天数 1 或者 -1
     * @return Date
     */
    public static Date addTime(Date time, int format, int index) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        calendar.add(format, index);
        return calendar.getTime();
    }

    /**
     * 获取今天的开始时刻
     *
     * @return 2022-03-25 00:00:00
     */
    public static Date getStartTime() {
        return getStartTime(new Date());
    }

    /**
     * 获取某一天的开始时刻
     *
     * @param time 当前时间
     * @return 2022-03-25 00:00:00
     */
    public static Date getStartTime(Date time) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某一天的结束时刻
     *
     * @param time 当前时间
     * @return 2022-03-25 23:59:59
     */
    public static Date getEndTime(Date time) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 转换为date时间
     *
     * @param timestamps 时间戳
     * @return 一天的最早开始时间和最晚时间
     */
    public static List<Date> transferToStartAndEndDate(List<Long> timestamps) {
        if (CollectionUtils.isEmpty(timestamps)) {
            return Collections.emptyList();
        }
        List<Date> list = new ArrayList<>();
        list.add(getStartTime(new Date(timestamps.get(0))));
        list.add(getEndTime(new Date(timestamps.get(1))));
        return list;
    }

    public static Date getMontStartTime(Date date, int amount) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, amount);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取上个月开始时间
     *
     * @param time time
     * @return date
     */
    public static Date getPreviousMonthStartTime(Date time) {
        return getMontStartTime(time, -1);
    }

    public static Date getNextMontStartTime(Date time) {
        return getMontStartTime(time, 1);
    }

    public static Date getMonthEndTime(Date time) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置创造新日期，这个日期是本月的最后一天
        calendar.set(Calendar.DATE, days);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static String getMonthStartTime(String time) {
        Date begin_date;
        try {
            //定义起始日期
            begin_date = parse(time);
        } catch (Exception e) {
            throw new CustomerException("时间转化异常，请检查你的时间格式是否为yyyy-MM或yyyy-MM-dd");
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(begin_date);
        // 设置创造新日期，这个日期是本月的最后一天
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return DATE_FORMAT_10.format(date);
    }



    public static class DateVo {
        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        private String startDate;
        private String endDate;

        private DateVo(String startStr, String endStr) {
            this.startDate = startStr;
            this.endDate = endStr;
        }

        private DateVo() {
        }

    }

    /**
     * @Description: 获取时间段内所有自然月, 参数格式为:yyyy-MM-dd
     * @Param: [startDate, endDate]
     * @Return: java.util.List<Test.DateVo>
     */
    public static List<DateVo> getBetweenMonths(String startDate, String endDate) throws ParseException {
        List<DateVo> list = new ArrayList<>();
        String firstDay = "";
        String lastDay = "";
        Date d1 = DATE_FORMAT_10.parse(startDate);
        Date d2 = DATE_FORMAT_10.parse(endDate);// 定义结束日期

        Calendar dd = Calendar.getInstance();
        // 开始日期日历
        Calendar c = Calendar.getInstance();
        //结束日期日历
        dd.setTime(d1);
        // 设置日期起始时间
        c.setTime(d2);
        Calendar cale = Calendar.getInstance();
        int startDay = d1.getDate();
        int endDay = d2.getDate();
        DateVo keyValueForDate = null;
        while (dd.getTime().before(d2)) {
            keyValueForDate = new DateVo();
            cale.setTime(dd.getTime());
            if (dd.getTime().equals(d1)) {
                if ((dd.get(Calendar.MONTH) + 1) == (c.get(Calendar.MONTH) + 1) && (dd.get(Calendar.YEAR)) == (c.get(Calendar.YEAR) + 1)) {
                    if (startDay == 1) {
                        cale.set(Calendar.DATE, endDay - startDay + 1);
                    } else {
                        cale.set(Calendar.DATE, endDay);
                    }
                } else {
                    cale.set(Calendar.DAY_OF_MONTH, dd.getActualMaximum(Calendar.DAY_OF_MONTH));
                }
                lastDay = DATE_FORMAT_10.format(cale.getTime());
                keyValueForDate.setStartDate(DATE_FORMAT_10.format(d1));
                keyValueForDate.setEndDate(lastDay);
            } else if (dd.get(Calendar.MONTH) == d2.getMonth() && dd.get(Calendar.YEAR) == c.get(Calendar.YEAR)) {
                cale.set(Calendar.DAY_OF_MONTH, 1);
                // 取第一天
                firstDay = DATE_FORMAT_10.format(cale.getTime());
                keyValueForDate.setStartDate(firstDay);
                keyValueForDate.setEndDate(DATE_FORMAT_10.format(d2));
            } else {
                cale.set(Calendar.DAY_OF_MONTH, 1);
                //取第一天
                firstDay = DATE_FORMAT_10.format(cale.getTime());
                cale.set(Calendar.DAY_OF_MONTH, dd.getActualMaximum(Calendar.DAY_OF_MONTH));
                lastDay = DATE_FORMAT_10.format(cale.getTime());
                keyValueForDate.setStartDate(firstDay);
                keyValueForDate.setEndDate(lastDay);
            }
            list.add(keyValueForDate);
            dd.add(Calendar.MONTH, 1);// 进行当前日期月份加1
        }
        if (endDay <= startDay) {
            keyValueForDate = new DateVo();
            cale.setTime(d2);
            cale.set(Calendar.DAY_OF_MONTH, 1);//取第一天
            firstDay = DATE_FORMAT_10.format(cale.getTime());
            keyValueForDate.setStartDate(firstDay);
            keyValueForDate.setEndDate(DATE_FORMAT_10.format(d2));
            list.add(keyValueForDate);
        }
        return list;
    }

    /**
     * @Description: 获取时间段内所有自然日, 参数格式为:yyyy-MM-dd
     * @Param: [startDate, endDate]
     * @Return: java.util.List<Test.DateVo>
     */
    public static List<DateVo> getBetweenDates(String startDate, String endDate) throws ParseException {
        Date start = DATE_FORMAT_10.parse(startDate);
        Date end = DATE_FORMAT_10.parse(endDate);// 定义结束日期
        List<DateVo> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd) || tempStart.equals(tempEnd)) {
            DateVo dateVo = new DateVo();
            dateVo.setStartDate(DATE_FORMAT_10.format(tempStart.getTime()));
            dateVo.setEndDate(DATE_FORMAT_10.format(tempStart.getTime()));
            result.add(dateVo);
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    /**
     * 计算两个日期之间相差的天数
     * 精确到时间戳计算而来
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static int getBetweenDates(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = sdf.parse(sdf.format(startDate));
            endDate = sdf.parse(sdf.format(endDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @see ${symbol_pound}getBetweenDates(Date, Date)
     * <p>
     * 计算两个日期之间相差的天数，没有那么精确，只做日期的运算
     */
    public static long betweenDates(Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(startDate);
        LocalDate start = LocalDate.of(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        calendar.setTime(endDate);
        LocalDate end = LocalDate.of(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * @Description: 获取时间段内所有自然周, 参数格式为:yyyy-MM-dd
     * @Param: [startDate, endDate]
     * @Return: java.util.List<Test.DateVo>
     */
    public static List<DateVo> getBetweenWeeks(String startDate, String endDate) throws ParseException {
        List<String> listWeekOrMonth = new ArrayList<>();
        List<DateVo> dateVoList = new ArrayList<>();
        Date sDate = DATE_FORMAT_10.parse(startDate);
        Calendar sCalendar = Calendar.getInstance();
        sCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        sCalendar.setTime(sDate);
        Date eDate = DATE_FORMAT_10.parse(endDate);// 定义结束日期
        Calendar eCalendar = Calendar.getInstance();
        eCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        eCalendar.setTime(eDate);
        boolean bool = true;
        while (sCalendar.getTime().getTime() < eCalendar.getTime().getTime()) {
            if (bool || sCalendar.get(Calendar.DAY_OF_WEEK) == 2 || sCalendar.get(Calendar.DAY_OF_WEEK) == 1) {
                listWeekOrMonth.add(DATE_FORMAT_10.format(sCalendar.getTime()));
                bool = false;
            }
            sCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        listWeekOrMonth.add(DATE_FORMAT_10.format(eCalendar.getTime()));
        if (listWeekOrMonth.size() % 2 != 0) {
            listWeekOrMonth.add(DATE_FORMAT_10.format(eCalendar.getTime()));
        }
        for (int i = 0; i < listWeekOrMonth.size() - 1; i++) {
            if (i % 2 == 0) {
                DateVo dateVo = new DateVo();
                dateVo.setStartDate(listWeekOrMonth.get(i));
                dateVo.setEndDate(listWeekOrMonth.get(i + 1));
                dateVoList.add(dateVo);
            }
        }
        return dateVoList;
    }

    /**
     * 获取当前年份都季度
     *
     * @param date
     * @return int
     * @author martinjiang
     * @version 1.0
     * @date 2022/10/26 17:46
     */
    public static int getQuarterOfYear(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse(date));
        return calendar.get(Calendar.MONTH) / 3 + 1;
    }

    /**
     * 获取当前年份
     *
     * @param date
     * @return int
     * @author martinjiang
     * @version 1.0
     * @date 2022/10/26 17:46
     */
    public static String getYear(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date dateTime = parse(date);
        return sdf.format(dateTime);
    }

    /**
     * 获取季度开始时间
     *
     * @param date
     * @return java.util.Date
     * @author martinjiang
     * @version 1.0
     * @date 2022/10/26 20:06
     */
    public static Date getQuarterStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month / 3 * 3);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }


    public String getQuarterForDate(String statisticTimes) {
        // 解析字符串为 LocalDate 对象
        LocalDate date = LocalDate.parse(statisticTimes);
        // 获取季度开始时间
        int year = date.getYear();
        int monthValue = date.getMonthValue();
        int quarterStartMonth;

        if (monthValue >= 1 && monthValue <= 3) {
            quarterStartMonth = 1;
        } else if (monthValue >= 4 && monthValue <= 6) {
            quarterStartMonth = 2;
        } else if (monthValue >= 7 && monthValue <= 9) {
            quarterStartMonth = 3;
        } else {
            quarterStartMonth = 4;
        }
        // 格式化为 "yyyy-MM" 格式并打印结果
        return year + "-" + quarterStartMonth;
    }



}
