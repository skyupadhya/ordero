package com.cart.model;

public class Inventory{
	private int id;
	private int qty;
	private double rateperitem;
	private String itemType;
	private int itemtypeid;
	
	public Inventory()
	{
		
	}
	public Inventory(int id,int qty,double rate,String itemType)
	{
		this.setId(id);
		this.setQty(qty);
		this.setRateperitem(rate);
		this.setItemType(itemType);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getRateperitem() {
		return rateperitem;
	}
	public void setRateperitem(double rateperitem) {
		this.rateperitem = rateperitem;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public int getItemtypeid() {
		return itemtypeid;
	}
	public void setItemtypeid(int itemtypeid) {
		this.itemtypeid = itemtypeid;
	}
}