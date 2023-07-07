package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order.Direction;
import com.mysql.cj.xdevapi.Result;

import java.awt.*;
import java.awt.Taskbar.State;
import java.awt.event.*;
import java.lang.ProcessHandle.Info;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.*;
import dao.*;
import database.JDBCUtil;
import view.*;

public class PurchaseView extends JFrame implements ActionListener {

	private JPanel Stall, OrderView, InfomationView, show, button, user;
	private JLabel greeting;
	private JButton Buy, MyOrder, CInfo, Exit;
	private CardLayout card;

	// Stall component
	private JPanel tb, buy;
	private JTable sp;
	private DefaultTableModel model;
	private String[] listColumn = new String[] { "P_Code", "P_Type", "P_Name", "P_Price" };
	private String[] type2 = new String[] { "Chip", "Mainboard", "Ram", "Rom", "GPU" };
	private JComboBox inf5;
	private JScrollPane scrl;
	private JLabel code, type, name, price, amount;
	private JTextField t1, t2, t3, t4, t5;
	private JButton AddTC;

	// My order component

	// User Information
	private JLabel C_Username, C_Name, C_Address, C_OPassWord, C_NPassWord;
	private JTextField inf1, inf2, inf3, inf4;
	private JButton Update, Change;

	// cart
	private JPanel Tcart;
	private JTable tcart;
	private DefaultTableModel model2;
	private String[] ColumnList2 = { "P_Type", "P_Name", "Amount", "Cost" };
	private JButton confirm, x, clear, move;
	private JScrollPane scrl1;

	// my order
	private JPanel myorder, ttorder;
	private JTable cOrder;
	private DefaultTableModel model3;
	private JLabel tt;
	private JButton pay, x2, clear2, refresh;
	private JTextField pcost;
	private boolean situation = true;

	public PurchaseView() {

		// ----------------------
		OrderView = new JPanel();
		InfomationView = new JPanel();
		CInfo = new JButton("My Information");
		CInfo.setBackground(new Color(122,225,246));
		CInfo.addActionListener(this);
		button = new JPanel();
		Exit = new JButton("Exit");
		Exit.setBackground(new Color(122,225,246));
		user = new JPanel();
		Exit.addActionListener(this);

		// Stall
		move = new JButton("->");
		move.addActionListener(this);
		Stall = new JPanel();
		buy = new JPanel();
		model = new DefaultTableModel();
		model.setColumnIdentifiers(listColumn);

		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			String sql = "SELECT * FROM Products;";
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

		sp = new JTable();
		sp.setModel(model);
		scrl = new JScrollPane();
		scrl.setBounds(0, 0, 255, 150);
		move.setBounds(60, 155, 115, 20);
		scrl.getViewport().add(sp);
		scrl.setBorder(new TitledBorder(new LineBorder(Color.black), "List Products", TitledBorder.CENTER,
				TitledBorder.TOP, null, Color.red));
		JPanel box = new JPanel();
		box.add(scrl);
		box.add(move);
		box.setLayout(null);

		code = new JLabel("P_Code");
		type = new JLabel("P_Type");
		name = new JLabel("P_Name");
		price = new JLabel("P_Price");
		amount = new JLabel("Amount");
		t2 = new JTextField("Null");
		t2.setEditable(false);
		t3 = new JTextField("Null");
		t3.setEditable(false);
		t4 = new JTextField("Null");
		t4.setEditable(false);
		t1 = new JTextField("");
		t1.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				for (int i = 0; i < sp.getModel().getRowCount(); i++) {
					if (t1.getText().equals((String) sp.getModel().getValueAt(i, 0))) {
						t2.setText((String) sp.getModel().getValueAt(i, 1));
						t3.setText((String) sp.getModel().getValueAt(i, 2));
						t4.setText((String) sp.getModel().getValueAt(i, 3));
						break;
					} else {
						t2.setText("Null");
						t3.setText("Null");
						t4.setText("Null");
					}
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				for (int i = 0; i < sp.getModel().getRowCount(); i++) {
					if (t1.getText().equals((String) sp.getModel().getValueAt(i, 0))) {
						t2.setText((String) sp.getModel().getValueAt(i, 1));
						t3.setText((String) sp.getModel().getValueAt(i, 2));
						t4.setText((String) sp.getModel().getValueAt(i, 3));
						break;
					} else {
						t2.setText("Null");
						t3.setText("Null");
						t4.setText("Null");
					}
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				for (int i = 0; i < sp.getModel().getRowCount(); i++) {
					if (t1.getText().equals((String) sp.getModel().getValueAt(i, 0))) {
						t2.setText((String) sp.getModel().getValueAt(i, 1));
						t3.setText((String) sp.getModel().getValueAt(i, 2));
						t4.setText((String) sp.getModel().getValueAt(i, 3));
						break;
					} else {
						t2.setText("Null");
						t3.setText("Null");
						t4.setText("Null");
					}
				}
			}
		});
		t5 = new JTextField("1");
//		check = new JButton("Check");
//		check.addActionListener(this);
		AddTC = new JButton("Add to your cart");
		AddTC.addActionListener(this);
		inf5 = new JComboBox<String>(type2);
//		Filter = new JButton("Filter");
//		Filter.addActionListener(this);

