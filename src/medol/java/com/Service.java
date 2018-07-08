package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import modal.Medalmap;
import modal.ReSult;
import modal.ReSult1;

import sql.DBConnection;






@Path("/service")
public class Service {
	
	@POST
	@Path("/html")
//	@Consumes(MediaType. APPLICATION_JSON)
//	@Produces(MediaType. TEXT_HTML)
	public  String excel(@FormParam("data")String data){
		Map map =  (Map) JSONObject.parse(data);
		return data;

		
	
	}
	
	/*
	 * -----------------------------登录---------------------
	 * */
	
	@POST
	@Path("/login")
	public String login(@FormParam("data")String data) {
		// TODO Auto-generated method stub
		Map maps =  (Map) JSONObject.parse(data);
		String user=(String) maps.get("user");
		String psw=(String) maps.get("psw");
		ReSult1 result=new ReSult1();
		ArrayList<Map<String, Object>> map=new ArrayList<Map<String,Object>>();
		if(user==null){
			result.setMessage("未输入用户名 ");
			result.setSuccess("false");
		}
		if(psw==null){
			result.setMessage("未输入密码 ");
			result.setSuccess("false");
		}
		DBConnection con=new DBConnection();
		String sql="select userid,username,password,phone,role,qq,name,gender from user where username='"+user+"'and password='"+psw+"'";
//		String sql="select * from user where username='"+user+"'and password='"+psw+"'";
		try {
			map=con.getDBData(sql);
			if(map!=null&& !map.equals("")){
				result.setMessage("登录成功");
				result.setSuccess("true");
				result.setArraylist(map);
				return toJSONString(result);
			}else{
				result.setMessage("登录失败:用户不存在或用户名错误或密码错误");
				result.setSuccess("false");
				return toJSONString(result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return toJSONString(result);
	}
	/*
	 * ----------------------注册------------------
	 * */
	
	@POST
	@Path("/regist")
	public String regiet(@FormParam("data")String data){
		ArrayList<Map<String, Object>> map=new ArrayList<Map<String,Object>>();
		ReSult result=new ReSult();
        Map maps =  (Map) JSONObject.parse(data);
		String tablenames=(String) maps.get("tablename");
		String phones=(String) maps.get("phone");
		String username=(String) maps.get("username");
		String words=(String) maps.get("word");
		String values=(String) maps.get("value");
		DBConnection con=new DBConnection();
		String sql="select username,password,phone from user where username='"+username+"'or phone='"+phones+"'";
		String sqL="insert into "+tablenames+" " +words+" values "+ values;
		
		try {
			map=con.getDBData(sql);
			if(map!=null&&map.equals("")){
				result.setMessage("用户名或手机号存在");
				result.setSuccess("false");
				return toJSONString(result);
			}else{
				boolean aa=con.updata(sqL);
				if(aa){
					result.setMessage("成功");
					result.setSuccess("true");
					return toJSONString(result);
				}else{
					result.setMessage("失败");
					result.setSuccess("false");
					return toJSONString(result);
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return toJSONString(result);
		
	}
	
	/*
	 * ********************查询*************************
	 * */
	
	@POST
	@Path("/select")
	public String select(@FormParam("data")String data) {
		// TODO Auto-generated method stub
		Map maps =  (Map) JSONObject.parse(data);
		String tablenames=(String) maps.get("tablename");
		String words=(String) maps.get("word");
		String wheres=(String) maps.get("where");
		String sql="select "+words+" from "+tablenames + " where "+wheres;
		DBConnection con=new DBConnection();
		ReSult1 result=new ReSult1();
		ArrayList<Map<String, Object>> datas=new ArrayList<Map<String,Object>>();
		try {
			datas=con.getDBData(sql);
			if(datas.equals("") && datas==null){
				result.setMessage("失败");
				result.setSuccess("false");
				
			}else{
				result.setMessage("成功");
				result.setSuccess("true");
				result.setArraylist(datas);
			}
			return toJSONString(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return toJSONString(result);
	}
	
	/*
	 * ************************跟新**************************
	 * */
	
	@POST
	@Path("/updata")
	public String updata(@FormParam("data")String data) {
		ReSult result=new ReSult();
		Map maps =  (Map) JSONObject.parse(data);
		String tablenames=(String) maps.get("tablename");
		String sets=(String) maps.get("set");
		 String wheres=(String) maps.get("where");
		DBConnection con=new DBConnection();
		
		String sql="update  "+ tablenames+" set "+sets+" where "+wheres;
		try {
			 boolean res=con.updata(sql);
			 if(res){
				result.setMessage("添加成功");
				result.setSuccess("true");
				return toJSONString(result);
			 }else{
				result.setMessage("失败");
				result.setSuccess("false");
				return toJSONString(result);
			 }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("异常失败");
			result.setSuccess("false");
			
		}
		return toJSONString(result);
	}


/* 
 ****************新增*****************
 **/

	@POST
	@Path("/insert")
	public String insert(@FormParam("data")String data

			) {
		// TODO Auto-generated method stub
		ArrayList<Map<String, Object>> map=new ArrayList<Map<String,Object>>();
		Map maps =  (Map) JSONObject.parse(data);
		ReSult result=new ReSult();
		String oneword=(String) maps.get("oneword");
		String secondword=(String) maps.get("secondword");
		String tablenames=(String) maps.get("tablename");
		String words=(String) maps.get("word");
		 String values=(String) maps.get("value");
		DBConnection con=new DBConnection();
		String sQl="select shopid,shopdescribe,userid,type,produceplace from "+ tablenames +" where "+" shopname = '"+oneword+"'and username = '"+secondword+"'";
		String sql="insert into "+tablenames+" " +words+" values "+ values;
		
		
		try {
			if(!oneword.equals("")&& !secondword.equals("")){
			map=con.getDBData(sQl);}
			if(map.equals("")&&map==null){
				result.setMessage("失败");
				result.setSuccess("false");
				return toJSONString(result);
			}
			else{
			 boolean res=con.updata(sql);
			 if(res){
				result.setMessage("添加成功");
				result.setSuccess("true");
				return toJSONString(result);
			 }else{
				result.setMessage("失败");
				result.setSuccess("false");
				return toJSONString(result);
			 }
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("异常失败");
			result.setSuccess("false");
			
		}
		return toJSONString(result);
		
	}


/*
 *---------------------删除---------------------
 **/

	@POST
	@Path("/delete")
	public String delete(@FormParam("data")String data) {
		// TODO Auto-generated method stub
		ReSult result=new ReSult();
		Map maps =  (Map) JSONObject.parse(data);
		String tablenames=(String) maps.get("tablename");
		
		 String wheres=(String) maps.get("where");
		DBConnection con=new DBConnection();
		boolean res=false;
		String sql="delete from "+ tablenames +" where "+ wheres;
		try {
			 res=con.delete(sql);
			 result.setMessage("成功");
			 result.setSuccess("true");
			return toJSONString(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("失败");
			 result.setSuccess("false");
		}
		return toJSONString(result);
		
	}
	
	
	@POST
	@Path("/select1")
	public String select1(@FormParam("data")String data){
		Map maps =  (Map) JSONObject.parse(data);
		String tablenames=(String) maps.get("tablename");
		String words=(String) maps.get("word");
		String wheres=(String) maps.get("where");
		String sql="select * from "+tablenames + ((null!=wheres&&wheres.length()>0)? " where "+wheres :"");
//		String sql="select * from commodity_bank" + ((null!=wheres&&wheres.length()>0)? " where "+wheres :"");
		ArrayList list = null;
		Medalmap medalmap=new Medalmap();
		ReSult1 result=new ReSult1();
		ArrayList<Map<String,Object>> lists = null;
		DBConnection db=new DBConnection();
		
		try {
			
			list=db.select(sql);
			System.out.print(list);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(tablenames.equals("commodity_bank")){
			lists=medalmap.imagemap(list);
		}else if(tablenames.equals("shopcar")){
			lists=medalmap.shopcarmap(list);
		}else if(tablenames.equals("orderform")){
			lists=medalmap.orderformmap(list);
		}
		if(lists.equals("") && lists==null){
			result.setMessage("失败");
			result.setSuccess("false");
			
		}else{
			result.setMessage("成功");
			result.setSuccess("true");
			result.setArraylist(lists);
		}
		return JSON.toJSONString(result);
		
		
	}
	
	
	@POST
	@Path("/insert1")
	public String insertorderfrom(@FormParam("data")String data){
		DBConnection con=new DBConnection();
		ReSult result=new ReSult();
		ArrayList<Map<String,Object>> lists = null;
		ArrayList<Map<String,Object>> list = null;
		ArrayList<Map<String,Object>> list1 = null;
		Map maps =  (Map) JSONObject.parse(data);
		String sellerid=(String) maps.get("userid");
		String clientuserid=(String) maps.get("clientid");
		String tablenames=(String) maps.get("tablename");
		String words=(String) maps.get("word");
		String values=(String) maps.get("value");
		String price=(String) maps.get("price");
		float priceN=Float.parseFloat(price);
		String sql1="select adress from adress where userid='"+clientuserid+"'";
		String sql2="select money from money where userid='"+sellerid+"'";
		String sql3="select money from money where userid='"+clientuserid+"'";
		try{
		   lists=con.getDBData(sql1);
		   if(lists.size()==0){
			   result.setMessage("请先补全地址");
			   result.setSuccess("false");
		   }else{
			  String adress=(String) lists.get(0).get("adress");
			  list=con.getDBData(sql2);
			  String money=(String) list.get(0).get("money");
			  list1=con.getDBData(sql3);
			  String money1=(String) list1.get(0).get("money");
			  float prices=Float.parseFloat(money);
			  float priceC=Float.parseFloat(money1);
			  if(priceC<priceN){
				  result.setMessage("余额不足");
				  result.setSuccess("false");
			  }
			  else{
				  float prices1=prices+priceN;
				  float priceC1=priceC-priceN;
				  String moneys = String.valueOf(prices1);
				  String moneyC = String.valueOf(priceC1);
				  Date date=new Date();
					SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dates=simpleDateFormat.format(date);
				  String sqls="update money set money='"+moneys+"' where userid='"+sellerid+"'";
				  String sqls1="update money set money='"+moneyC+"' where userid='"+clientuserid+"'";
				  con.updata(sqls);
				  con.updata(sqls1);
				  String sqL="insert into "+tablenames+"( " +words+",adressdetail,startdatetime) values ("+ values+",'"+adress+"','"+dates+"')";
				  con.updata(sqL);
				  result.setMessage("购买成功");
				  result.setSuccess("true");
			  }
			  return JSON.toJSONString(result); 
		   }
		   return JSON.toJSONString(result);  
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return JSON.toJSONString(result);
		
		
	}
	
	public static String toJSONString(Object o){
		return JSONObject.toJSONString(o,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
	}
	

}
