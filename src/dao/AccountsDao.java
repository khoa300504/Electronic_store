package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.JDBCUtil;
import model.*;

public class AccountsDao implements DAOInterface<Accounts>{

	public static Accounts getAccounts()
	{
		return new Accounts();
	}
	
	@Override
	public int insert(Accounts t) {
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "INSERT INTO accounts values ('"+t.getUsername()+"','"+t.getPassword()+"','"+t.getName()+"','customer','"+t.getAddress()+"');";
			
			st.executeUpdate(sql);
			
			System.out.println("A row has changed");
			
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(Accounts t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Accounts t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Accounts> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Accounts selectById(Accounts t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Accounts> selectByCondition(String condition) {
//		Connection con = JDBCUtil.getConnection();
//		
//		Statement st = con.createStatement();
//		
//		String sql = "SELECT "
		return null;
	}
}
