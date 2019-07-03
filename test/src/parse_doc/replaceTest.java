package parse_doc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class replaceTest {
	public static void main(String args[]) {
		
		String str="(二�)�꽦�떊嫄댁꽕";
		str=str.replaceAll("\\(", "\\\\(");
		str=str.replaceAll("\\)", "\\\\)");
//		
		
        
//        List list =new ArrayList();
//        BasicDBObject BDBO = (BasicDBObject) dbs.eval("db.g2b_comp.find({'corpNm':{'$regex':'"+str+"'}})");
//      BasicDBObject BDBO = (BasicDBObject) dbs.eval("db.g2b_comp.find({'corpNm':'(二�)�꽦�떊嫄댁꽕'})");
        
//        System.out.println(BDBO);
        
       
        System.out.println("----------------");
        System.out.println(str);
//        list.add(BDBO);
        BasicDBObject match =new BasicDBObject();
        match.put("corpNm", str);
        List list= find("g2b_comp", match,0,5);
        
        
        System.out.println(list);
        
	    
	}
	public static List find(String collection, BasicDBObject match, int skip, int limit) {
		String mongoUrl = "192.168.10.104";
	    String mongoPort = "27109";
	    String mongoDbName = "g2b";
        MongoClient cs = null;
        DB dbs = null;
        List list = new ArrayList();
        DBCursor cursor = null;
        try {
            cs = new MongoClient(mongoUrl, Integer.parseInt(mongoPort));
            dbs = cs.getDB(mongoDbName);
            DBCollection coll = dbs.getCollection(collection);
            cursor = coll.find(match).skip(skip).limit(limit);
            System.out.println("db."+collection+".find("+match+").skip("+skip+").limit("+limit+")");
            Iterator itor = cursor.iterator();
            while(itor.hasNext()){
                list.add(itor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (cs != null)
                cs.close();
        }
        return list;
    }
	
}
