package MongoDB;
import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;


public class crawler {
    
	
    public static void main(String[] args) throws JSONException, IOException {   
    	   //爬取的范围
    	   //int start=0;9;11;114;687;1219；1578;2141;2720;3227;
           
    	   //获取股票代码和名称
           dbTest t=new dbTest();
           String[][]  alllist=t.readColData();
           
           crawler jHtml = new crawler();
           //读取全部数据
           for(int i=3227;i<alllist.length;i++) {
        	   try {
        	   jHtml.newposts(i,alllist[i][0],alllist[i][1]);
        	   }
        	   catch (Exception e)
               {

                   continue;
               }
	       
           }
    
    	
 	    //crawler jHtml = new crawler();
 	  // https://xueqiu.com/service/comments?reply=true&filtered=true&id=93720515&callback=jQuery183024847112501443247_1508599275774
 	 

 		   //jHtml.getUser("8122293918", "linvestor");
 	   // jHtml.newposts(0,"SZ300434","金石东方");
		//Document doc = Jsoup.connect("https://xueqiu.com/statuses/search.json?count=10&comment=0&symbol=SH603396&hl=0&source=all&sort=time&page=3&_="+System.currentTimeMillis()).header("Content-type","application/json").header("Accept","application/json, text/javascript, */*; q=0.01" ).header("Accept-Encoding","gzip, deflate, br" ).header("Accept-Language","zh-CN,zh;q=0.9" ).header("cache-control","no-cache" ).header("Connection","keep-alive" ).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36").cookie("Cookie", "s=fj11iz0ydu; u=391508426962256; device_id=d142957add2ca405e7e1a09f80ba6ab5; webp=0; aliyungf_tc=AQAAAFAHQH3NwwIAZ1Gi0+fK6jn5aT2q; xq_a_token=e3cae829e5836e234be00887406080b41c2cb69a; xq_r_token=319673aba44e00bd0fed3702652be32b2349860e; __utma=1.877700155.1508426963.1508426963.1508482430.2; __utmc=1; __utmz=1.1508426963.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Hm_lvt_1db88642e346389874251b5a1eded6e3=1508426963,1508482430; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1508482677").ignoreContentType(true).get();                    
       
		//System.out.println(doc.text());
 	    /*
 	     * 
 	     * JSONObject jo =new org.json.JSONObject(doc.text().toString());
		JSONObject jo1 =new org.json.JSONObject(jsonString(doc.text()));
		jsonString();可以解决json内容包含双引号的问题
 	     * 
 	     * 
 	     * 
 	     * */
		//JSONObject jo =new org.json.JSONObject(doc.text().toString());
		//JSONObject jo1 =new org.json.JSONObject(jsonString(doc.text()));
		//System.out.println(jo1.get("symbol"));
    }
    private static String jsonString(String s){
        char[] temp = s.toCharArray();       
        int n = temp.length;
        for(int i =0;i<n;i++){
            if(temp[i]==':'&&temp[i+1]=='"'){
                    for(int j =i+2;j<n;j++){
                        if(temp[j]=='"'){
                            if(temp[j+1]!=',' &&  temp[j+1]!='}'){
                                temp[j]='”';
                            }else if(temp[j+1]==',' ||  temp[j+1]=='}'){
                                break ;
                            }
                        }
                    }   
            }
        }       
        return new String(temp);
    }
    //爬数据   传入股票代码和名字
    public void newposts(int start,String code,String name) throws JSONException{
    	
        try {
                    
        			//存入数据库准备
        			Mongo mongoClient = new Mongo( "localhost" , 27017 );
		       
        			// 连接到数据库
        			DB db = mongoClient.getDB("db");  
        			DBCollection dbCol = db.getCollection("post");
        	        //构建链接
        			Document doc = Jsoup.connect("https://xueqiu.com/statuses/search.json?count=10&comment=0&symbol="+code+"&hl=0&source=all&sort=time&page=1&_="+System.currentTimeMillis()).header("Content-type","application/json").header("Accept","application/json, text/javascript, */*; q=0.01" ).header("Accept-Encoding","gzip, deflate, br" ).header("Accept-Language","zh-CN,zh;q=0.9" ).header("cache-control","no-cache" ).header("Connection","keep-alive" ).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36").cookie("Cookie", "s=fj11iz0ydu; u=391508426962256; device_id=d142957add2ca405e7e1a09f80ba6ab5; webp=0; aliyungf_tc=AQAAAFAHQH3NwwIAZ1Gi0+fK6jn5aT2q; xq_a_token=e3cae829e5836e234be00887406080b41c2cb69a; xq_r_token=319673aba44e00bd0fed3702652be32b2349860e; __utma=1.877700155.1508426963.1508426963.1508482430.2; __utmc=1; __utmz=1.1508426963.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Hm_lvt_1db88642e346389874251b5a1eded6e3=1508426963,1508482430; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1508482677").ignoreContentType(true).get();                    
                    //System.out.println(doc.text());
        			//获取json
        		
        			JSONObject jo =new org.json.JSONObject(jsonString(doc.text()));
                    //新帖总页数
        			int maxPage=Integer.valueOf(jo.getString("maxPage"));
        			
        			//逐页访问
        			int page=1;
        			for(;page<=maxPage;page++) {
        				//每一页新帖列表
            			Document temp = Jsoup.connect("https://xueqiu.com/statuses/search.json?count=10&comment=0&symbol="+code+"&hl=0&source=all&sort=time&page="+page+"&_="+System.currentTimeMillis()).header("Content-type","application/json").header("Accept","application/json, text/javascript, */*; q=0.01" ).header("Accept-Encoding","gzip, deflate, br" ).header("Accept-Language","zh-CN,zh;q=0.9" ).header("cache-control","no-cache" ).header("Connection","keep-alive" ).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36").cookie("Cookie", "s=fj11iz0ydu; u=391508426962256; device_id=d142957add2ca405e7e1a09f80ba6ab5; webp=0; aliyungf_tc=AQAAAFAHQH3NwwIAZ1Gi0+fK6jn5aT2q; xq_a_token=e3cae829e5836e234be00887406080b41c2cb69a; xq_r_token=319673aba44e00bd0fed3702652be32b2349860e; __utma=1.877700155.1508426963.1508426963.1508482430.2; __utmc=1; __utmz=1.1508426963.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Hm_lvt_1db88642e346389874251b5a1eded6e3=1508426963,1508482430; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1508482677").ignoreContentType(true).get();                    
            			JSONObject everypage =new org.json.JSONObject(jsonString(temp.text()));
        				JSONArray list = new JSONArray(jsonString(everypage.get("list").toString()));
        				for(int i=0 ; i < list.length() ;i++){                
        					//获取每一个JsonObject对象
        					JSONObject xintie = list.getJSONObject(i);
                            String shareid=code;
                            String sharename=name;
           					String userid=xintie.getString("user_id");
           					//根据用户id获取用户信息
           					JSONObject user=new org.json.JSONObject(jsonString(xintie.getString("user")));
           					String screen_name=user.getString("screen_name");
           					//用户信息插入数据库
           					getUser(userid,screen_name);
        					String postid=xintie.getString("id");
        				    String text=xintie.getString("text");
        					String created_at=xintie.getString("created_at");
        					String source=xintie.getString("source");
        					String like_count=xintie.getString("like_count");
        					String reply_count=xintie.getString("reply_count");
        					
        					//
        					ArrayList<DBObject> fatherlist = new ArrayList<DBObject>();  
        		    		BasicDBObject a = new BasicDBObject();  
        		    		
        					//评论列表
        		    		//System.out.println(postid);
        					Document comment = Jsoup.connect("https://xueqiu.com/service/comments?reply=true&filtered=true&id="+postid+"&callback=jQuery183046338746710156475_1508526685414").header("Content-type","application/json").header("Accept","application/json, text/javascript, */*; q=0.01" ).header("Accept-Encoding","gzip, deflate, br" ).header("Accept-Language","zh-CN,zh;q=0.9" ).header("cache-control","no-cache" ).header("Connection","keep-alive" ).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36").cookie("Cookie", "s=fj11iz0ydu; u=391508426962256; device_id=d142957add2ca405e7e1a09f80ba6ab5; webp=0; aliyungf_tc=AQAAAFAHQH3NwwIAZ1Gi0+fK6jn5aT2q; xq_a_token=e3cae829e5836e234be00887406080b41c2cb69a; xq_r_token=319673aba44e00bd0fed3702652be32b2349860e; __utma=1.877700155.1508426963.1508426963.1508482430.2; __utmc=1; __utmz=1.1508426963.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Hm_lvt_1db88642e346389874251b5a1eded6e3=1508426963,1508482430; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1508482677").ignoreContentType(true).post();                    
        					String str=comment.text().toString().substring(92);
        				 	String s=str.substring(0, str.length()-1);
        				 	JSONObject comm =new org.json.JSONObject(jsonString(s));
        				 	JSONArray commlist = new JSONArray(jsonString(comm.get("comments").toString()));
        				 	int countcomment=Integer.valueOf(comm.getString("count"));
        				 	//System.out.println(countcomment);
        				 	if(countcomment>20) {
        				 		countcomment=20;
        				 	}
        				 	ArrayList<DBObject> sonlist = new ArrayList<DBObject>();     
        				 	for(int l=0;l<countcomment;l++) {
        				 		try
        		                {  
        		                   
        				 		BasicDBObject b = new BasicDBObject();
        				 		JSONObject pinglun = commlist.getJSONObject(l);
        				 		String ud=pinglun.getString("user_id");
        				 		String sj=pinglun.getString("created_at");
        				 		String nr=pinglun.getString("text");
        				 		JSONObject us=new org.json.JSONObject(jsonString(pinglun.getString("user")));
               					String sn=us.getString("screen_name");
        				 		b.put("userid", ud);  
            		    		b.put("name", sn);  
            		    		b.put("time", sj);  
            		    		b.put("content", nr);
            		    		sonlist.add(b);
        		                }            
        		                catch (Exception e)
        		                {
        		 
        		                    continue;
        		                }
        				 	}
        				 	a.put("shareid", shareid);  
        				 	a.put("sharename", sharename);  
        				 	a.put("userid", userid);  
        				 	a.put("screen_name", screen_name);  
        				 	a.put("postid", postid);  
        				 	a.put("text", text);  
        				 	a.put("created_at", created_at);  
        				 	a.put("source", source);  
        		    		a.put("like_count",like_count );
        		    		a.put("reply_count", reply_count );
        		    		a.put("commment", sonlist);  
        		    		fatherlist.add(a);  
        		    		dbCol.insert(fatherlist);  
        		    		System.out.println("第"+(start+1)+"支股票"+name+" 第"+page+"页  第"+(i+1)+"条新帖插入成功!");
        				}
                    
        			}

                    
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    //爬取用户数据
    public void getUser(String userid,String screen_name) throws IOException, JSONException {
		//用户为股票
    	if(userid.equals("-1")) {
    		System.out.println("评论用户为股票主页!");
    	}else {
    		//获取用户股票代码   根据代码找出所有股票
			Document doc = Jsoup.connect("https://xueqiu.com/v4/stock/portfolio/stocks.json?size=1000&pid=-1&tuid="+userid+"&cuid=7499633898&pname=%E5%85%A8%E9%83%A8&uid="+userid+"&category=2&type=1&_="+System.currentTimeMillis()).header("Content-type","application/json").header("Accept","application/json, text/javascript, */*; q=0.01" ).header("Accept-Encoding","gzip, deflate, br" ).header("Accept-Language","zh-CN,zh;q=0.9" ).header("cache-control","no-cache" ).header("Connection","keep-alive" ).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36").cookie("Cookie", "s=fj11iz0ydu; u=391508426962256; device_id=d142957add2ca405e7e1a09f80ba6ab5; webp=0; aliyungf_tc=AQAAAFAHQH3NwwIAZ1Gi0+fK6jn5aT2q; xq_a_token=e3cae829e5836e234be00887406080b41c2cb69a; xq_r_token=319673aba44e00bd0fed3702652be32b2349860e; __utma=1.877700155.1508426963.1508426963.1508482430.2; __utmc=1; __utmz=1.1508426963.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Hm_lvt_1db88642e346389874251b5a1eded6e3=1508426963,1508482430; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1508482677").ignoreContentType(true).get();                    
			JSONObject jo =new org.json.JSONObject(jsonString(doc.text().toString()));
			JSONArray sharelist = new JSONArray(jsonString(jo.get("stocks").toString()));
			String linkurl="https://xueqiu.com/stock/quote.json?code=";
			int m=0;
			for(;m<sharelist.length();m++) {
				JSONObject temp=sharelist.getJSONObject(m);
				String code=temp.getString("code");
				linkurl=linkurl+code+"%2C";
			}
			linkurl=linkurl+"&_="+System.currentTimeMillis();
			
			//找股票的详细信息
			//存入数据库准备
		    Mongo mongoClient = new Mongo( "localhost" , 27017 );
		       
		    // 连接到数据库
			DB db = mongoClient.getDB("db");  
		    DBCollection dbCol = db.getCollection("user");
		    //判断用户是否已存在
		    BasicDBObject queryObject = new BasicDBObject("id",userid);
		    DBObject obj = dbCol.findOne(queryObject);
		    if(obj!=null) {
		    	System.out.println("用户已经存在!");
		    }else {
		    	ArrayList<DBObject> sonlist = new ArrayList<DBObject>();  
		    	if(m==0) {
		    		ArrayList<DBObject> fatherlist = new ArrayList<DBObject>();  
		    		BasicDBObject a = new BasicDBObject();  
		    		a.put("id", userid);  
		    		a.put("name", screen_name );  
		    		a.put("sharelist", sonlist);  
		    		fatherlist.add(a);  
		          
		    		dbCol.insert(fatherlist);  
		    		System.out.println("用户插入成功!");
		    	}else {
		    		Document doc2 = Jsoup.connect(linkurl).header("Content-type","application/json").header("Accept","application/json, text/javascript, */*; q=0.01" ).header("Accept-Encoding","gzip, deflate, br" ).header("Accept-Language","zh-CN,zh;q=0.9" ).header("cache-control","no-cache" ).header("Connection","keep-alive" ).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36").cookie("Cookie", "s=fj11iz0ydu; u=391508426962256; device_id=d142957add2ca405e7e1a09f80ba6ab5; webp=0; aliyungf_tc=AQAAAFAHQH3NwwIAZ1Gi0+fK6jn5aT2q; xq_a_token=e3cae829e5836e234be00887406080b41c2cb69a; xq_r_token=319673aba44e00bd0fed3702652be32b2349860e; __utma=1.877700155.1508426963.1508426963.1508482430.2; __utmc=1; __utmz=1.1508426963.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Hm_lvt_1db88642e346389874251b5a1eded6e3=1508426963,1508482430; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1508482677").ignoreContentType(true).get();                    
		    		JSONObject jo2 =new org.json.JSONObject(jsonString(doc2.text().toString()));
		    		JSONArray sharelist2 = new JSONArray(jsonString(jo2.get("quotes").toString()));
		    		for(int i=0;i<sharelist2.length();i++) {
		    			BasicDBObject b = new BasicDBObject(); 
		    			JSONObject temp2=sharelist2.getJSONObject(i);
		    			String symbol=temp2.getString("symbol");				    
		    			String name=temp2.getString("name");
		    			String current=temp2.getString("current");
		    			b.put("id", symbol);  
		    			b.put("name", name );  
		    			b.put("price", current);  
		    			sonlist.add(b);
				
		    		}
		    		ArrayList<DBObject> fatherlist = new ArrayList<DBObject>();  
		    		BasicDBObject a = new BasicDBObject();  
		    		a.put("id", userid);  
		    		a.put("name", screen_name );  
		    		a.put("sharelist", sonlist);  
		    		fatherlist.add(a);  
		          
		    		dbCol.insert(fatherlist);  
		    		System.out.println("用户插入成功!");
		    	}
		    	
		    	
		    }
    	}
    	                   

    }
    
    
    
    

}