package view;

import java.awt.*;
import dao.*;
import database.JDBCUtil;
import java.awt.event.*;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class loginView extends JFrame implements ActionListener {

	private JLabel username, password, sign, username2, password2, urname, address;
	private static JTextField info1;
	private JTextField info01;
	private JTextField info3;
	private JTextField info4;
	private JPasswordField info2, info02;
	private JButton register, login, create, loginByManager, exit, btlogin;
	private JPanel loginPanel1, loginPanel2, registerPanel;
	private CardLayout card;
	private Container cont;
	private Accounts accounts;
	private AccountsDao accountsDao;
	private static String fname;

	public loginView() {
		// custome icon
		URL urlp1 = loginView.class.getResource("p1.png");
		Image img = Toolkit.getDefaultToolkit().createImage(urlp1);
		this.setIconImage(img);

		card = new CardLayout();
		cont = this.getContentPane();

		accounts = new Accounts();
		accountsDao = new AccountsDao();

		JLabel icon = new JLabel();
		ImageIcon imageIcon = new ImageIcon("src/images/hello.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		icon.setIcon(imageIcon);
		icon.setBounds(145,10,50,50);
		urname = new JLabel("Your Name:");
		username = new JLabel("Username:");
		username.setBounds(30,70,100,30);
		password = new JLabel("Password:");
		password.setBounds(30,110,100,30);
		address = new JLabel("Your address");
		info1 = new JTextField();
		info1.setBounds(185, 70, 120, 30);
		info2 = new JPasswordField();
		info2.setBounds(185, 110, 120, 30);
		info3 = new JTextField();
		info4 = new JTextField();
		register = new JButton("Create Account");
		register.setBounds(20, 220, 140, 30);
		register.addActionListener(this);
		login = new JButton("Sign in");
		login.setBounds(20,170,140,30);
		login.addActionListener(this);
		exit = new JButton("Exit");
		exit.setBounds(175, 220, 140, 30);
		exit.addActionListener(this);
		loginByManager = new JButton("Login by Manager");
		loginByManager.setBounds(175, 170, 140, 30);
		loginByManager.addActionListener(this);
		btlogin = new JButton("Back to login");
		btlogin.addActionListener(this);
		create = new JButton("Sign up");
		create.addActionListener(this);
		loginPanel1 = new JPanel();
		loginPanel2 = new JPanel();
		registerPanel = new JPanel();
		username2 = new JLabel("Username:");
		password2 = new JLabel("Password:");
		info01 = new JTextField();
		info02 = new JPasswordField();

		loginPanel1.setBorder(new EmptyBorder(7, 7, 7, 7));
		loginPanel1.add(icon);
		loginPanel1.add(username);
		loginPanel1.add(info1);
		loginPanel1.add(password);
		loginPanel1.add(info2);
		loginPanel1.add(login);
		loginPanel1.add(register);
		loginPanel1.add(loginByManager);
		loginPanel1.add(exit);
		loginPanel1.setLayout(null);

		registerPanel.add(urname);
		registerPanel.add(info3);
		registerPanel.add(username2);
		registerPanel.add(info01);
		registerPanel.add(password2);
		registerPanel.add(info02);
		registerPanel.add(address);
		registerPanel.add(info4);
		registerPanel.add(create);
		registerPanel.add(btlogin);
		registerPanel.setLayout(new GridLayout(5, 2));
		registerPanel.setBorder(new EmptyBorder(7, 7, 7, 7));

		cont.add(loginPanel1);
		cont.setLayout(card);
		cont.setBackground(Color.blue);

		setVisible(true);
		setLocationRelativeTo(null);
		setSize(360, 320);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("LoginBox");
	}

	public static JTextField getInfo1() {
		return info1;
	}

	public void setInfo1(JTextField info1) {
		this.info1 = info1;
	}

	public static String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Create Account")) {
			cont.add(registerPanel);
			card.next(cont);
		}
		if (e.getActionCommand().equals("Back to login")) {
			card.next(cont);
			info01.setText("");
			info02.setText("");
			info3.setText("");
			info4.setText("");
		}

		if (e.getActionCommand().equals("Sign up")) {
			if (info01.getText().equals("") || info02.getText().equals("") || urname.getText().equals("")) {
				JOptionPane.showMessageDialog(cont, "Null Value!!", "About", JOptionPane.INFORMATION_MESSAGE);
			} else {
				this.accounts.setUsername(info01.getText());
				this.accounts.setPassword(info02.getText());
				this.accounts.setName(info3.getText());
				this.accounts.setAddress(info4.getText());
				this.accountsDao.insert(accounts);
				JOptionPane.showMessageDialog(cont, "Sign up success!!", "About", JOptionPane.INFORMATION_MESSAGE);
				try {
					Connection con = JDBCUtil.getConnection();
					Statement st = con.createStatement();
					String sql = "CREATE TABLE " + info3.getText()
							+ "(P_Name VARCHAR(50) NOT NULL PRIMARY KEY,Amount SMALLINT NOT NULL, Cost INT NOT NULL);";
					st.executeUpdate(sql);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				card.next(cont);
			}
		}
		if (e.getActionCommand().equals("Sign in")) {
			try {
				Connection con = JDBCUtil.getConnection();

				Statement st = con.createStatement();

				String sql = "SELECT Username,Password FROM Accounts WHERE Role = 'customer'";

				ResultSet rs = st.executeQuery(sql);
				
				boolean flag = true;
				while (rs.next()) {
					flag = true;
					if (info1.getText().equals(rs.getString("Username"))) {
						if (info2.getText().equals(rs.getString("Password"))) {
							JOptionPane.showMessageDialog(cont, "Login success!", "About",
									JOptionPane.INFORMATION_MESSAGE);
							rs.close();
							Connection con1 = JDBCUtil.getConnection();
							Statement st1 = con1.createStatement();
							String sql1 = "SELECT name FROM accounts WHERE username = '" + info1.getText() + "';";
							ResultSet rs1 = st1.executeQuery(sql1);
							rs1.next();
							fname = rs1.getString("name");
							JDBCUtil.closeConnection(con1);
							this.setVisible(false);
							new PurchaseView();
							break;
						} else
							flag = false;
					} else
						flag = false;
				}
				if (flag == false) {
					JOptionPane.showMessageDialog(cont, "Wrong Username or Password!", "About",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand().equals("Login by Manager")) {
			try {
				Connection con = JDBCUtil.getConnection();

				Statement st = con.createStatement();

				String sql = "SELECT Username,Password FROM Accounts WHERE Role = 'Manager'";

				ResultSet rs = st.executeQuery(sql);

				boolean flag = true;

				while (rs.next()) {
					if (info1.getText().equals(rs.getString("Username"))) {
						if (info2.getText().equals(rs.getString("Password"))) {
							JOptionPane.showMessageDialog(cont, "Login success!", "About",
									JOptionPane.INFORMATION_MESSAGE);
							this.dispose();
							new ManagerView();
							break;
						} else
							flag = false;
					} else
						flag = false;
				}
				if (flag == false) {
					JOptionPane.showMessageDialog(cont, "Wrong Username or Password!", "About",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals("Exit")) {
			this.dispose();
			System.exit(0);
		}
	}
	public static void main(String[] args) {
		new loginView();
	}
}
