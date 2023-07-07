package model;

public class Products {
	private String P_type;
	private String P_code;
	private String P_name;
	private int P_price;
	public Products(String p_type, String p_code, String p_name, int price) {
		super();
		this.P_type = p_type;
		this.P_code = p_code;
		this.P_name = p_name;
		this.P_price = price;
	}
	public Products() {
		super();
	}
	public String getP_type() {
		return P_type;
	}
	public void setP_type(String p_type) {
		P_type = p_type;
	}
	public String getP_code() {
		return P_code;
	}
	public void setP_code(String p_code) {
		P_code = p_code;
	}
	public String getP_name() {
		return P_name;
	}
	public void setP_name(String p_name) {
		P_name = p_name;
	}
	public int getP_Price() {
		return P_price;
	}
	public void setP_Price(int price) {
		this.P_price = price;
	}
	@Override
	public String toString() {
		return "Products [P_type=" + P_type + ", P_code=" + P_code + ", P_name=" + P_name + ", P_price=" + P_price
				+ "]";
	}
	
}
