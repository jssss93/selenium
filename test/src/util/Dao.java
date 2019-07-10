package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import ch.qos.logback.classic.LoggerContext;
import ga.g2b.com.util.DateUtil;

@Controller
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Dao {

//	private static String mongoUrl = "192.168.10.104";
//	private static String mongoPort = "27109";
	private static String mongoUrl = "192.168.0.7";
	private static String mongoPort = "29107";
	private static String mongoDbName = "g2b";

	// 커넥션 풀을 만들기 위한 기본 설정값을 저장하는 객체
	static Builder options = new Builder();

	static Builder options() {
		options.connectionsPerHost(30); // 시작 시 30개의 풀을 만들고 시작
		options.minConnectionsPerHost(10); // 최소 10개를 유지
		return options;
	}

	static {
		((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver")
				.setLevel(ch.qos.logback.classic.Level.ERROR);
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE); // e.g. or Log.WARNING, etc.
		Logger logger = Logger.getLogger("com.mongodb");
		logger.setLevel(Level.SEVERE);
	}

	static MongoClientURI uri = new MongoClientURI("mongodb://" + mongoUrl + ":" + mongoPort, options());

	public static void insert(String collection, DBObject doc) {
		MongoClient cs = null;
		DB dbs = null;
		try {
			
			cs = new MongoClient(uri);
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			doc.removeField("_id");
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".insert(" + doc + ")");
			coll.insert(doc);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cs != null) {
				cs.close();

			}
		}
	}

	public static void insert(String collection, List list) {
		MongoClient cs = null;
		DB dbs = null;
		try {
			cs = new MongoClient(uri);
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".insert(" + list + ")");
			coll.insert(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cs != null)
				cs.close();
		}
	}

	public static void remove(String collection, DBObject match) {
		if (match != null) {
			MongoClient cs = null;
			DB dbs = null;
			try {
				cs = new MongoClient(uri);
				dbs = cs.getDB(mongoDbName);
				DBCollection coll = dbs.getCollection(collection);
				if (collection.equals("obid")) {
					System.out.println("쿼리 다시 확인할 것");
				} else if (match == null) {
					System.out.println("쿼리 다시 확인할 것");
				} else {
					System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".remove(" + match + ")");
					coll.remove(match);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cs != null)
					cs.close();
			}
		}

	}

	public static void update(String collection, DBObject match, DBObject set, boolean upsert, boolean multi) {
		MongoClient cs = null;
		DB dbs = null;
		try {
			cs = new MongoClient(uri);
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".update(" + match + "," + set + "," + upsert + "," + multi + ")");
			coll.update(match, set, upsert, multi);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cs != null)
				cs.close();
		}
	}

	public static void updateList(String collection, DBObject match, DBObject add, boolean upsert, boolean multi) {
		MongoClient cs = null;
		DB dbs = null;
		try {
			cs = new MongoClient(uri);
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			BasicDBObject addt = new BasicDBObject("$addToSet", add);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".update(" + match + "," + addt + "," + upsert + "," + multi + ")");
			coll.update(match, addt, upsert, multi);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cs != null)
				cs.close();
		}
	}

	public static List hint(String collection, BasicDBObject match, String hint) {
		MongoClient cs = null;
		DB dbs = null;
		List list = new ArrayList();
		DBCursor cursor = null;
		try {
			cs = new MongoClient(uri);
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".find(" + match + ").hint(" + hint + ")");
			cursor = coll.find(match).hint(hint);
			Iterator itor = cursor.iterator();
			while (itor.hasNext()) {
				list.add(itor.next());
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
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

	public static List find(String collection, BasicDBObject match) {
		MongoClient cs = null;
		DB dbs = null;
		DBCursor cursor = null;
		List list = new ArrayList();
		try {
			cs = new MongoClient(uri);
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			StackTraceElement[] a = new Throwable().getStackTrace();
			System.out.println("/////////////////////");
			for (int i = 0; i < a.length; i++) {
				if (i < 3) {
					System.out.println("클래스 - " + a[i].getClassName());
					System.out.println(", 메소드 - " + a[i].getMethodName());
					System.out.println(", 라인 - " + a[i].getLineNumber());
				}
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".find(" + match + ")");
			System.out.println("/////////////////////");
			cursor = coll.find(match);
			Iterator itor = cursor.iterator();
			while (itor.hasNext()) {
				list.add(itor.next());
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
		} catch (Exception e) {

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (cs != null)
				cs.close();
		}
		return list;
	}

	public static List findField_sort(String collection, BasicDBObject match, BasicDBObject field, BasicDBObject sort) {
		MongoClient cs = null;
		DB dbs = null;
		DBCursor cursor = null;
		List list = new ArrayList();
		try {
			cs = new MongoClient(mongoUrl, Integer.parseInt(mongoPort));
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".find(" + match + "," + field + ").sort(" + sort + ")");
			cursor = coll.find(match, field);
			Iterator itor = cursor.iterator();
			while (itor.hasNext()) {
				list.add(itor.next());
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
		} catch (Exception e) {

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (cs != null)
				cs.close();
		}
		return list;
	}
	public static List findField_sortLimit(String collection, BasicDBObject match, BasicDBObject field, BasicDBObject sort,int skip, int limit) {
		MongoClient cs = null;
		DB dbs = null;
		DBCursor cursor = null;
		List list = new ArrayList();
		try {
			cs = new MongoClient(mongoUrl, Integer.parseInt(mongoPort));
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".find(" + match + "," + field + ").sort(" + sort + ").skip("+skip+").limit("+limit+")");
			cursor = coll.find(match, field).sort(sort).skip(skip).limit(limit);
			Iterator itor = cursor.iterator();
			while (itor.hasNext()) {
				list.add(itor.next());
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
		} catch (Exception e) {

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (cs != null)
				cs.close();
		}
		return list;
	}
	

	public static List findField(String collection, BasicDBObject match, BasicDBObject field) {
		MongoClient cs = null;
		DB dbs = null;
		DBCursor cursor = null;
		List list = new ArrayList();
		try {
			cs = new MongoClient(uri);
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".find(" + match + "," + field + ")");
			cursor = coll.find(match, field);
			Iterator itor = cursor.iterator();
			while (itor.hasNext()) {
				list.add(itor.next());
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
		} catch (Exception e) {

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (cs != null)
				cs.close();
		}
		return list;
	}

	public static List findFieldLimit(String collection, BasicDBObject match, BasicDBObject field, int skip,
			int limit) {
		MongoClient cs = null;
		DB dbs = null;
		DBCursor cursor = null;
		List list = new ArrayList();
		try {
			cs = new MongoClient(uri);
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".find(" + match + "," + field + ").skip(" + skip + ").limit(" + limit + ")");
			cursor = coll.find(match, field).skip(skip).limit(limit);
			Iterator itor = cursor.iterator();
			while (itor.hasNext()) {
				list.add(itor.next());
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
		} catch (Exception e) {

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (cs != null)
				cs.close();
		}
		return list;
	}

	public static List find(String collection, BasicDBObject match, BasicDBObject sort) {
		MongoClient cs = null;
		DB dbs = null;
		DBCursor cursor = null;
		List list = new ArrayList();
		try {
			cs = new MongoClient(uri);
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".find(" + match + ").sort(" + sort + ")");
			cursor = coll.find(match).sort(sort);
			list = cursor.toArray();
//			Iterator itor = cursor.iterator();
//			while (itor.hasNext()) {
//				list.add(itor.next());
//			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
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

	public static List find(String collection, BasicDBObject match, BasicDBObject match2, BasicDBObject sort, int skip,
			int limit) {
		MongoClient cs = null;
		DB dbs = null;
		List list = new ArrayList();
		DBCursor cursor = null;
		try {
			cs = new MongoClient(mongoUrl, Integer.parseInt(mongoPort));
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".find(" + match + "," + match2 + ").sort(" + sort + ").skip("+ skip + ").limit(" + limit + ")");
			cursor = coll.find(match, match2).sort(sort).skip(skip).limit(limit);
			Iterator itor = cursor.iterator();
			while (itor.hasNext()) {
				list.add(itor.next());
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
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

	public static List find(String collection, BasicDBObject match, int skip, int limit) {
		MongoClient cs = null;
		DB dbs = null;
		List list = new ArrayList();
		DBCursor cursor = null;
		try {
			cs = new MongoClient(uri);
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".find(" + match + ").skip(" + skip + ").limit(" + limit + ")");
			cursor = coll.find(match).skip(skip).limit(limit);
			Iterator itor = cursor.iterator();
			while (itor.hasNext()) {
				list.add(itor.next());
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
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

	public static List find(String collection, BasicDBObject match, BasicDBObject sort, int skip, int limit) {
		MongoClient cs = null;
		DB dbs = null;
		List list = new ArrayList();
		DBCursor cursor = null;
		try {
			cs = new MongoClient(uri);
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".find(" + match + ").sort(" + sort + ").skip(" + skip + ").limit("+ limit + ")");
			cursor = coll.find(match).sort(sort).skip(skip).limit(limit);
			Iterator itor = cursor.iterator();
			while (itor.hasNext()) {
				list.add(itor.next());
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
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

	public static Long count(String collection, BasicDBObject match) {
		MongoClient cs = null;
		DB dbs = null;
		long cnt = 0;

		try {
			cs = new MongoClient(uri);
			dbs = cs.getDB(mongoDbName);
			DBCollection coll = dbs.getCollection(collection);
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db." + collection + ".find(" + match + ").count()");
			cnt = coll.find(match, new BasicDBObject("_id",1)).count();
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cs != null)
				cs.close();
		}
		return cnt;
	}

	public static List aggregate(String collection, List list) {
		MongoClient cs = null;
		List result = new ArrayList();
		try {
			cs = new MongoClient(mongoUrl, Integer.parseInt(mongoPort));
			MongoDatabase db = null;
			MongoCollection col = null;
			db = cs.getDatabase(mongoDbName);
			col = db.getCollection(collection);
			
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db."+collection+".aggregate("+list+")");
			AggregateIterable<Document> output = col.aggregate(list);
			for (Document dbObject : output) {
				BasicDBObject basicDBObject = new BasicDBObject(dbObject);
				result.add(basicDBObject);
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cs != null)
				cs.close();
		}
		return result;
	}
	
	public static Long aggregatecount(String collection, List list) {
		MongoClient cs = null;
		long result = 0L;
		try {
			cs = new MongoClient(mongoUrl, Integer.parseInt(mongoPort));
			MongoDatabase db = null;
			MongoCollection col = null;
			db = cs.getDatabase(mongoDbName);
			col = db.getCollection(collection);
			BasicDBObject totc = (BasicDBObject)JSON.parse("{'$group':{'_id':'null','total_count':{'$sum':1}}}");
			list.add(totc);
			
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db."+collection+".aggregate("+list+")");
			AggregateIterable<Document> output = col.aggregate(list);
			for (Document dbObject : output) {
				BasicDBObject basicDBObject = new BasicDBObject(dbObject);
				result = Long.parseLong(""+basicDBObject.get("total_count"));
			}
				System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cs != null)
				cs.close();
		}
		return result;
	}
	
	public static List aggregate_lookup(String collection, BasicDBObject match, BasicDBObject project, BasicDBObject lookup, BasicDBObject sort) {
		MongoClient cs = null;
		List result = new ArrayList();
		try {
			cs = new MongoClient(mongoUrl, Integer.parseInt(mongoPort));
			MongoDatabase db = null;
			MongoCollection col = null;
			db = cs.getDatabase(mongoDbName);
			col = db.getCollection(collection);
			
			AggregateIterable<Document> output = col.aggregate( Arrays.asList( 
					new Document("$match"	, match),
	                new Document("$project"	, project),
	                new Document("$lookup"	, lookup),
	                new Document("$sort"	, sort)
	                		));
			for (Document dbObject : output) {
				BasicDBObject basicDBObject = new BasicDBObject(dbObject);
				result.add(basicDBObject);
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cs != null)
				cs.close();
		}
		return result;
	}

	public static List aggregate_lookup(String collection, BasicDBObject match, BasicDBObject project, BasicDBObject lookup, BasicDBObject sort,int skip,int limit) {
		MongoClient cs = null;
		List result = new ArrayList();
		try {
			cs = new MongoClient(mongoUrl, Integer.parseInt(mongoPort));
			MongoDatabase db = null;
			MongoCollection col = null;
			db = cs.getDatabase(mongoDbName);
			col = db.getCollection(collection);
			
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"db."+collection+".aggregate({$match:{"+match+"},{$project:"+project+"},{$lookup:"+lookup+"},{$sort:"+sort+"},{$skip:"+skip+"},{$limit:"+limit+"})");
			AggregateIterable<Document> output = col.aggregate( 
				Arrays.asList( 
					new Document("$match"	, match),
	                new Document("$project"	, project),
	                new Document("$lookup"	, lookup),
	                new Document("$sort"	, sort), 
	                new Document("$skip"	, skip),
	                new Document("$limit"	, limit)
	            )
			);
			for (Document dbObject : output) {
				BasicDBObject basicDBObject = new BasicDBObject(dbObject);
				result.add(basicDBObject);
			}
			System.out.println(DateUtil.getYYYY_MM_DD_HH_MI_SS()+"return !!!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cs != null)
				cs.close();
		}
		return result;
	}
}
