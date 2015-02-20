package com.cart.model;

public class Customer {
	private int id;
	private String username;
	private String password;
	private String creditcardno;

	public Customer() {
	}

	public Customer(String name, String pswd, String creditcardno) {
		this.username = name;
		this.password = pswd;
		this.creditcardno = creditcardno;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pswd) {
		this.password = pswd;
	}

	public String getCreditcardno() {
		return creditcardno;
	}

	public void setCreditcardno(String Creditcardno) {
		this.creditcardno = Creditcardno;
	}

}