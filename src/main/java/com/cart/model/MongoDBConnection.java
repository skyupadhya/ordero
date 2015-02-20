package com.cart.model;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class MongoDBConnection {
	DB con;
	
	static Map<String,String> collectionMap = new HashMap<String, String>();
	static{
		collectionMap.put("car", "cars");
		collectionMap.put("tv", "tvs");
		collectionMap.put("router","routers");
	}

	public MongoDBConnection(){
		try{
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			con = mongoClient.getDB("ecommerce-db");
			System.out.println("Successfully connected to MongoDB");
		}
		catch(MongoException e)
		{
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DBObject mongoDBGetProduct(String prodType, int prodId) {
		DBCollection collection = con.getCollection(collectionMap.get(prodType));
		BasicDBObject query = new BasicDBObject("_id", prodId);
		DBObject obj = null;
		
		DBCursor cursor = collection.find(query);
		while(cursor.hasNext()) {
			obj = cursor.next();
		    System.out.println(obj);
		}
		
		return obj;
	}
	
	public boolean mongoDBProdAvail(String prodType, int prodId) {
		DBCollection collection = con.getCollection(collectionMap.get(prodType));
		BasicDBObject query = new BasicDBObject("_id", prodId);
		DBObject obj = null;
		
		DBCursor cursor = collection.find(query);	
		while(cursor.hasNext()) {
		    obj = cursor.next();
		    System.out.println(obj);
		    
		    String startDate = (String) obj.get("startdate");
			String endDate = (String) obj.get("enddate");
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String todayDate = sdf.format(new Date());
			try {
				Date start = sdf.parse(startDate);
				Date today = sdf.parse(todayDate);
				
				if (endDate == "00-00-0000") {
					if (today.after(start))
					    return true;
					else 
						return false;
				}
				
				Date end = sdf.parse(endDate);
				
				if(today.after(start) && today.before(end))
					return true;
				else 
				    return false;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
}