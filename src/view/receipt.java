package view;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.JDBCUtil;

public class receipt extends JFrame{
	private JLabel shopname,title;
	private JTable products;
	private DefaultTableModel model;
	private JLabel sign;
	private String[] listcolumns = {"P_Name","Amount","Cost"};
	
	public receipt() {
		shopname = new JLabel("ComputerComponents Shop");
		shopname.setFont(new Font("Serif", Font.ITALIC, 24));
		shopname.setForeground(Color.red);
		title = new JLabel("Sales Receipt");
		title.setFont(new Font("Tahoma", Font.PLAIN, 16));
		title.setForeground(Color.red);
		products = new JTable();
		model = new DefaultTableModel();
		model.setColumnIdentifiers(listcolumns);
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			String sql = "SELECT * FROM "+ ManagerView.getTransport()+";";
			ResultSet rs =  st.executeQuery(sql);
			while(rs.next())
			{
				String value[] = new String[3];
				value[0] = rs.getString(1);
				value[1] = rs.getInt(2)+"";
				value[2] = rs.getInt(3)+"";
				model.addRow(value);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.fireTableDataChanged();
		products.setModel(model);
		JScrollPane scrl = new JScrollPane(products);
		sign = new JLabel("Your sign");
		shopname.setBounds(160,20,350,30);
		title.setBounds(240,80,200,30);
		scrl.setBounds(40,140,500,300);
		sign.setBounds(440,470,100,20);
		this.add(shopname);
		this.add(title);
		this.add(scrl);
		this.add(sign);
		this.setLayout(null);
		this.setLocation(500, 100);
		this.setSize(600,600);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new receipt();
	}
}
