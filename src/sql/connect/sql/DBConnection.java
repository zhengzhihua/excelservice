package sql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBConnection {
	
	//日期格式
		public SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式

	public Connection getConnection() throws Exception {

		InputStream is = this.getClass().getClassLoader().getResourceAsStream("com/db/file/db.properties");
		Properties property = new Properties();
		property.load(is);
		String driverClassName = property.getProperty("jdbc.driverClassName");
		String url = property.getProperty("jdbc.url");
		String username = property.getProperty("jdbc.username");
		String password = property.getProperty("jdbc.password");
		Connection conn = null;
		try {
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public boolean updata(String sqlstr) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn=getConnection();
			ps = conn.prepareStatement(sqlstr);
			ps.execute(sqlstr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			close(conn,ps,rs);
		}
		return true;
	}
	
	
	public ArrayList<Map<String, Object>> getDBData(String sqlstr){
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		String sqlStrTemp=sqlstr.toLowerCase();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		    conn=getConnection();
			ps = conn.prepareStatement(sqlstr);
			rs = ps.executeQuery(sqlstr);
			HashMap<String, String> metaData=getRsMetaData(rs);
			sqlstr= sqlstr.substring(7, sqlStrTemp.indexOf(" from "));
			String[] cols=sqlstr.split(",");
			while(rs.next()) {  
				HashMap<String,Object> row = new HashMap<String,Object>();
				for(int i=0;i<cols.length;i++){
					String colname="";
					if(cols[i].contains(" as "))
					{
						colname=(cols[i].substring(cols[i].indexOf(" as ")+4)).trim();
					}
					else
					{
						colname=cols[i].contains(".")?cols[i].substring(cols[i].indexOf(".")+1):cols[i];
					}
					colname=colname.trim();
					String curValue=getValueFromRs(rs, colname, metaData.get(colname.toUpperCase()));
					row.put(colname, curValue);
				}
				data.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close(conn,ps,rs);
		}
		return data;
	}
	
	public boolean delete(String sql) throws Exception{
		Connection conn = null;
		Statement ps = null;
		ResultSet rs = null;
		String sqlStrTemp=sql.toLowerCase();
		try {
			conn=getConnection();
			ps = conn.prepareStatement(sql);
			ps.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			close(conn,ps,rs);
		}
		return true;
	}
	
	public ArrayList select(String sql){
		ArrayList al=new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn=getConnection();
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery(sql);
			ResultSetMetaData rsmd=rs.getMetaData();
			//通过 rsmd 可以得到该结果集有多少列
			int columnNum=rsmd.getColumnCount();
			//循环的从al中取出数据，并封装到ArrayList中
			while(rs.next()){
				String[] objects=new String[columnNum];				
				for(int i=0;i<objects.length;i++){
					objects[i]=rs.getString(i+1);
				}
				al.add(objects);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
		
	}
		
	
//获取查询数据的数据类型
	public HashMap<String,String> getRsMetaData(ResultSet rs) throws SQLException
	{
		ResultSetMetaData metaData=rs.getMetaData();
		HashMap<String,String> hs=new HashMap<String,String>();
		int columnsLen=metaData.getColumnCount();
		for(int i=1;i<columnsLen;i++)
		{
			hs.put(metaData.getColumnName(i), metaData.getColumnTypeName(i));
		}
		//关闭rs连接
		return hs;
	}
	
	
	//关闭连接
	public void close(final Connection connection, final Statement stat, final ResultSet rs) {
		try {
			if (connection != null && !connection.isClosed()) {
					connection.close();
			}
			if (stat != null && !stat.isClosed()) {
				stat.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//根据特殊数据类型，获取值
	public String getValueFromRs(ResultSet rs,String columnName,String maxtype) throws SQLException
	{
		String value=rs.getString(columnName);
		if(value!=null&&!"".equals(value))
		{
			if(("DATE".equals(maxtype)||"DATETIME".equals(maxtype)))
			{
				//为Date或Datetime  并且不为空
				if("DATE".equalsIgnoreCase(maxtype))
				{
					//日期类型
					return dateformat.format(rs.getDate(columnName));
				}
				else if("DATETIME".equalsIgnoreCase(maxtype))
				{
					//日期时间类型
					String date=rs.getDate(columnName)+"";
					String time=rs.getTime(columnName)+"";
					return date+" "+time;
				}
			}
			else if(("NUMBER".equalsIgnoreCase(maxtype)||"FLOAT".equalsIgnoreCase(maxtype)||"DECIMAL".equals(maxtype)||"AMOUNT".equals(maxtype)||maxtype==null)&&(value.startsWith(".")))
			{
				return Float.parseFloat(value)+"";
			}
			else{
				//其他类型
				return value;
			}
		}
        return null;
	}
	
	
}
