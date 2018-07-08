package sql;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Tests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList list;
		DBConnection db=new DBConnection();
		
		try {
			String sql="select * from commodity_bank where num='1'";
			list=db.select(sql);
			System.out.print(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
