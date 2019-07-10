package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mongodb.BasicDBObject;

public class JavaUtil {
	
    public static void log(HttpServletRequest request , Map reqPrms, HttpSession session, String url){
        // System.out.println("시간 : "+DateUtil.getYYYY_MM_DD_HH_MI_SS());
        // System.out.println("아이피 : "+request.getRemoteAddr());
        // System.out.println("세션 : "+session.getAttribute("email"));
        // System.out.println("URL : "+url);
        
    }
    
	public static String getSHA256(String str) {
		String SHA = "";
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			SHA = sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			SHA = null;
		}

		return SHA;
	}
	
	public static String PrimarySet() {
		String rule_id="";
		try {			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(System.currentTimeMillis()));
			String time = new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());
			int msec = Calendar.getInstance().get(Calendar.MILLISECOND);
			Random oRandom = new Random();
			int intValue = oRandom.nextInt(1000000000);
			String intSub = String.format("%10d", intValue);
			rule_id = time + msec + intSub;
			rule_id = encrypt(rule_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rule_id;
	}
	
	public static String encrypt(String input) throws NoSuchAlgorithmException {

		String output = "";
		StringBuffer sb = new StringBuffer();
		MessageDigest md = MessageDigest.getInstance("SHA-512");

		md.update(input.getBytes());
		byte[] msgb = md.digest();

		for (int i = 0; i < msgb.length; i++) {
			byte temp = msgb[i];
			String str = Integer.toHexString(temp & 0xFF);
			while (str.length() < 2) {
				str = "0" + str;
			}
			str = str.substring(str.length() - 2);
			sb.append(str);
		}

		output = sb.toString();

		return output;
	}
	
	
	public static void convertArray(Map map , JSONArray arr){
		Set keys = map.keySet();
		Iterator itr = keys.iterator();
		while(itr.hasNext()){
			String key= ""+itr.next();
			Object value = map.get(key);
			JSONObject job = new JSONObject();
			if(key.equals("낙찰결과")){
				continue;
			}else{
				job.put(key,value);
			}
			
			arr.add(job);
			
			
		}
	}
	
	public static void convertDBObject(Map map , BasicDBObject dbo){
		Set keys = map.keySet();
		Iterator itr = keys.iterator();
		while(itr.hasNext()){
			String key= ""+itr.next();
			Object value = map.get(key);
			//BasicDBObject job = new BasicDBObject();
			dbo.put(key,value);
			
			
			
		}
	}
	
	public static void convertArray2(Map map , JSONArray arr){
		Set keys = map.keySet();
		Iterator itr = keys.iterator();
		while(itr.hasNext()){
			String key= ""+itr.next();
			Object value = map.get(key);
			JSONObject job = new JSONObject();
		
			job.put(key,value);
			
			
			arr.add(job);
			
			
		}
	}
	
	public static void putMap(Map reqPrms, BasicDBObject response){
		
		Set keys = reqPrms.keySet();
		Iterator itr = keys.iterator();
		while(itr.hasNext()){
			String key = ""+itr.next();
			Object value = reqPrms.get(key);
			response.put(key,value);
		}
		
	}
	
	public static String replace(String rep) {
		rep = rep.replaceAll("\"", "");
		if (rep.equals("null")) {
			rep = null;
		}
		return rep;
	}

	public static String trim(String trim) {
		trim = trim.replaceAll("\\p{Space}", "");
		return trim;
	}

	public static Date getTime(String time, String format) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getTime(String format, Date date) {
		String time = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		time = new SimpleDateFormat(format).format(cal.getTime());
		return time;
	}

	public static String getTime(String format, Long dateTime) {
		String time = "";
		Calendar cal = Calendar.getInstance();
		Date date = new Date(dateTime);
		cal.setTime(date);
		time = new SimpleDateFormat(format).format(cal.getTime());
		return time;
	}

	public static String fillZeroTwo(int count) {
		String contents = String.format("%02d", count);
		return contents;
	}

	public static String fillZeroThree(int count) {
		String contents = String.format("%03d", count);
		return contents;
	}
	
	public static String comma(String num) {
		  DecimalFormat df = new DecimalFormat("#,###");
		  return df.format(Long.parseLong(num));
	}
}
