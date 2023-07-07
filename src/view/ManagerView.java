package view;

import java.awt.*;
import java.awt.Taskbar.State;

import dao.*;
import database.JDBCUtil;
import java.awt.event.*;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class ManagerView extends JFrame implements ActionListener {

	private Container cont;
	private JPanel ProductView, BillView, Button, show, operate;
	private JButton Product, Bill;

	private JLabel pt, pn, pp, pc;
	private JTextField inf2, inf3, inf4,find;
	private JButton Add, Delete, Clear, Update, Check, Refresh, Exit, Filter;
	private JComboBox<String> inf1, inf5;
	private String[] type1 = new String[] { "Chip", "Mainboard", "Ram", "Rom", "GPU" };
	private String[] type2 = new String[] {"All", "Chip", "Mainboard", "Ram", "Rom", "GPU", };
	private String[] listColumn = new String[] { "P_Code", "P_Type", "P_Name", "P_Price" };
	private TableRowSorter sorter;

	private String P_Type, P_Name, P_Code;
	private int P_Price;

	private Products product;
	private ProductsDAO productOp;

	private CardLayout card;
	private JScrollPane scrollPane;
	private JTable sp;
	private DefaultTableModel model;
	private JLabel img;
	private Color lighblue = new Color(198, 245, 243);
	
	//customer order
	String[] listColumn2 = {"C_Name","Cost","Situation"};
	String[] listColumn3 = {"P_Name","Amount","Cost"};
	private JTable bill1,bill2;
	private DefaultTableModel model02,model01;
	private JButton remove,detail,confirm;
    private	static String transport;

	public ManagerView() {

		model = new DefaultTableModel();
		model.setColumnIdentifiers(listColumn);
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			String sql = "SELECT * FROM Products ORDER BY P_Type;";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				String[] value = new String[4];
				value[0] = rs.getString(1);
				value[1] = rs.getString(2);
				value[2] = rs.getString(3);
				value[3] = rs.getString(4);
				model.addRow(value);
			}

			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sorter = new TableRowSorter<>(model);
		sp = new JTable();
		sp.setModel(model);
		sp.setRowSorter(sorter);
		scrollPane = new JScrollPane(sp);
		scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		scrollPane.setBorder(new TitledBorder(new LineBorder(Color.blue), "Your Products", TitledBorder.CENTER,
				TitledBorder.TOP, null, Color.red));
		productOp = new ProductsDAO();

		cont = this.getContentPane();
		card = new CardLayout();

		show = new JPanel();
		ProductView = new JPanel();
		BillView = new JPanel();
		Button = new JPanel();
		Product = new JButton("Add Product");
		Product.setBackground(Color.gray);
		Product.setForeground(Color.white);
		Product.addActionListener(this);
		Bill = new JButton("Customer Bill");
		Bill.setBackground(Color.gray);
		Bill.setForeground(Color.white);
		Bill.addActionListener(this);
		operate = new JPanel();
		pc = new JLabel("Product Code");
		pc.setBorder(new EmptyBorder(5, 70, 5, 5));
		pt = new JLabel("Product Type");
		pt.setBorder(new EmptyBorder(5, 70, 5, 5));
		pn = new JLabel("Product Name");
		pn.setBorder(new EmptyBorder(5, 70, 5, 5));
		pp = new JLabel("Product Price");
		pp.setBorder(new EmptyBorder(5, 70, 5, 5));
		inf1 = new JComboBox<String>(type1);
		inf5 = new JComboBox<String>(type2);
		inf5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(inf5.getSelectedItem().toString().equals("All"))
					try {
						Connection con = JDBCUtil.getConnection();
						Statement st = con.createStatement();
						String sql = "SELECT * FROM Products;";
						ResultSet rs =  st.executeQuery(sql);
						model.getDataVector().removeAllElements();
						model.fireTableDataChanged();
						while(rs.next())
						{
							String[] value = new String[4];
							value[0] = rs.getString(1);
							value[1] = rs.getString(2);
							value[2] = rs.getString(3);
							value[3] = rs.getString(4);
							model.addRow(value);
						}
						JDBCUtil.closeConnection(con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else
				{
				try {
					Connection con = JDBCUtil.getConnection();
					Statement st = con.createStatement();
					String sql = "SELECT * FROM Products WHERE P_Type = '"+inf5.getSelectedItem().toString()+"';";
					ResultSet rs =  st.executeQuery(sql);
					model.getDataVector().removeAllElements();
					model.fireTableDataChanged();
					while(rs.next())
					{
						String[] value = new String[4];
						value[0] = rs.getString(1);
						value[1] = rs.getString(2);
						value[2] = rs.getString(3);
						value[3] = rs.getString(4);
						model.addRow(value);
					}
					JDBCUtil.closeConnection(con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			}
		});
		
//		Filter = new JButton("Filter");
//		Filter.addActionListener(this);
		inf2 = new JTextField();
		inf3 = new JTextField();
		inf4 = new JTextField();
		Add = new JButton("Add");
		Add.addActionListener(this);
		Delete = new JButton("Delete");
		Delete.addActionListener(this);
		Update = new JButton("Update");
		Update.addActionListener(this);
		Check = new JButton("Check(by Id)");
		Check.addActionListener(this);
		Refresh = new JButton("Refresh");
		Refresh.addActionListener(this);
		Exit = new JButton("Exit");
		Exit.setBackground(Color.gray);
		Exit.setForeground(Color.white);
		find = new JTextField("");
		find.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				search(find.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(find.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				search(find.getText());
			}
			public void search(String str) {
	            if (str.length() == 0) {
	               sorter.setRowFilter(null);
	            } else {
	            	sorter.setRowFilter(RowFilter.regexFilter(str));
	            }
	         }
		});
		Exit.addActionListener(this);
		img = new JLabel();
		img.setIcon(new ImageIcon("src/images/icon.png"));

		ProductView.add(pt);
		ProductView.add(inf1);
		ProductView.add(pc);
		ProductView.add(inf2);
		ProductView.add(pn);
		ProductView.add(inf3);
		ProductView.add(pp);
		ProductView.add(inf4);
		ProductView.add(Add);
		ProductView.add(Delete);
		ProductView.add(Update);
		ProductView.add(Check);
		ProductView.add(inf5);
//		ProductView.add(Filter);
//		ProductView.add(Refresh);
		ProductView.add(find);
		ProductView.setLayout(new GridLayout(7, 2));
		ProductView.setVisible(false);
		ProductView.setBorder(new EmptyBorder(5, 5, 5, 5));
		ProductView.setBorder(new TitledBorder(new LineBorder(Color.blue), "Add Products", TitledBorder.ABOVE_TOP,
				TitledBorder.TOP, null, Color.red));

		Button.add(img);
		Button.add(Product);
		Button.add(Bill);
		Button.add(Exit);
		Button.setBackground(lighblue);

		bill1 = new JTable();
		bill2 = new JTable();
		model01 = new DefaultTableModel();
		model01.setColumnIdentifiers(listColumn2);
		model02 = new DefaultTableModel();
		model02.setColumnIdentifiers(listColumn3);
		remove = new JButton("Remove");
		remove.addActionListener(this);
		remove.setBounds(375,10,90,25);
		detail = new JButton("Detail");
		detail.addActionListener(this);
		detail.setBounds(475,10,90,25);
		confirm = new JButton("Confirm");
		confirm.addActionListener(this);
		confirm.setBounds(575,10,90,25);
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			String sql = "SELECT * FROM bill;";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				String[] value = new String[3];
				value[0] = rs.getString(1);
				value[1] = rs.getInt(2)+"";
				value[2] = rs.getString(3);
				model01.addRow(value);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bill1.setModel(model01);
		JScrollPane scrl1 = new JScrollPane(bill1);
		scrl1.setBounds(10,10,350,100);
		bill2.setModel(model02);
		JScrollPane scrl2 = new JScrollPane(bill2);
		scrl2.setBounds(65,120,550,140);
		BillView.add(scrl1);
		BillView.add(scrl2);
		BillView.add(remove);
		BillView.add(detail);
		BillView.add(confirm);
		BillView.setLayout(null);
		
		show.setLayout(card);
		show.add(ProductView, "1");
		show.add(BillView, "2");

		cont.add(Button);
		cont.add(show);
		cont.add(scrollPane);
		cont.setLayout(new GridLayout(3, 1));

		this.setVisible(true);
		this.setTitle("Manager");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(500, 100);
		this.setSize(700,600);
		ImageIcon img2 = new ImageIcon("src/images/Manage.png");
		this.setIconImage(img2.getImage());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if (str.equals("Add Product")) {
			card.show(show, "1");
			Bill.setBackground(Color.gray);
			Product.setBackground(Color.black);
			ProductView.setVisible(true);
			scrollPane.setVisible(true);
		} else if (str.equals("Customer Bill")) {
			Bill.setBackground(Color.black);
			Product.setBackground(Color.gray);
			card.show(show, "2");
			ProductView.setVisible(false);
			scrollPane.setVisible(false);
		} else if (str.equals("Add")) {
			product = new Products();
			this.product.setP_code(inf2.getText());
			this.product.setP_type(inf1.getSelectedItem().toString());
			this.product.setP_name(inf3.getText());
			this.product.setP_Price(Integer.parseInt(inf4.getText()));
			product = new Products(this.product.getP_type(), this.product.getP_code(), this.product.getP_name(),
					this.product.getP_Price());
			this.productOp.insert(product);
			if (Integer.parseInt(inf2.getText()) < 9) {
				inf2.setText("0" + (Integer.parseInt(inf2.getText()) + 1) + "");
			} else
				inf2.setText(Integer.parseInt(inf2.getText()) + 1 + "");
			try {
				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();
				String sql = "SELECT * FROM Products;";
				ResultSet rs = st.executeQuery(sql);
				model.getDataVector().removeAllElements();
				model.fireTableDataChanged();
				while (rs.next()) {
					String[] value = new String[4];
					value[0] = rs.getString(1);
					value[1] = rs.getString(2);
					value[2] = rs.getString(3);
					value[3] = rs.getString(4);
					model.addRow(value);
				}

				JDBCUtil.closeConnection(con);
			} catch (SQLException f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
			}
		} else if (str.equals("Delete")) {
			this.productOp.delete(inf2.getText());
			try {
				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();
				String sql = "SELECT * FROM Products;";
				ResultSet rs = st.executeQuery(sql);
				model.getDataVector().removeAllElements();
				model.fireTableDataChanged();
				while (rs.next()) {
					String[] value = new String[4];
					value[0] = rs.getString(1);
					value[1] = rs.getString(2);
					value[2] = rs.getString(3);
					value[3] = rs.getString(4);
					model.addRow(value);
				}

				JDBCUtil.closeConnection(con);
			} catch (SQLException f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
			}
		} else if (str.equals("Check(by Id)")) {
			product = new Products(inf1.getSelectedItem().toString(), inf2.getText(), null, 0);
			this.productOp.selectById(product);
			if (this.product.getP_name() == null) {
				JOptionPane.showMessageDialog(this, "No Exist", "Warning", JOptionPane.INFORMATION_MESSAGE);
				inf2.setText("");
				inf3.setText("");
				inf4.setText("");
			} else {
				JOptionPane.showMessageDialog(this, "Exist", "About", JOptionPane.INFORMATION_MESSAGE);
				inf3.setText(this.product.getP_name());
				inf4.setText(this.product.getP_Price() + "");
			}
		} else if (str.equals("Refresh")) {
			try {
				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();
				String sql = "SELECT * FROM Products;";
				ResultSet rs = st.executeQuery(sql);
				model.getDataVector().removeAllElements();
				model.fireTableDataChanged();
				while (rs.next()) {
					String[] value = new String[4];
					value[0] = rs.getString(1);
					value[1] = rs.getString(2);
					value[2] = rs.getString(3);
					value[3] = rs.getString(4);
					model.addRow(value);
				}

				JDBCUtil.closeConnection(con);
			} catch (SQLException f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
			}
		} else if (str.equals("Exit")) {
			Exit.setBackground(Color.black);
			this.dispose();
			new loginView();
		}
		else if(str.equals("Accounts Manager"))
		{
			
		}
		else if(str.equals("Update"))
		{
			try {
				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();
				String sql = "DELETE FROM Products WHERE P_Code = '"+ inf2.getText()+"';";
				st.executeUpdate(sql);
				String sql1 = "INSERT INTO Products VALUES('"+inf2.getText()+"','"+inf1.getSelectedItem().toString()+"','"+inf3.getText()+"',"+Integer.parseInt(inf4.getText())+");";
				st.executeUpdate(sql1);
					String sql2 = "SELECT * FROM Products;";
					ResultSet rs = st.executeQuery(sql2);
					model.getDataVector().removeAllElements();
					model.fireTableDataChanged();
					while (rs.next()) {
						String[] value = new String[4];
						value[0] = rs.getString(1);
						value[1] = rs.getString(2);
						value[2] = rs.getString(3);
						value[3] = rs.getInt(4)+"";
						model.addRow(value);
					}
					model.fireTableDataChanged();
					JDBCUtil.closeConnection(con);
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(str.equals("Detail"))
		{
			model02.getDataVector().removeAllElements();
			model02.fireTableDataChanged();
			int row = bill1.getSelectedRow();
			try {
				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();
				String sql = "SELECT * FROM "+model01.getValueAt(row, 0).toString()+";";
				ResultSet rs = st.executeQuery(sql);
				while(rs.next())
				{
					String value[] = new String[3];
					value[0] = rs.getString("P_name");
					value[1] = rs.getInt("Amount")+"";
					value[2] = rs.getInt("Cost")+"";
					model02.addRow(value);
				}
				model02.fireTableDataChanged();
				JDBCUtil.closeConnection(con);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(str.equals("Remove"))
		{
			if(bill1.getSelectedRow()>-1)
			{
			try {
				int row = bill1.getSelectedRow();
				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();
				String sql = "DELETE FROM Bill WHERE C_name = '" +bill1.getModel().getValueAt(row, 0).toString()+"';";
				st.executeUpdate(sql);
				model01.getDataVector().removeAllElements();
				model01.fireTableDataChanged();
				sql = "SELECT Count(*) as sl FROM Bill;";
				ResultSet rs =  st.executeQuery(sql);
				rs.next();
				if(rs.getInt(1) > 0)
				{
					sql = "SELECT * FROM Bill;";
					rs = st.executeQuery(sql);
					while(rs.next())
					{
						String value[] = new String[3];
						value[0] = rs.getString(1);
						value[1] = rs.getInt(2)+"";
						value[2] = rs.getString(3);
						model01.addRow(value);
					}
					model01.fireTableDataChanged();
				}
				
				else
				{
					
				}
				JDBCUtil.closeConnection(con);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			else
			{
				
			}
		}
		else if(str.equals("Confirm"))
		{
			model02.getDataVector().removeAllElements();
			model02.fireTableDataChanged();
			int row = bill1.getSelectedRow();
			transport = model01.getValueAt(row, 0).toString();
			new receipt();
			try {
				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();
				String sql = "UPDATE Bill SET Situation = 'Done'WHERE C_name='"+model01.getValueAt(row, 0).toString()+"';";
				st.executeUpdate(sql);
				sql = "DELETE FROM Bill WHERE C_name = '"+model01.getValueAt(row, 0).toString()+"';";
				st.executeUpdate(sql);
				model01.getDataVector().removeAllElements();
				model01.fireTableDataChanged();
				sql = "SELECT * FROM Bill;";
				ResultSet rs =  st.executeQuery(sql);
				while(rs.next())
				{
					String value[] = new String[3];
					value[0] = rs.getString(1);
					value[1] = rs.getInt(2)+"";
					value[2] = rs.getString(3);
					model01.addRow(value);
				}
				model01.fireTableDataChanged();
				JDBCUtil.closeConnection(con);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	

	public static String getTransport() {
		return transport;
	}

	public static void setTransport(String transport) {
		ManagerView.transport = transport;
	}

	public static void main(String[] args) {
		new ManagerView();
	}
}
