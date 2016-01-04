package org.singledog.wechat.sdk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static final String date_pattern = "yyyy-MM-dd";
	public static final String time_pattern = "yyyy-MM-dd HH:mm:ss";
	
	public static String formatDate(Date date){
		if(date == null)
			return null;
		return new SimpleDateFormat(date_pattern).format(date);
	}
	
	public static String format(String pattern ,Date date){
		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static String formatTime(Date date){
		if(date == null)
			return "";
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	} 
	
	public static Date parseDate(String datestr){
		try {
			return new SimpleDateFormat(date_pattern).parse(datestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Date parseTime(String time){
		Date result = null;
		if(time == null || "".equals(time)){
			return null;
		}
		
		try {
			result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Date add(Date date, int mount, int field){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, mount);
		return cal.getTime();
	}
	
	/**
	 * date 2 - date1
	 * @param date1
	 * @param date2
	 * @param type {@link Calendar}
	 * @return
	 */
	public static int diff(Date date1, Date date2, int type){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		int d1 = cal.get(type);  
		cal.setTime(date2);
		return cal.get(type) - d1;
	}
	
	public static String timeStr(long time){
		Date date = new Date(time);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public static Date tomorrowDay(){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	/**
	 * caculate minutes between two time
	 * @param timeEarly format 12:03
	 * @param timeLater  format 15:03 
	 * @return
	 */
	public static double minutesBetween(String timeEarly, String timeLater){
		String[] earlys = timeEarly.split(":");
		int time = Integer.parseInt(earlys[0]) + Integer.parseInt(earlys[1]) * 60;
		earlys = timeLater.split(":");
		int time2 = Integer.parseInt(earlys[0]) + Integer.parseInt(earlys[1]) * 60;
		return (time2 - time) / 60;
	}
	
}
