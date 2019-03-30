package com.yuntongxun.itsys.base.common.util;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
	public static final FastDateFormat simpleDateFormat_default = FastDateFormat.getInstance("yyyy/MM/dd");
	public static final FastDateFormat simpleDateFormat_ym = FastDateFormat.getInstance("yyyy/MM");
	public static final FastDateFormat simpleDateFormat_no_slash = FastDateFormat.getInstance("yyyyMMdd");
	public static final FastDateFormat simpleDateFormat_ymr_slash = FastDateFormat.getInstance("yyyy-MM-dd");
	public static final FastDateFormat simpleDateFormat_ym_no_slash = FastDateFormat.getInstance("yyyyMM");
	public static final FastDateFormat simpleDateFormat_date_time = FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss");
	public static final FastDateFormat simpleDateFormat_date_time_no_slash = FastDateFormat
			.getInstance("yyyyMMddHHmmss");
	public static final FastDateFormat simpleDateFormat_date_hm = FastDateFormat.getInstance("yyyy/MM/dd HH:mm");
	public static final FastDateFormat simpleDateFormat_time = FastDateFormat.getInstance("HH:mm:ss");
	public static final FastDateFormat simpleDateFormat_hm = FastDateFormat.getInstance("HH:mm");
	public static final FastDateFormat simpleDateFormat_long_time = FastDateFormat.getInstance("HHmmss");
	public static final FastDateFormat simpleDateFormat_short_time = FastDateFormat.getInstance("HHmm");
	public static final FastDateFormat simpleDateFormat_date_time_line = FastDateFormat
			.getInstance("yyyy-MM-dd HH:mm:ss");
	public static final FastDateFormat simpleDateFormat_date_time_no_slash_sec = FastDateFormat
			.getInstance("yyyyMMddHHmmssSSS");

	/**
	 * 变量：日期格式化类型 - 格式:yyyy/MM/dd
	 * 
	 * @since 1.0
	 */
	public static final int DEFAULT = 0;

	/**
	 * 变量：日期格式化类型 - 格式:yyyy/MM
	 * 
	 * @since 1.0
	 */
	public static final int YM = 1;

	/**
	 * 变量：日期格式化类型 - 格式:yyyy-MM-dd
	 * 
	 * @since 1.0
	 */
	public static final int YMR_SLASH = 11;

	/**
	 * 变量：日期格式化类型 - 格式:yyyyMMdd
	 * 
	 * @since 1.0
	 */
	public static final int NO_SLASH = 2;

	/**
	 * 变量：日期格式化类型 - 格式:yyyyMM
	 * 
	 * @since 1.0
	 */
	public static final int YM_NO_SLASH = 3;

	/**
	 * 变量：日期格式化类型 - 格式:yyyy-MM-dd HH:mm:ss
	 * 
	 * @since 1.0
	 */
	public static final int DATE_TIME = 4;

	/**
	 * 变量：日期格式化类型 - 格式:yyyyMMddHHmmss
	 * 
	 * @since 1.0
	 */
	public static final int DATE_TIME_NO_SLASH = 5;

	/**
	 * 变量：日期格式化类型 - 格式:yyyy/MM/dd HH:mm
	 * 
	 * @since 1.0
	 */
	public static final int DATE_HM = 6;

	/**
	 * 变量：日期格式化类型 - 格式:HH:mm:ss
	 * 
	 * @since 1.0
	 */
	public static final int TIME = 7;

	/**
	 * 变量：日期格式化类型 - 格式:HH:mm
	 * 
	 * @since 1.0
	 */
	public static final int HM = 8;

	/**
	 * 变量：日期格式化类型 - 格式:HHmmss
	 * 
	 * @since 1.0
	 */
	public static final int LONG_TIME = 9;

	/**
	 * 变量：日期格式化类型 - 格式:HHmm
	 * 
	 * @since 1.0
	 */
	public static final int SHORT_TIME = 10;

	/**
	 * 变量：日期格式化类型 - 格式:yyyy-MM-dd HH:mm:ss
	 * 
	 * @since 1.0
	 */
	public static final int DATE_TIME_LINE = 12;
	
	/**
	 * 格式 yyyyMMddHHmmssSSS
	 */
	public static final int DATE_TIME_NO_SLASH_SEC = 13;

	/**
	 * 变量：1小时的毫秒数
	 * 
	 * @since 1.0
	 */
	public static final double HOUR_MILLISECOND = 1000 * 60 * 60;

	/**
	 * 星期常量
	 */
	public static final String[] WEEK = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

	/**
	 * 常用日期格式化
	 */
	public static final String[] DATE_PATTERN = new String[] { "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss",
			"yyyy-MM-dd HH:mm:ss.S" };

	public static final String[] DATE_PATTERN2 = new String[] { "yyyyMMddHHmmss" };
	
	
	public static final String[] DATE_PATTERN3 = new String[] { "yyyy-MM-dd HH:mm:ss" };
	
	/**
	 * 判断是否符合日期格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isFormatTime2(String time) {
		try {
			Date d = DateUtils.parseDate(time, DATE_PATTERN3);
			return d != null ? true : false;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 判断是否符合日期格式 yyyyMMddHHmmss
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isFormatTime(String time) {
		try {
			Date d = DateUtils.parseDate(time, DATE_PATTERN2);
			return d != null ? true : false;
		} catch (ParseException e) {
			return false;
		}
	}

	
	public static boolean isFormatTimeForChatRoom(String time) {
		try {
			Date d = DateUtils.parseDate(time, DATE_PATTERN3);
			return d != null ? true : false;
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * 取得时间 yyyyMMddHHmmss
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTimestamp(long time) {
		return DateFormatUtils.format(time, "yyyyMMddHHmmss");
	}

	/**
	 * 获取年和月
	 * 
	 * @param date
	 * @return
	 */
	public static String getYearMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		String monthStr = month < 10 ? "0" + month : "" + month;
		return year + monthStr;
	}

	/**
	 * 取得时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 时间字符串
	 */
	public static String getDateTime(Date date) {
		return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getTextReceiveDate(String time) {
		// 2012 05 17 18 49 03
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(time.substring(0, 4) + "-");
		sbBuffer.append(time.substring(4, 6) + "-");
		sbBuffer.append(time.substring(6, 8) + " ");

		sbBuffer.append(time.substring(8, 10) + ":");
		sbBuffer.append(time.substring(10, 12) + ":");
		sbBuffer.append(time.substring(12, time.length()));

		return sbBuffer.toString();
	}

	/**
	 * @param date
	 * @param type
	 * @return
	 */
	public static Date strToDate(String date, int type) {
		try {
			Date d = DateUtils.parseDate(date, DATE_PATTERN);
			return d;
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * @param date
	 * @param type
	 * @return
	 */
	public static String dateToStr(Date date, int type) {
		switch (type) {
		case DEFAULT:
			return simpleDateFormat_default.format(date);
		case YM:
			return simpleDateFormat_ym.format(date);
		case NO_SLASH:
			return simpleDateFormat_no_slash.format(date);
		case YMR_SLASH:
			return simpleDateFormat_ymr_slash.format(date);
		case YM_NO_SLASH:
			return simpleDateFormat_ym_no_slash.format(date);
		case DATE_TIME:
			return simpleDateFormat_date_time.format(date);
		case DATE_TIME_NO_SLASH:
			return simpleDateFormat_date_time_no_slash.format(date);
		case DATE_HM:
			return simpleDateFormat_date_hm.format(date);
		case TIME:
			return simpleDateFormat_time.format(date);
		case HM:
			return simpleDateFormat_hm.format(date);
		case LONG_TIME:
			return simpleDateFormat_long_time.format(date);
		case SHORT_TIME:
			return simpleDateFormat_short_time.format(date);
		case DATE_TIME_LINE:
			return simpleDateFormat_date_time_line.format(date);
		case DATE_TIME_NO_SLASH_SEC:
			return simpleDateFormat_date_time_no_slash_sec.format(date);
		default:
			throw new IllegalArgumentException("Type undefined: " + type);
		}
	}

	/**
	 * 判定给定的年是否为闰年
	 * 
	 * @param year
	 * @return
	 */
	private static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 取得给定的月份的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfMonth(int year, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			if (isLeapYear(year)) {
				return 29;
			}
			return 28;
		default:
			return -1;
		}
	}

	/**
	 * 获取Timestamp格式的当前系统时间
	 * 
	 * @return
	 */
	public static Timestamp getDateOfTimestamp() {
		Timestamp timeStamp = null;
		String dateTime = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_LINE);
		if (!"".equals(dateTime) && dateTime != null) {
			timeStamp = Timestamp.valueOf(dateTime);
		}
		return timeStamp;
	}

	/**
	 * 获取当前系统时间 格式: yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getDateOfString(Timestamp date, int i) {
		String dateTime = dateToStr(date, i);
		return dateTime;
	}

	/**
	 * 获取年月日，格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDate() {
		String dateTime = dateToStr(new Date(), DateUtil.DATE_TIME_LINE);
		return dateTime;
	}

	/**
	 * 获取年月日时分秒，格式：yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getTimeOfToday() {
		String dateTime = dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH);
		return dateTime;
	}
	
	/**
	 * 获取年月日时分秒，格式：yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String getTimeSecOfToday() {
		String dateTime = dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH_SEC);
		return dateTime;
	}

	/**
	 * 获取本月第一天
	 * 
	 * @return now 当前日期
	 */
	public static String getFristMonth() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.set(Calendar.DAY_OF_MONTH, 1);
		String now = simpleDateFormat_ymr_slash.format(ca.getTime());
		return now;
	}

	/**
	 * 获取本周第一天
	 * 
	 * @return firstMonday
	 */
	public static String getFristWeek() {
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek;
		}
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		String firstMonday = simpleDateFormat_ymr_slash.format(monday);
		return firstMonday;
	}
	
	/**
     * 获取上周一的日期，
     * 格式：yyyy-MM-dd
     * @return
     */
    public static String getLastWeekMonday(){

    	Calendar cal = Calendar.getInstance();
    	cal.setFirstDayOfWeek(Calendar.MONDAY);// 将每周第一天设为星期一，默认是星期天
    	cal.add(Calendar.WEEK_OF_MONTH,-1);// 周数减一，即上周
    	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 日子设为星期一
    	Date d = cal.getTime();
    	String day = dateToStr(d, YMR_SLASH);
        return day;
    }

	/**
	 * 获取当前日期 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getNowDay() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 呼叫开始的时间，单位秒，用1970/1/1 0:00:00以来的秒数来表示，类型为long
	 * @param nowDay
	 * @return
	 */
	public static long getTimestamp(String nowDay) {
		try {
			Date d = DateUtils.parseDate(nowDay, DATE_PATTERN2);
			return d.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取当前时间的时间戳
	 * 
	 * @return
	 */
	public static long getTimestamp() {
		long time = System.currentTimeMillis();
		return time;
	}

	/**
	 * 获取下一天的时间戳
	 * 
	 * @return
	 */
	public static long getNextDayTimestamp(String nowDay) {
		try {
			Date d = DateUtils.addDays(DateUtils.parseDate(nowDay, new String[] { "yyyy-MM-dd" }), 1);
			return d.getTime();
		} catch (ParseException e) {
			return System.currentTimeMillis();
		}
	}

	/**
	 * 获取前一天的日期
	 * 
	 * @return
	 */
	public static String getPrevDate() {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.DATE, -1); // 得到前一天
		String dayBefore = simpleDateFormat_ymr_slash.format(calendar.getTime());
		return dayBefore;
	}
	
	/**
	 * 获取当前yyyy-MM-dd-HH格式日期
	 * 
	 * @return 
	 */
	public static String getPrevHour() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd-HH");
	}
	
	/**
	 * 判断输入的字符串是否满足时间格式 ： yyyy-MM-dd HH:mm:ss
	 * @param patternString 需要验证的字符串
	 * @return 合法返回 true ; 不合法返回false
	 */
	public static boolean isTimeLegal(String patternString) {
		
		Pattern a=Pattern.compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$"); 
		
		Matcher b=a.matcher(patternString); 
		if(b.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 转换时间为string类型
	 * @param time
	 * @return
	 */
	public static String changeTimeToString (long time) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lastTime = sdf.format(new Date(time));
		return lastTime;
	}

	/**
	 * 获取去年当前时间
	 * @return
	 */
	public static Date getLastYear() {
		Calendar curr = Calendar.getInstance();
		curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)-1);
		Date date = curr.getTime();
		return date;
	}

	/**
	 * 获取上月的当前时间
	 * 
	 * @return
	 */
	public static String getPrevMonth() {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.MONTH, -1); // 得到前一月
		String dayBefore = simpleDateFormat_date_time_line.format(calendar.getTime());
		return dayBefore;
	}
	
	
	/**
	 * 将日期类型转换成自定义格式
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date formatDateStr(String dateStr, String format) {
		Date date = new Date();
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			date = df.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
		return date;
	}
	
	/**
	 * 将日期类型转换成自定义格式的字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		String ret = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			ret = df.format(date);
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}
	
	/**
	 * 根据毫秒数转换为年月日字符串
	 * @param time
	 * @return
	 */
	public static String getYMDByString(String time){
		String pattern="yyyy-MM-dd";
		SimpleDateFormat sp=new SimpleDateFormat(pattern);
		long date=Long.parseLong(time);
		String ret=sp.format(date);
		return ret;
	}

	/**
	 * 日期格式为yyyyMMdd
	 * getAge:(根据日期算出年龄). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author ty
	 * @param birthDay
	 * @return
	 * @throws Exception
	 * @since JDK 1.8
	 */
	public static int getAge(Date birthDay) throws Exception {  
        Calendar cal = Calendar.getInstance();  
  
        if (cal.before(birthDay)) {  
            throw new IllegalArgumentException(  
                    "The birthDay is before Now.It's unbelievable!");  
        }  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        cal.setTime(birthDay);  
  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
  
        int age = yearNow - yearBirth;  
  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }  
        return age;  
    }
    public static  Date getStringToDate(String  param) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
		return sdf.parse(param);
	}

	public static boolean compareDate(Date date1, Date date2) {
		if (date1.before(date2)) {
		} else if (date1.after(date2)) {
			return false;
		} else {
		}
		return true;
	}
	/**
	 * 获取SimpleDateFormat
	 *
	 * @param parttern 日期格式
	 * @return SimpleDateFormat对象
	 * @throws RuntimeException 异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {
		return new SimpleDateFormat(parttern);
	}

	/**
	 * 获取日期中的某数值。如获取月份
	 *
	 * @param date     日期
	 * @param dateType 日期格式
	 * @return 数值
	 */
	private static int getInteger(Date date, int dateType) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(dateType);
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 *
	 * @param date     日期字符串
	 * @param dateType 类型
	 * @param amount   数值
	 * @return 计算后日期字符串
	 */
	private static String addInteger(String date, int dateType, int amount) {
		String dateString = null;
		DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null) {
			Date myDate = StringToDate(date, dateStyle);
			myDate = addInteger(myDate, dateType, amount);
			dateString = DateToString(myDate, dateStyle);
		}
		return dateString;
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 *
	 * @param date     日期
	 * @param dateType 类型
	 * @param amount   数值
	 * @return 计算后日期
	 */
	private static Date addInteger(Date date, int dateType, int amount) {
		Date myDate = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(dateType, amount);
			myDate = calendar.getTime();
		}
		return myDate;
	}

	/**
	 * 获取精确的日期
	 *
	 * @param timestamps 时间long集合
	 * @return 日期
	 */
	private static Date getAccurateDate(List<Long> timestamps) {
		Date date = null;
		long timestamp = 0;
		Map<Long, long[]> map = new HashMap<Long, long[]>();
		List<Long> absoluteValues = new ArrayList<Long>();

		if (timestamps != null && timestamps.size() > 0) {
			if (timestamps.size() > 1) {
				for (int i = 0; i < timestamps.size(); i++) {
					for (int j = i + 1; j < timestamps.size(); j++) {
						long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
						absoluteValues.add(absoluteValue);
						long[] timestampTmp = {timestamps.get(i), timestamps.get(j)};
						map.put(absoluteValue, timestampTmp);
					}
				}

				// 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的
				long minAbsoluteValue = -1;
				if (!absoluteValues.isEmpty()) {
					// 如果timestamps的size为2，这是差值只有一个，因此要给默认值
					minAbsoluteValue = absoluteValues.get(0);
				}
				for (int i = 0; i < absoluteValues.size(); i++) {
					for (int j = i + 1; j < absoluteValues.size(); j++) {
						if (absoluteValues.get(i) > absoluteValues.get(j)) {
							minAbsoluteValue = absoluteValues.get(j);
						} else {
							minAbsoluteValue = absoluteValues.get(i);
						}
					}
				}

				if (minAbsoluteValue != -1) {
					long[] timestampsLastTmp = map.get(minAbsoluteValue);
					if (absoluteValues.size() > 1) {
						timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
					} else if (absoluteValues.size() == 1) {
						// 当timestamps的size为2，需要与当前时间作为参照
						long dateOne = timestampsLastTmp[0];
						long dateTwo = timestampsLastTmp[1];
						if ((Math.abs(dateOne - dateTwo)) < 100000000000L) {
							timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
						} else {
							long now = new Date().getTime();
							if (Math.abs(dateOne - now) <= Math.abs(dateTwo - now)) {
								timestamp = dateOne;
							} else {
								timestamp = dateTwo;
							}
						}
					}
				}
			} else {
				timestamp = timestamps.get(0);
			}
		}

		if (timestamp != 0) {
			date = new Date(timestamp);
		}
		return date;
	}

	/**
	 * 判断字符串是否为日期字符串
	 *
	 * @param date 日期字符串
	 * @return true or false
	 */
	public static boolean isDate(String date) {
		boolean isDate = false;
		if (date != null) {
			if (StringToDate(date) != null) {
				isDate = true;
			}
		}
		return isDate;
	}

	/**
	 * 获取日期字符串的日期风格。失敗返回null。
	 *
	 * @param date 日期字符串
	 * @return 日期风格
	 */
	public static DateStyle getDateStyle(String date) {
		DateStyle dateStyle = null;
		Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
		List<Long> timestamps = new ArrayList<Long>();
		for (DateStyle style : DateStyle.values()) {
			Date dateTmp = StringToDate(date, style.getValue());
			if (dateTmp != null) {
				timestamps.add(dateTmp.getTime());
				map.put(dateTmp.getTime(), style);
			}
		}
		dateStyle = map.get(getAccurateDate(timestamps).getTime());
		return dateStyle;
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 *
	 * @param date 日期字符串
	 * @return 日期
	 */
	public static Date StringToDate(String date) {
		DateStyle dateStyle = null;
		return StringToDate(date, dateStyle);
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 *
	 * @param date     日期字符串
	 * @param parttern 日期格式
	 * @return 日期
	 */
	public static Date StringToDate(String date, String parttern) {
		Date myDate = null;
		if (date != null) {
			try {
				myDate = getDateFormat(parttern).parse(date);
			} catch (Exception e) {
			}
		}
		return myDate;
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 *
	 * @param date      日期字符串
	 * @param dateStyle 日期风格
	 * @return 日期
	 */
	public static Date StringToDate(String date, DateStyle dateStyle) {
		Date myDate = null;
		if (dateStyle == null) {
			List<Long> timestamps = new ArrayList<Long>();
			for (DateStyle style : DateStyle.values()) {
				Date dateTmp = StringToDate(date, style.getValue());
				if (dateTmp != null) {
					timestamps.add(dateTmp.getTime());
				}
			}
			myDate = getAccurateDate(timestamps);
		} else {
			myDate = StringToDate(date, dateStyle.getValue());
		}
		return myDate;
	}

	/**
	 * 将日期转化为日期字符串。失败返回null。
	 *
	 * @param date     日期
	 * @param parttern 日期格式
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, String parttern) {
		String dateString = null;
		if (date != null) {
			try {
				dateString = getDateFormat(parttern).format(date);
			} catch (Exception e) {
			}
		}
		return dateString;
	}

	/**
	 * 将日期转化为日期字符串。失败返回null。
	 *
	 * @param date      日期
	 * @param dateStyle 日期风格
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, DateStyle dateStyle) {
		String dateString = null;
		if (dateStyle != null) {
			dateString = DateToString(date, dateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date     旧日期字符串
	 * @param parttern 新日期格式
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, String parttern) {
		return StringToString(date, null, parttern);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date      旧日期字符串
	 * @param dateStyle 新日期风格
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, DateStyle dateStyle) {
		return StringToString(date, null, dateStyle);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date         旧日期字符串
	 * @param olddParttern 旧日期格式
	 * @param newParttern  新日期格式
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, String olddParttern, String newParttern) {
		String dateString = null;
		if (olddParttern == null) {
			DateStyle style = getDateStyle(date);
			if (style != null) {
				Date myDate = StringToDate(date, style.getValue());
				dateString = DateToString(myDate, newParttern);
			}
		} else {
			Date myDate = StringToDate(date, olddParttern);
			dateString = DateToString(myDate, newParttern);
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date         旧日期字符串
	 * @param olddDteStyle 旧日期风格
	 * @param newDateStyle 新日期风格
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, DateStyle olddDteStyle, DateStyle newDateStyle) {
		String dateString = null;
		if (olddDteStyle == null) {
			DateStyle style = getDateStyle(date);
			dateString = StringToString(date, style.getValue(), newDateStyle.getValue());
		} else {
			dateString = StringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 增加日期的年份。失败返回null。
	 *
	 * @param date       日期
	 * @param yearAmount 增加数量。可为负数
	 * @return 增加年份后的日期字符串
	 */
	public static String addYear(String date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的年份。失败返回null。
	 *
	 * @param date       日期
	 * @param yearAmount 增加数量。可为负数
	 * @return 增加年份后的日期
	 */
	public static Date addYear(Date date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 *
	 * @param date       日期
	 * @param yearAmount 增加数量。可为负数
	 * @return 增加月份后的日期字符串
	 */
	public static String addMonth(String date, int yearAmount) {
		return addInteger(date, Calendar.MONTH, yearAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 *
	 * @param date       日期
	 * @param yearAmount 增加数量。可为负数
	 * @return 增加月份后的日期
	 */
	public static Date addMonth(Date date, int yearAmount) {
		return addInteger(date, Calendar.MONTH, yearAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 *
	 * @param date      日期字符串
	 * @param dayAmount 增加数量。可为负数
	 * @return 增加天数后的日期字符串
	 */
	public static String addDay(String date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 *
	 * @param date      日期
	 * @param dayAmount 增加数量。可为负数
	 * @return 增加天数后的日期
	 */
	public static Date addDaytoDate(Date date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 *
	 * @param date      日期字符串
	 * @param dayAmount 增加数量。可为负数
	 * @return 增加小时后的日期字符串
	 */
	public static String addHour(String date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 *
	 * @param date      日期
	 * @param dayAmount 增加数量。可为负数
	 * @return 增加小时后的日期
	 */
	public static Date addHour(Date date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 *
	 * @param date      日期字符串
	 * @param dayAmount 增加数量。可为负数
	 * @return 增加分钟后的日期字符串
	 */
	public static String addMinute(String date, int hourAmount) {
		return addInteger(date, Calendar.MINUTE, hourAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 *
	 * @param date      日期
	 * @param dayAmount 增加数量。可为负数
	 * @return 增加分钟后的日期
	 */
	public static Date addMinute(Date date, int hourAmount) {
		return addInteger(date, Calendar.MINUTE, hourAmount);
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 *
	 * @param date      日期字符串
	 * @param dayAmount 增加数量。可为负数
	 * @return 增加秒钟后的日期字符串
	 */
	public static String addSecond(String date, int hourAmount) {
		return addInteger(date, Calendar.SECOND, hourAmount);
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 *
	 * @param date      日期
	 * @param dayAmount 增加数量。可为负数
	 * @return 增加秒钟后的日期
	 */
	public static Date addSecond(Date date, int hourAmount) {
		return addInteger(date, Calendar.SECOND, hourAmount);
	}

	/**
	 * 获取日期的年份。失败返回0。
	 *
	 * @param date 日期字符串
	 * @return 年份
	 */
	public static int getYear(String date) {
		return getYear(StringToDate(date));
	}

	/**
	 * 获取日期的年份。失败返回0。
	 *
	 * @param date 日期
	 * @return 年份
	 */
	public static int getYear(Date date) {
		return getInteger(date, Calendar.YEAR);
	}

	/**
	 * 获取日期的月份。失败返回0。
	 *
	 * @param date 日期字符串
	 * @return 月份
	 */
	public static int getMonth(String date) {
		return getMonth(StringToDate(date));
	}

	/**
	 * 获取日期的月份。失败返回0。
	 *
	 * @param date 日期
	 * @return 月份
	 */
	public static int getMonth(Date date) {
		return getInteger(date, Calendar.MONTH);
	}

	/**
	 * 获取日期的天数。失败返回0。
	 *
	 * @param date 日期字符串
	 * @return 天
	 */
	public static int getDay(String date) {
		return getDay(StringToDate(date));
	}

	/**
	 * 获取日期的天数。失败返回0。
	 *
	 * @param date 日期
	 * @return 天
	 */
	public static int getDay(Date date) {
		return getInteger(date, Calendar.DATE);
	}

	/**
	 * 获取日期的小时。失败返回0。
	 *
	 * @param date 日期字符串
	 * @return 小时
	 */
	public static int getHour(String date) {
		return getHour(StringToDate(date));
	}

	/**
	 * 获取日期的小时。失败返回0。
	 *
	 * @param date 日期
	 * @return 小时
	 */
	public static int getHour(Date date) {
		return getInteger(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 *
	 * @param date 日期字符串
	 * @return 分钟
	 */
	public static int getMinute(String date) {
		return getMinute(StringToDate(date));
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 *
	 * @param date 日期
	 * @return 分钟
	 */
	public static int getMinute(Date date) {
		return getInteger(date, Calendar.MINUTE);
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 *
	 * @param date 日期字符串
	 * @return 秒钟
	 */
	public static int getSecond(String date) {
		return getSecond(StringToDate(date));
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 *
	 * @param date 日期
	 * @return 秒钟
	 */
	public static int getSecond(Date date) {
		return getInteger(date, Calendar.SECOND);
	}

	/**
	 * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
	 *
	 * @param date 日期字符串
	 * @return 日期
	 */
	public static String getDate(String date) {
		return StringToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期。默认yyyy-MM-dd格式。失败返回null。
	 *
	 * @param date 日期
	 * @return 日期
	 */
	public static String getDate(Date date) {
		return DateToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	 *
	 * @param date 日期字符串
	 * @return 时间
	 */
	public static String getTime(String date) {
		return StringToString(date, DateStyle.HH_MM_SS);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	 *
	 * @param date 日期
	 * @return 时间
	 */
	public static String getTime(Date date) {
		return DateToString(date, DateStyle.HH_MM_SS);
	}

	/**
	 * 获取日期的星期。失败返回null。
	 *
	 * @param date 日期字符串
	 * @return 星期
	 */
	public static Week getWeek(String date) {
		Week week = null;
		DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null) {
			Date myDate = StringToDate(date, dateStyle);
			week = getWeek(myDate);
		}
		return week;
	}

	/**
	 * 获取日期的星期。失败返回null。
	 *
	 * @param date 日期
	 * @return 星期
	 */
	public static Week getWeek(Date date) {
		Week week = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		switch (weekNumber) {
			case 0:
				week = Week.SUNDAY;
				break;
			case 1:
				week = Week.MONDAY;
				break;
			case 2:
				week = Week.TUESDAY;
				break;
			case 3:
				week = Week.WEDNESDAY;
				break;
			case 4:
				week = Week.THURSDAY;
				break;
			case 5:
				week = Week.FRIDAY;
				break;
			case 6:
				week = Week.SATURDAY;
				break;
		}
		return week;
	}

	/**
	 * 获取两个日期相差的天数
	 *
	 * @param date      日期字符串
	 * @param otherDate 另一个日期字符串
	 * @return 相差天数
	 */
	public static long getIntervalDays(String date, String otherDate) {
		return getIntervalDays(StringToDate(date), StringToDate(otherDate));
	}

	/**
	 * @param date      日期
	 * @param otherDate 另一个日期
	 * @return 相差天数
	 */
	public static long getIntervalDays(Date date, Date otherDate) {
		date = DateUtil.StringToDate(DateUtil.getDate(date));
		long time = date.getTime() - otherDate.getTime();
		return time / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取两个时间中的每一天
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<Date> getPerDay(Date startTime,Date endTime ){
		//定义一个接受时间的集合
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(startTime);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(startTime);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(endTime);
		// 测试此日期是否在指定日期之后
		while (endTime.after(calBegin.getTime()))  {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}

}
