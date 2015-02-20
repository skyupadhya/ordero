package com.cart.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.cart.model.ShaHashin;
//import com.cart.model.Custorder;

public class DBConnection {

	Connection con = null;
	static ResultSet rs;
	Statement stmnt = null;

	public DBConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cart","sandy","locked");

			stmnt = con.createStatement();

			if(!con.isClosed()){
				System.out.println("Successfully connected");
			}
		}
		catch(Exception db)
		{
			db.printStackTrace();
		}
	}
	
	public Boolean signIn(String username, String pwd){
		Boolean result = false;
		
		try{
			ShaHashin shaaa = new ShaHashin();

			String password = shaaa.shaHashing(pwd);
			
			String query = "select * from customer where username = '"+username+"' and password = '"+password+"'";

			System.out.println(password);
			ResultSet rs = null;
			System.out.println("query executed  "+query);
			rs = stmnt.executeQuery(query);
			if(rs.next()){
				if(rs.getString("password").equals(password))
					result = true;
				else 
					result = false;
				System.out.println("Logged in successful");
			}
			else
			{
				result = false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public boolean validateCard(String cardno,String username)
	{
		System.out.println("Inside validatecard fn");
		System.out.println("username"+username);
		System.out.println("cardno"+cardno);
		Boolean result = false;
		String query = "select creditcardno  from customer "
				+ "where username= '"+username+"'and creditcardno='"+cardno+"'";
		ResultSet rs = null;
		try {
			rs = stmnt.executeQuery(query);
			if(rs.next())
			{
				System.out.println("creditcardno="+rs.getString("creditcardno"));
				if(rs.getString("creditcardno").equals(cardno))
					result = true;
				else
					result = false;
			}
			else
			{
				System.out.println("Empty result");
				result = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	public boolean checkInventoryForMoreThanZeroItems(String itemtype,int itemtypeid)
	{
		boolean result = false;
		String query ="select qty from inventory where itemtype = '"+itemtype+"'and itemtypeid='"+itemtypeid+"'";
		ResultSet rs = null;
		try {
			rs = stmnt.executeQuery(query);
			if(rs.next())
			{
				if(rs.getInt("qty") > 0)
					result = true;
				else
					result = false;
			}
			else
			{
				result = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	public Inventory displayItemType(String itemType,int itemtypeid)
	{
		Inventory obj = new Inventory();
		String query = "select * from inventory where itemtype = '"+itemType+"'and itemtypeid='"+itemtypeid+"'";
		ResultSet rs = null;
		try {
			rs = stmnt.executeQuery(query);
			if(rs.next())
			{
				obj.setId(rs.getInt("itemid"));
				obj.setItemType(rs.getString("itemtype"));
				obj.setQty(rs.getInt("qty"));
				obj.setRateperitem(rs.getDouble("rateperitem"));
				obj.setItemtypeid(itemtypeid);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	  public static Date addDays(Date date, int days) {
	        GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(date);
	        cal.add(Calendar.DATE, days);
	                 
	        return cal.getTime();
	    }
	public int pay(String username,String itemType,int qty,double rate,String cardno,int itemtypeid)
	{
		int result = -1;
		if(validateCard(cardno, username))
		{
			String status ="Shipped";
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");	
			Date orderdate = new Date();
			String strorderdate = sdf.format(orderdate);
			Date deliverydate = addDays(orderdate, 3);
			String strdeliverydate = sdf.format(deliverydate);
				String query = "insert into custorder(qty,rate,status,orderdate,deliverydate,"
						+ "itemtype,username,itemtypeid)"
						+ "values"
						+ "('"+qty+"','"+rate+"','"+status+"','"+strorderdate+"','"+strdeliverydate+"','"
						+itemType+"','"+username+"','"+itemtypeid+"')";
				String queryOrderId ="select orderid from custorder where username='"+username+"'";
				ResultSet rset = null;
				try {
					int rs = stmnt.executeUpdate(query);
					System.out.println("insert query into custorder rs:"+rs);
					if(rs>0){
						rset = stmnt.executeQuery(queryOrderId);
						while(rset.next())
						{	
							result = rset.getInt("orderid");
							
						}
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				
		}
		else
		{
			result = -1;
		}
		System.out.println("result orderid:"+result);
		return result;
	}
	public boolean decrementcount(String itemType,int itemtypeid)
	{
		boolean result = false;
		String query = "update inventory set qty= qty-1 where "
				+ "itemtype='"+itemType+"'and itemtypeid='"+itemtypeid+"'";
		
		try {
			int rs = stmnt.executeUpdate(query);
			if(rs>0){
			result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return result;
	}
	
	public boolean incrementcount(String itemType,int itemtypeid)
	{
		boolean result = false;
		String query = "update inventory set qty= qty+1 where "
				+ "itemtype='"+itemType+"'and itemtypeid='"+itemtypeid+"'";
		
		try {
			int rs = stmnt.executeUpdate(query);
			if(rs>0){
			result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return result;
	}
	
	public List<Custorder> displayOrder(String username)
	{
		List<Custorder> custOrderData = new ArrayList<Custorder>();
		
		String query = "select * from custorder where username = '"+username+"'";
		Date currentDate = new Date();
		String deliver ="Delivered";
		int id = 0;
		String updateDeliverQuery = "update custorder set status ='"+deliver+"' where orderid ="
				+ "'"+id+"'";

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		ResultSet rs = null;
		try {
			rs = stmnt.executeQuery(query);
			while(rs.next())
			{
				Custorder obj = new Custorder();
				String strDeliveryDate = rs.getString("deliverydate");
				
				String stat = rs.getString("status");
				id = rs.getInt("orderid");
							
				obj.setOrderId(id);
				obj.setItemType(rs.getString("itemtype"));
				obj.setItemTypeId(rs.getInt("itemtypeid"));
				obj.setStatus(stat);
				obj.setUsername(username);
				obj.setOrderDate(rs.getString("orderdate"));
				obj.setDeliveryDate(strDeliveryDate);
				custOrderData.add(obj);
				System.out.println("Order Id = " + id);
				System.out.println("Order Id = " + obj.getOrderId());
			}
			for (Custorder order : custOrderData) {
				String strdd = order.getDeliveryDate();
				Date deliveryDate = sdf.parse(strdd);
		    	   System.out.println("Order Id in DB connection = " + order.getOrderId());
		   		if(order.getStatus().equals("Shipped") && chkDate(currentDate, deliveryDate))
		   		{
		   			stmnt.executeUpdate(updateDeliverQuery);
		   			order.setStatus("Delivered");
		   		}
		    	   
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return custOrderData;
	}
	
	public boolean cancelOrder(int orderId)
	{
		boolean result = false;
		try {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String strDeliveryDate ="";
		String queryDate ="select deliverydate,itemtype,itemtypeid from custorder where orderid='"+orderId+"'"; 
		String itemType ="";
		int itemTypeId =0;
		ResultSet val = stmnt.executeQuery(queryDate);
		if(val.next())
		{
			strDeliveryDate = val.getString("deliverydate");
		    itemType = val.getString("itemtype");
		    itemTypeId = val.getInt("itemtypeid");
		}
	
		Date deliveryDate = sdf.parse(strDeliveryDate);
		Date currentDate = new Date();
		if(!chkDate(currentDate,deliveryDate))
		{
		String query ="update custorder set status ='cancelled' where orderid ='"+orderId+"'";
		
			int rs = stmnt.executeUpdate(query);
			if(rs>0){
				result = true;
				incrementcount(itemType,itemTypeId);
				
				}
		}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public boolean chkDate(Date currentDate,Date deliveryDate)
	{
		boolean result = false;
		//currentdate bigger than deliverydate
		if(currentDate.compareTo(deliveryDate) > 0)
		{
			result = true;
		}
		else
		{
			result = false;
		}
		return result;
	}
}