		buy.add(code);
		buy.add(t1);
		buy.add(type);
		buy.add(t2);
		buy.add(name);
		buy.add(t3);
		buy.add(price);
		buy.add(t4);
		buy.add(amount);
		buy.add(t5);
//		buy.add(check);
		buy.add(inf5);
		buy.add(AddTC);
//		buy.add(Filter);
		buy.setLayout(new GridLayout(6, 2));
		buy.setBorder(new EmptyBorder(5, 5, 5, 5));
		buy.setBorder(new TitledBorder(new LineBorder(Color.black), "Detail Product", TitledBorder.CENTER,
				TitledBorder.TOP, null, Color.red));

		// cart
		Tcart = new JPanel();
		JPanel tp1 = new JPanel();
		JPanel tp2 = new JPanel();
		model2 = new DefaultTableModel();
		model2.setColumnIdentifiers(ColumnList2);
		tcart = new JTable();
		tcart.setModel(model2);
		scrl1 = new JScrollPane(tcart);
		scrl1.setBorder(new TitledBorder(new LineBorder(Color.black), "Your Temp Cart", TitledBorder.CENTER,
				TitledBorder.TOP, null, Color.red));
		tp1.add(scrl1);
		x = new JButton("Remove");
		x.addActionListener(this);
		x.setBounds(30, 30, 100, 30);
		clear = new JButton("Clear");
		clear.addActionListener(this);
		clear.setBounds(140, 30, 100, 30);
		confirm = new JButton("Confirm");
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			String sql = "SELECT Count(Situation) as sl FROM BILL WHERE C_Name = '"+loginView.getFname()+"';";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			if(rs.getInt("sl") > 0)
			{
				sql = "SELECT Situation FROM BILL WHERE C_Name = '"+loginView.getFname()+"';";
				ResultSet rs1 = st.executeQuery(sql);
				rs1.next();
			if(rs1.getString("Situation").equals("Waiting"))
			{
				confirm.setEnabled(false);
			}
			}
			else
			{
				
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		confirm.addActionListener(this);
		confirm.setBounds(30, 110, 210, 50);
		tp2.add(confirm);
		tp2.add(x);
		tp2.add(clear);
		tp2.setLayout(null);
		Tcart.add(tp1);
		Tcart.add(tp2);
		Tcart.setLayout(new GridLayout(2, 1));

		Stall.add(box);
		Stall.add(scrl1);
		Stall.add(buy);
		Stall.add(tp2);
		Stall.setLayout(new GridLayout(2, 2));

		// my order
		model3 = new DefaultTableModel();
		String[] listcolumn = { "P_Name", "Amount", "Cost" };
		model3.setColumnIdentifiers(listcolumn);
		myorder = new JPanel();
		ttorder = new JPanel();
		int sum1 = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			String sql = "SELECT * FROM " + loginView.getFname() + ";";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				String[] value = new String[3];
				value[0] = rs.getString("P_Name");
				value[1] = rs.getInt("Amount") + "";
				value[2] = rs.getInt("Cost") + "";
				model3.addRow(value);
				sum1 += Integer.parseInt(value[2]);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		x2 = new JButton("Remove");
		x2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] rows = cOrder.getSelectedRows();
				System.out.println(rows.length);
				System.out.println(rows[0]);
				for (int i = 0; i < rows.length; i++) {
					String xoa = model3.getValueAt(rows[i], 0).toString();
					try {
						Connection con = JDBCUtil.getConnection();
						Statement st = con.createStatement();
						String sql = "DELETE FROM " + loginView.getFname() + " WHERE P_Name = '" + xoa + "';";
						st.execute(sql);
						JDBCUtil.closeConnection(con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				for (int i = 0; i < rows.length; i++) {
					model3.removeRow(rows[i] - i);
				}
			}
		});
		x2.setBounds(160, 20, 90, 30);
		clear2 = new JButton("Clear");
		clear2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Connection con = JDBCUtil.getConnection();
					Statement st = con.createStatement();
					String sql = "TRUNCATE TABLE " + loginView.getFname() + ";";
					st.executeUpdate(sql);
					JDBCUtil.closeConnection(con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					Connection con = JDBCUtil.getConnection();
					Statement st = con.createStatement();
					String sql = "SELECT * FROM " + loginView.getFname() + ";";
					ResultSet rs = st.executeQuery(sql);
					model3.getDataVector().removeAllElements();
					model3.fireTableDataChanged();
					revalidate();
					while (rs.next()) {
						String[] value = new String[3];
						value[0] = rs.getString(1);
						value[1] = rs.getString(2);
						value[2] = rs.getString(3);
						model3.addRow(value);
					}
					JDBCUtil.closeConnection(con);
				} catch (SQLException f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
				}
			}
		});
		clear2.setBounds(20, 20, 90, 30);
		cOrder = new JTable();
		cOrder.setModel(model3);
		JScrollPane scrl3 = new JScrollPane(cOrder);
		myorder.add(scrl3);
		myorder.setBorder(new TitledBorder(new LineBorder(Color.black), "Your Cart", TitledBorder.CENTER,
				TitledBorder.TOP, null, Color.red));
		tt = new JLabel("UnPaid");
		tt.setBounds(235, 100, 100, 25);
		tt.setForeground(Color.red);
		pcost = new JTextField();
		pcost.setText(sum1 + "");
		pcost.setEditable(false);
		pcost.setBounds(210, 70, 100, 30);
		pay = new JButton("Pay Now");
		pay.addActionListener(this);
		pay.setBounds(200, 130, 120, 40);
		ttorder.add(x2);
		ttorder.add(clear2);
		ttorder.add(tt);
		ttorder.add(pay);
		ttorder.add(pcost);
		ttorder.setLayout(null);
		ttorder.setBorder(new TitledBorder(new LineBorder(Color.black), "Payment Information", TitledBorder.CENTER,
				TitledBorder.TOP, null, Color.red));
		OrderView.add(myorder);
		OrderView.add(ttorder);
		OrderView.setLayout(new GridLayout(2, 1));

		greeting = new JLabel();
		greeting.setText("Hello: " + loginView.getFname());
		user.add(greeting);
		user.add(Exit);
		user.setLayout(new GridLayout(2, 1));
		user.setBorder(new EmptyBorder(5, 5, 5, 5));

		show = new JPanel();
		card = new CardLayout();
		show.setLayout(card);
		show.add(Stall, "1");
		show.add(OrderView, "2");
		Buy = new JButton("Stall");
		Buy.setBackground(new Color(122,225,246));
		Buy.addActionListener(this);
		MyOrder = new JButton("My Order");
		MyOrder.setBackground(new Color(122,225,246));
		MyOrder.addActionListener(this);

		JLabel icon = new JLabel();
		ImageIcon imageIcon = new ImageIcon("src/images/shopping.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		icon.setIcon(imageIcon);
		button.add(icon);
		button.add(Buy);
		button.add(MyOrder);
		button.setBorder(new EmptyBorder(5, 5, 5, 5));

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints ordi = new GridBagConstraints();
		this.setLayout(layout);

		ordi.fill = GridBagConstraints.HORIZONTAL;
		ordi.gridx = 0;
		ordi.gridy = 0;
		this.add(user, ordi);

		ordi.gridx = 1;
		ordi.gridy = 0;
		this.add(button, ordi);

		ordi.gridx = 0;
		ordi.gridy = 2;
		ordi.fill = GridBagConstraints.HORIZONTAL;
		ordi.gridwidth = 2;
		this.add(show, ordi);

		this.setVisible(true);
		this.setSize(540, 520);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ImageIcon img2 = new ImageIcon("src/images/buy.png");
		this.setIconImage(img2.getImage());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if (str.equals("Stall")) {
			Buy.setBackground(new Color(124,107,235));
			MyOrder.setBackground(new Color(122,225,246));
			card.show(show, "1");
		} else if (str.equals("My Order")) {
			Buy.setBackground(new Color(122,225,246));
			MyOrder.setBackground(new Color(124,107,235));
			card.show(show, "2");
			try {
				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();
				String sql = "SELECT * FROM " + loginView.getFname() + ";";
				ResultSet rs = st.executeQuery(sql);
				model3.getDataVector().removeAllElements();
				model3.fireTableDataChanged();
				revalidate();
				while (rs.next()) {
					String[] value = new String[3];
					value[0] = rs.getString(1);
					value[1] = rs.getString(2);
					value[2] = rs.getString(3);
					model3.addRow(value);
				}
				JDBCUtil.closeConnection(con);
			} catch (SQLException f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
			}
			int tpcost = 0;
			for (int i = 0; i < model3.getRowCount(); i++) {
				tpcost += Integer.parseInt(model3.getValueAt(i, 2).toString());
			}
			pcost.setText(tpcost + "");
			try {
				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();
				String sql = "SELECT Count(Situation) as sl FROM BILL WHERE C_Name = '"+loginView.getFname()+"';";
				ResultSet rs = st.executeQuery(sql);
				rs.next();
				if(rs.getInt("sl") > 0)
				{
					sql = "SELECT Situation FROM BILL WHERE C_Name = '"+loginView.getFname()+"';";
					ResultSet rs1 = st.executeQuery(sql);
					rs1.next();
					if(rs1.getString("Situation").equals("Waiting"))
					{
					pay.setText("Cancel Order");
					tt.setText("Waiting");
					x2.setEnabled(false);
					clear2.setEnabled(false);
					confirm.setEnabled(false);
					}
				}
				JDBCUtil.closeConnection(con);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (str.equals("My Information")) {
			card.show(show, "3");
		} else if (str.equals("Exit")) {
			this.dispose();
			new loginView();
		} else if (str.equals("Add to your cart")) {
			if (t2.getText().equals("Null"))
				JOptionPane.showMessageDialog(this, "Null Value", "Error", JOptionPane.INFORMATION_MESSAGE);
			else {
				int t = 0;
				String[] tcartrow = new String[4];
				tcartrow[0] = t2.getText();
				tcartrow[1] = t3.getText();
				tcartrow[2] = t5.getText();
				tcartrow[3] = (Integer.parseInt(t4.getText()) * Integer.parseInt(t5.getText())) + "";
				if (tcart.getModel().getRowCount() > 0) {
					for (int i = 0; i < tcart.getModel().getRowCount(); i++) {
						if (tcart.getModel().getValueAt(i, 1).toString().equals(t3.getText())) {
							String te = tcart.getModel().getValueAt(i, 2).toString();
							int t1 = Integer.parseInt(te) + Integer.parseInt(t5.getText());
							tcartrow[2] = t1 + "";
							tcart.getModel().setValueAt(tcartrow[2], i, 2);
							tcartrow[3] = "" + (t1 * Integer.parseInt(t4.getText()));
							tcart.getModel().setValueAt(tcartrow[3], i, 3);
							t += 1;
							model2.fireTableDataChanged();
						}
					}
					if (t == 0)
						model2.addRow(tcartrow);
				} else
					model2.addRow(tcartrow);
			}
		} else if (str.equals("Update")) {
			if (inf1.getText() == "") {
				try {
					Connection con = JDBCUtil.getConnection();
					Statement st = con.createStatement();
					String sql = "UPDATE Accounts SET Address = '" + inf2.getText() + "' WHERE Username ='"
							+ loginView.getInfo1().getText() + "';";
					st.executeUpdate(sql);
					JDBCUtil.closeConnection(con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (inf2.getText() == "") {
				try {
					Connection con = JDBCUtil.getConnection();
					Statement st = con.createStatement();
					String sql = "UPDATE Accounts SET Name ='" + inf1.getText() + "' WHERE Username ='"
							+ loginView.getInfo1().getText() + "';";
					st.executeUpdate(sql);
					JDBCUtil.closeConnection(con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (inf1.getText() != "" && inf2.getText() != "") {
				try {
					Connection con = JDBCUtil.getConnection();
					Statement st = con.createStatement();
					String sql = "UPDATE Accounts SET Name ='" + inf1.getText() + "',Address = '" + inf2.getText()
							+ "' WHERE Username ='" + loginView.getInfo1().getText() + "';";
					st.executeUpdate(sql);
					JDBCUtil.closeConnection(con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (str.equals("Confirm")) {
			if (model3.getRowCount() == 0) {
				try {
					Connection con = JDBCUtil.getConnection();
					Statement st = con.createStatement();
					for (int i = 0; i < tcart.getModel().getRowCount(); i++) {
						String sql = "INSERT INTO " + loginView.getFname() + " VALUES('"
								+ tcart.getModel().getValueAt(i, 1) + "','" + model2.getValueAt(i, 2) + "',"
								+ tcart.getModel().getValueAt(i, 3) + ");";
						st.executeUpdate(sql);
					}
					JDBCUtil.closeConnection(con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				for (int i = 0; i < model2.getRowCount(); i++) {
					boolean flag = false;
					MyBreakLabel: for (int j = 0; j < model3.getRowCount(); j++) {
						if (model2.getValueAt(i, 1).toString().equals(model3.getValueAt(j, 0))) {
							int amount = Integer.parseInt(model3.getValueAt(j, 1).toString())
									+ Integer.parseInt(model2.getValueAt(i, 2).toString());
							int cost = Integer.parseInt(model3.getValueAt(j, 2).toString())
									+ Integer.parseInt(model2.getValueAt(i, 3).toString());
							try {
								Connection con = JDBCUtil.getConnection();
								Statement st = con.createStatement();
								String sql = "DELETE FROM " + loginView.getFname() + " WHERE P_Name = '"
										+ model2.getValueAt(i, 1).toString() + "';";
								st.executeUpdate(sql);
								sql = "INSERT INTO " + loginView.getFname() + " VALUES('"
										+ model2.getValueAt(i, 1).toString() + "'," + amount + "," + cost + ");";
								st.executeUpdate(sql);
								JDBCUtil.closeConnection(con);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							model3.setValueAt(amount, j, 1);
							model3.setValueAt(amount, j, 2);
							model3.fireTableDataChanged();
							flag = false;
							break MyBreakLabel;
						} else {
							flag = true;
						}
					}
					if (flag) {
						String[] value = new String[3];
						value[0] = model2.getValueAt(i, 1).toString();
						value[1] = model2.getValueAt(i, 2).toString();
						value[2] = model2.getValueAt(i, 3).toString();
						model3.addRow(value);
						model3.fireTableDataChanged();
						try {
							Connection con = JDBCUtil.getConnection();
							Statement st = con.createStatement();
							String sql = "INSERT INTO " + loginView.getFname() + " VALUES('"
									+ model2.getValueAt(i, 1).toString() + "'," + model2.getValueAt(i, 2).toString()
									+ "," + model2.getValueAt(i, 3).toString() + ");";
							st.executeUpdate(sql);
							JDBCUtil.closeConnection(con);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		} else if (str.equals("Pay Now")) 
		{
			int tpcost = 0;
			for (int i = 0; i < model3.getRowCount(); i++) {
				tpcost += Integer.parseInt(model3.getValueAt(i, 2).toString());
			}
			try {
				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();
				String sql = "INSERT INTO Bill VALUES('"+loginView.getFname()+"',"+tpcost+",'Waiting');";
				st.executeUpdate(sql);
				JDBCUtil.closeConnection(con);
				tt.setText("Waiting");
				x2.setEnabled(false);
				clear2.setEnabled(false);
				confirm.setEnabled(false);
				pay.setText("Cancel Order");
				pcost.setText(tpcost+"");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (str.equals("Remove")) {
			int[] rows = tcart.getSelectedRows();
			for (int i = 0; i < rows.length; i++) {
				model2.removeRow(rows[i] - i);
			}
		} else if (str.equals("Clear")) {
			model2.getDataVector().removeAllElements();
			model2.fireTableDataChanged();
		} else if (str.equals("->")) {
			int[] rows = sp.getSelectedRows();
			if (model2.getRowCount() == 0) {
				for (int i = 0; i < rows.length; i++) {
					String[] value = new String[4];
					value[0] = sp.getModel().getValueAt(rows[i], 1).toString();
					value[1] = sp.getModel().getValueAt(rows[i], 2).toString();
					value[2] = "1";
					value[3] = sp.getModel().getValueAt(rows[i], 3).toString();
					model2.addRow(value);
					model2.fireTableDataChanged();
				}
			} else {
				boolean flag = false;
				if (sp.getSelectedRows().length == 1) {
					for (int j = 0; j < model2.getRowCount(); j++) {
						if (sp.getModel().getValueAt(rows[0], 2).toString()
								.equals(model2.getValueAt(j, 1).toString())) {
							String set = model2.getValueAt(j, 2).toString();
							int add = Integer.parseInt(set);
							add++;
							model2.setValueAt(add, j, 2);
							int sum = add * (Integer.parseInt(sp.getModel().getValueAt(rows[0], 3).toString()));
							model2.setValueAt(sum, j, 3);
							model2.fireTableDataChanged();
							flag = false;
							break;
						} else {
							flag = true;
						}
					}
				}
				if (flag) {
					String[] value = new String[4];
					value[0] = sp.getModel().getValueAt(rows[0], 1).toString();
					value[1] = sp.getModel().getValueAt(rows[0], 2).toString();
					value[2] = "1";
					value[3] = sp.getModel().getValueAt(rows[0], 3).toString();
					model2.addRow(value);
					model2.fireTableDataChanged();
					flag = false;
				}
			}
		} else if (str.equals("Cancel Order")) {
			try {
				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();
				String sql = "DELETE FROM Bill WHERE C_Name='"+loginView.getFname()+"';";
				st.executeUpdate(sql);
				JDBCUtil.closeConnection(con);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pay.setText("Pay Now");
			tt.setText("UnPaid");
			x2.setEnabled(true);
			clear2.setEnabled(true);
			confirm.setEnabled(true);
		}
	}

	public static void main(String[] args) {
		new PurchaseView();
	}
}
