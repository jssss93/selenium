package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/*
 * 날짜 형식 관련 문자열을 처리하는 유틸 클래스 
 */
public class DateUtil {
	
	public static void changeDate(Map reqPrms){
		String ss_date = (""+reqPrms.get("ss_date")).replaceAll("-", "") ;
		String se_date = (""+reqPrms.get("se_date")).replaceAll("-", "");
		reqPrms.put("ss_date",ss_date);
		reqPrms.put("se_date",se_date);
	}
	
	public static long getLongYYYY_MM_DD(String date) throws ParseException {
		return getLong(date, "yyyy-MM-dd");
	}

	public static long getLongYYYY_MM_DD_HH_MI_SS(String date) throws ParseException {
		return getLong(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static long getLong(String date, String format) throws ParseException {
		SimpleDateFormat _format = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(_format.parse(date));

		return calendar.getTimeInMillis();
	}
        
        public static String getYYYY() {
                return getForamtedDate("yyyy");
        }
	
	public static String getYYYYMMDD() {
		return getForamtedDate("yyyyMMdd");
	}
	public static String getYYYY_MM_DD_HH_MI() {
		return getForamtedDate("yyyy-MM-dd HH:mm");
	}
	public static String getYYYY_MM_DD() {
		return getForamtedDate("yyyy-MM-dd");
	}

	public static String getYYYYMMDD(Date date) {
		return getForamtedDate(date, "yyyyMMdd");
	}

	public static String getYYYYMMDDHHMI() {
		return getForamtedDate("yyyyMMddHHmm");
	}

	public static String getYYYYMMDDHHMISS() {
		return getForamtedDate("yyyyMMddHHmmss");
	}

	public static String getYYYYMMDDHHMISSMS() {
		return getForamtedDate("yyyyMMddHHmmssSSS");
	}

	public static String getYYYY_MM_DD_HH_MI_SS_MS() {
		return getForamtedDate("yyyy-MM-dd HH:mm:ss,SSS");
	}

	public static String getYYYY_MM_DD_HH_MI_SS() {
		return getForamtedDate("yyyy-MM-dd HH:mm:ss");
	}

	
	public static String getYYYY_MMM_DD_HH_MI_SS(Date date) {
		return getForamtedDate(date, "yyyy-MMM-dd HH:mm:ss");
	}
	
	public static String getYYYY_MM_DD_HH_MI_SS(Date date) {
		return getForamtedDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getMMDDHHMISSMS() {
		return getForamtedDate("MMddHHmmssSSS");
	}

	public static String getForamtedDate(String format) {
		return getForamtedDate(new Date(), format);
	}

	public static String getForamtedDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format,Locale.ENGLISH);
		return formatter.format(date);
	}

	// 20150810 5 -> 20150815
	public static String addDays(String yyyyMMdd, int days) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(format.parse(yyyyMMdd));
		calendar.add(Calendar.DATE, days);

		return format.format(calendar.getTime());
	}
	
	// 20150810 5 -> 20150815
	public static String addDays2(String yyyyMMdd, int days) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmm");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(format.parse(yyyyMMdd));
		calendar.add(Calendar.DATE, days);

		return format.format(calendar.getTime());
	}

	// 날짜수 계산
	public static int betweenDays(String yyyyMMdd1, String yyyyMMdd2) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		int days1 = (int) ((format.parse(yyyyMMdd1).getTime() / 3600000) / 24);
		int days2 = (int) ((format.parse(yyyyMMdd1).getTime() / 3600000) / 24);

		return days2 - days1;
	}

	// 윤년여부를 판단
	public static boolean isLeapYear(int year) {
		return (((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0));
	}

	public static String date_convert(String date){
		
		if(!date.equals("")&&!date.equals("null")){
			String yy = date.substring(0, 4);
			String MM = date.substring(4, 6);
			String dd = date.substring(6, 8);
			String hh = date.substring(8,10);
			String mm = date.substring(10,12);
			date = yy+"-"+MM+"-"+dd+" "+hh+":"+mm;
		}
		
	//	// System.out.println(date);
		return date;
		
	}
	
	public static String date_convertYYYYMMDD(String date){
		
		if(!date.equals("")&&!date.equals("null")){
			String yy = date.substring(0, 4);
			String MM = date.substring(4, 6);
			String dd = date.substring(6, 8);
			date = yy+"-"+MM+"-"+dd;
		}
		
	//	// System.out.println(date);
		return date;
		
	}
}
/**
 * Communication & Knowledge
 */
