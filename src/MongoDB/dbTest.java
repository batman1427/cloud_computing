package MongoDB;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class dbTest{
   public static void main( String args[] ){
      /*try{   
       // 连接到 mongodb 服务
         MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
       
         // 连接到数据库
         MongoDatabase mongoDatabase = mongoClient.getDatabase("db");  
       System.out.println("Connect to database successfully");
        
      }catch(Exception e){
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
     }*/
	   dbTest t=new dbTest();
	   String[][] a=t.readColData();
	   for(int i=0;i<a.length;i++) {
		   //System.out.print(a[i][0]+"  ");
		   //System.out.println(a[i][1]);
		   if(a[i][1].equals("锦州港")) {
			   System.out.println(i);
		   }
	   }
	   t.createColData();
   }
   
   
   /** 
    * 数据读取 
    */  
   public String[][] readColData(){  
	   Mongo mongoClient = new Mongo( "localhost" , 27017 );
       
       // 连接到数据库
	   DB db = mongoClient.getDB("db");  
       DBCollection dbCol = db.getCollection("alllist");  
       DBCursor ret = dbCol.find();  
       System.out.println("从数据集中读取数据："); 
       int i=0;
       String[][] alllist=new String[3420][2];
       while(ret.hasNext()){  
           BasicDBObject bdbObj = (BasicDBObject) ret.next();  
           if(bdbObj != null){  
               //System.out.print("code:"+bdbObj.getString("code"));  
               //System.out.println("name:"+bdbObj.getString("name")); 
        	   if(bdbObj.getString("code").startsWith("6")) {
        		   alllist[i][0]="SH"+bdbObj.getString("code");
        	   }else {
               alllist[i][0]="SZ"+bdbObj.getString("code");
        	   }
               alllist[i][1]=bdbObj.getString("name");
               i++;
           }  
       }
       //System.out.println(i);
       return  alllist;
   }
   
   
   
   
   //插入数据
   private static void createColData(){  
	   Mongo mongoClient = new Mongo( "localhost" , 27017 );
       
       // 连接到数据库
	   DB db = mongoClient.getDB("db");  
       DBCollection dbCol = db.getCollection("test");  
     /*  BasicDBObject queryObject = new BasicDBObject("name","小李");
       DBObject obj = dbCol.findOne(queryObject);
       if(obj==null) {
       System.out.println(123);
       }else {
       System.out.println(456);
       }*/
       ArrayList<DBObject> dbList = new ArrayList<DBObject>();  
       BasicDBObject doc1 = new BasicDBObject();  
       doc1.put("name", "小李");  
       doc1.put("age", 30);  
       doc1.put("address", "江苏南京");  
       dbList.add(doc1);  

       
       List<DBObject> dbList1 = new ArrayList<DBObject>();  
       BasicDBObject doc4 = new BasicDBObject();  
       doc4.put("name", "小李");  
       doc4.put("age", 30);  
       doc4.put("address", "江苏南京");  
       dbList1.add(doc4);
       
       
       BasicDBObject doc2 = new BasicDBObject();  
       doc2.put("name", "小张");  
       doc2.put("age", 25);  
       doc2.put("address", dbList1);  
       dbList.add(doc2);  
         
       dbCol.insert(dbList);  
       System.out.println("向数据集中插入数据完成！");  
       System.out.println("------------------------------");  
   }  
}