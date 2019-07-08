

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import net.sf.jasperreports.engine.util.JsonUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ParseJSON_CJS {
	public static JSONArray convertListToJson(List<Map<String, Object>> bankCdList) {

		JSONArray jsonArray = new JSONArray();

		for (Map<String, Object> map : bankCdList) {

			jsonArray.add(convertMapToJson(map));

		}

		return jsonArray;

	}
	public static JSONArray listToJsonArray(List list){
		JSONArray rslt = new JSONArray();
		for (int i=0; i<list.size(); i++){				
			Map cmap = (Map)list.get(i);
			Set keys = cmap.keySet();
			Iterator itr = keys.iterator();				
			while(itr.hasNext()){
				JSONObject job = new JSONObject();
				String key = ""+itr.next();
				String value = ""+cmap.get(key);
				job.put(key, value);
				rslt.add(job);
			}
		}		
		return rslt;
	}

	public static JSONObject convertMapToJson(Map<String, Object> map) {

		JSONObject json = new JSONObject();

		for (Map.Entry<String, Object> entry : map.entrySet()) {

			String key = entry.getKey();

			Object value = entry.getValue();

			// json.addProperty(key, value);

			json.put(key, value);

		}

		return json;

	}
	public static String getTest(String str) {
		return str;
	}
	
	
	 public static JSONObject getJsonStringFromMap( Map<String, Object> map )
	    {
	        JSONObject jsonObject = new JSONObject();
	        for( Map.Entry<String, Object> entry : map.entrySet() ) {
	            String key = entry.getKey();
	            Object value = entry.getValue();
	            jsonObject.put(key, value);
	        }
	        
	        return jsonObject;
	    }
	    
	    /**
	     * List<Map>을 jsonArray로 변환한다.
	     *
	     * @param list List<Map<String, Object>>.
	     * @return JSONArray.
	     */
	    public static JSONArray getJsonArrayFromList( List<Map<String, Object>> list )
	    {
	        JSONArray jsonArray = new JSONArray();
	        for( Map<String, Object> map : list ) {
	            jsonArray.add( getJsonStringFromMap( map ) );
	        }
	        
	        return jsonArray;
	    }
	    
	    /**
	     * List<Map>을 jsonString으로 변환한다.
	     *
	     * @param list List<Map<String, Object>>.
	     * @return String.
	     */
	    public static String getJsonStringFromList( List<Map<String, Object>> list )
	    {
	        JSONArray jsonArray = getJsonArrayFromList( list );
	        return jsonArray.toJSONString();
	    }
	 
	    /**
	     * JsonObject를 Map<String, String>으로 변환한다.
	     *
	     * @param jsonObj JSONObject.
	     * @return Map<String, Object>.
	     */
	    //여기부터 제대로 작동안함. 2019.01.28 
	    //수정할것.
	    public static Map<String, Object> getMapFromJsonObject( JSONObject jsonObj )
	    {
	        Map<String, Object> map = new HashMap<>();
	        
//	        try {
//	            
//	            map = new ObjectMapper().readValue(jsonObj.toJSONString(), Map.class) ;
//	            
//	        } catch (JsonParseException e) {
//	            e.printStackTrace();
//	        } catch (JsonMappingException e) {
//	            e.printStackTrace();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
	        
	        
	        Set key = jsonObj.keySet();
	        Set Value= jsonObj.entrySet();
	        
            // Iterator 설정
            Iterator<String> iter = key.iterator();

            // 각각 키 값 출력
            while(iter.hasNext())
            {
                String keyname = iter.next();
                System.out.println("key : "+keyname+" type : "+jsonObj.get(keyname).getClass());
                System.out.println("value : "+jsonObj.get(keyname));
                map.put(keyname, jsonObj.get(keyname));
            }
	 
	        return map;
	    }
	 
	    /**
	     * JsonArray를 List<Map<String, String>>으로 변환한다.
	     *
	     * @param jsonArray JSONArray.
	     * @return List<Map<String, Object>>.
	     */
	    public static List<Map<String, Object>> getListMapFromJsonArray( JSONArray jsonArray )
	    {
	    	 System.out.println("아예안들어가????");
	        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	        System.out.println("아예안들어가????");
	        if( jsonArray != null )
	        {
	        	System.out.println(jsonArray);
	            int jsonSize = jsonArray.size();
	            for( int i = 0; i < jsonSize; i++ )
	            {
	                Map<String, Object> map = getMapFromJsonObject( ( JSONObject ) jsonArray.get(i) );
	                list.add( map );
	                System.out.println(i+"번째");
	                System.out.println(list);
	            }
	        }
	        
	        return list;
	    }

}
