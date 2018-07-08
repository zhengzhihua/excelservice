package modal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Medalmap {
	
	public  ArrayList<Map<String,Object>> imagemap(ArrayList list){
	//	ArrayList<Map<String,Object>> lists=new ArrayList();
		ArrayList<Map<String, Object>> lists=new ArrayList<Map<String,Object>>();
		
		for(int j=0;j<list.size();j++){
			Object[] object=(Object[]) list.get(j);
			int num=object.length;
			 Map<String,Object> map =new HashMap<String, Object>();
		   for(int i=0;i<num;i++){
			  
			System.out.print(object[0]);
			map.put("shopid",object[0]);
			map.put("shopname", object[1]);
			map.put("shopdescribe", object[2]);
			map.put("username", object[3]);
			map.put("userid", object[4]);
			map.put("price", object[5]);
			map.put("type", object[6]);
			map.put("produceplace", object[7]);
			map.put("image", object[8]);
			map.put("uploadtime", object[9]);
			map.put("primaryprice", object[10]);
		}
			lists.add(map);
		}
		
		return lists;
		
	}
	public  ArrayList<Map<String,Object>> shopcarmap(ArrayList list){
		ArrayList<Map<String,Object>> lists=new ArrayList<Map<String, Object>>();
		
		for(int j=0;j<list.size();j++){
			Object[] object=(Object[]) list.get(j);
			int num=object.length;
			 Map<String,Object> map =new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			
			System.out.print(object[0]);
			map.put("shopcarid",object[0]);
			map.put("shopid", object[1]);
			map.put("shopname", object[2]);
			map.put("shopdescribe", object[3]);
			map.put("username", object[4]);
			map.put("userid", object[5]);
			map.put("price", object[6]);
			map.put("type", object[7]);
			map.put("produceplace", object[8]);
			map.put("image", object[9]);
			map.put("usernames", object[10]);
			map.put("userids", object[11]);
			map.put("num", object[12]);
			map.put("primaryprice", object[13]);
		}
			lists.add(map);
		}
		return lists;
		
	}
	public  ArrayList<Map<String,Object>> orderformmap(ArrayList list){
		ArrayList<Map<String,Object>> lists=new ArrayList<Map<String, Object>>();
		for(int j=0;j<list.size();j++){
			Object[] object=(Object[]) list.get(j);
			int num=object.length;
			 Map<String,Object> map =new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			
			System.out.print(object[0]);
			map.put("orderformid",object[0]);
			map.put("username", object[1]);
			map.put("shopname", object[2]);
			map.put("shopid", object[3]);
			map.put("userid", object[4]);
			map.put("status", object[5]);
			map.put("image", object[6]);
			map.put("price", object[7]);
			map.put("clientname", object[8]);
			map.put("clientid", object[9]);
			map.put("number", object[10]);
			map.put("adressdetail", object[11]);
			map.put("startdatetime", object[12]);
			map.put("finishtime", object[13]);
			map.put("totalmoney", object[14]);
		}
			lists.add(map);
		}
		return lists;
		
	}

}
