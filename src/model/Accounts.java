package model;

public class Accounts {
	private String username;
	private String password;
	private String name;
	private String role;
	private String address;
	public Accounts(String username, String password, String name, String role, String address) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
		this.address = address;
	}
	public Accounts() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
