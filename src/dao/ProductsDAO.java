package dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.mysql.cj.xdevapi.PreparableStatement;

import database.JDBCUtil;

public class ProductsDAO implements DAOInterface<Products>{

	public static Products getProducts()
	{
		return new Products();
	}
	
	@Override
	public int insert(Products t) {
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "INSERT INTO Products VALUES ('"+t.getP_code()+"','"+t.getP_type()+"','"+t.getP_name()+"',"+t.getP_Price()+");";
			
			int check = st.executeUpdate(sql);
			
			
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return 0;
	}

	public void delete(String id) {
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "DELETE FROM PRODUCTS WHERE P_code = '"+id+"';";
			
			int check = st.executeUpdate(sql);
			
			System.out.println("Co "+check+" dong bi thay doi");
			
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int update(Products t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Products> selectAll() {

		try {
			Connection con = JDBCUtil.getConnection();
			
			String sql = "SELECT * FROM Products";
			
			ArrayList<Products> list = new ArrayList<>();
			
			PreparedStatement ps = con.prepareCall(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				Products good = new Products();
				good.setP_code(rs.getString("P_code"));
				good.setP_type(rs.getString("P_type"));
				good.setP_name(rs.getString("P_name"));
				good.setP_Price(rs.getInt("P_price"));
				
				list.add(good);
			}
			
			ps.close();
			rs.close();
			JDBCUtil.closeConnection(con);
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Products selectById(Products t) {
		try {
			Connection con = JDBCUtil.getConnection();
			
			Statement st = con.createStatement();
			
			String sql = "SELECT * FROM Products WHERE P_code = '"+t.getP_code()+"' AND P_type = '"+t.getP_type()+"';";
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next())
			{
				t.setP_code(rs.getString("P_Code"));
				t.setP_type(rs.getString("P_Type"));
				t.setP_name(rs.getString("P_Name"));
				t.setP_Price(rs.getInt("P_price"));
			}
			
			JDBCUtil.closeConnection(con);
			
			return t;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ArrayList<Products> selectByCondition(String condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Products t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static void main(String[] args) {
		ProductsDAO a = new ProductsDAO();
		System.out.println(a.selectAll());
	}
}
