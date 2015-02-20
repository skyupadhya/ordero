package com.cart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cart.model.Custorder;
import com.cart.model.DBConnection;
import com.cart.model.Inventory;
import com.cart.model.MongoDBConnection;
import com.mongodb.DBObject;

 
//@RestController
@Controller
public class cartController {
	String uname ="";
	String pwd = "";
    @RequestMapping(value = "/login/{prodType}/{prodId}")
    public ModelAndView giveLogin(@PathVariable String prodType,
                                  @PathVariable int prodId) {
        System.out.println("in login");
        
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("prodId", prodId);
        mv.addObject("prodType",prodType);
        return mv;
    }
   
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public ModelAndView validateUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("prodType") String prodType,
            @RequestParam("prodId") int prodId) 
            {
        System.out.println("in checkout");
 
        DBConnection connection = new DBConnection();
        boolean signingin = false;
        signingin = connection.signIn(username,password);
       
        ModelAndView mv = new ModelAndView();
       
        // Validate user
        if (signingin) {
            // Valid user
        	MongoDBConnection mongoConnection = new MongoDBConnection();
       
        	if (!mongoConnection.mongoDBProdAvail(prodType, prodId)) {           
        		mv.addObject("error", "Database down! Please come back later.");                
        		mv.setViewName("error");               
        		return mv;            
        	}                   
        	DBObject product = mongoConnection.mongoDBGetProduct(prodType, prodId);
              
            boolean result = connection.checkInventoryForMoreThanZeroItems(prodType, prodId);
            if(result)
            {
            	System.out.println("username inside login controller:"+username);
	            Inventory obj= new Inventory();
	            obj = connection.displayItemType(prodType, prodId);
	            mv.addObject("username",username);
	            mv.addObject("prodId", prodId);
	            mv.addObject("prodType",prodType);
	            mv.addObject("prodRate", obj.getRateperitem());
	            mv.addObject("qty",obj.getQty());
	            mv.addObject("message","Product available");
	        	mv.addObject("product",product);
	            mv.setViewName("checkout");
            }
            else
            {
            	//0 products available
            	 mv.addObject("error", "Sorry! Item Not on Sale.");
                 //mv.addObject("redirectUrl", "http://localhost:8090/store");
                 //mv.addObject("button", "Back to store");
                 mv.setViewName("error"); 
            }
        }
        else {
            // Invalid user
            mv.setViewName("failure");   
            mv.addObject("error", "Invalid login! Wrong username or password. ");
            mv.addObject("redirectUrl", "http://localhost:8080/cart/login/" + prodType + '/' + prodId);
        }
        return mv;
    }
   
    @RequestMapping(value = "/billing")
    public ModelAndView confirmOrder(@RequestParam("username") String username,
            @RequestParam("prodId") int prodId,
            @RequestParam("prodType") String prodType,
            @RequestParam("prodRate") double rate,
            @RequestParam("quantity") int quantityByUser,
            @RequestParam("qty") int qtyInInventory) {
        System.out.println("in checkout");
        
        ModelAndView mv = new ModelAndView();
        
        if (quantityByUser <= qtyInInventory) {
        	System.out.println("username inside checkout controller:"+username);
        	mv.addObject("prodId", prodId);
            mv.addObject("prodType",prodType);
        	mv.addObject("username",username);
        	mv.addObject("prodRate",rate);
        	mv.addObject("quantity",quantityByUser);
            mv.setViewName("billing");
        }
        else {  
            mv.setViewName("error");
            mv.addObject("error", "Invalid input! Quantity requested is more than available."); 
        }
       
        return mv;
    }
    
    @RequestMapping(value = "/placeorder")
    public ModelAndView billingAndUpdateInventory(
    		@RequestParam("username") String username,
            @RequestParam("prodId") int prodId,
            @RequestParam("prodType") String prodType,
            @RequestParam("prodRate") double rate,
            @RequestParam("quantity") int quantityByUser,
            @RequestParam("cardNo") String cardNo) {
       
        System.out.println("in billing");
        System.out.println("username inside billing controller:"+username);
        // TODO :
        // 1) Check if card number is valid
        double totalRate = quantityByUser * rate;
        ModelAndView mv = new ModelAndView();
        
        DBConnection connection = new DBConnection();
        boolean result = connection.validateCard(cardNo, username);
        int orderid = -1;
        if (result) {
        	orderid = connection.pay(username, prodType, quantityByUser, totalRate, cardNo, prodId);
        	if(orderid != -1)
        	{
        		int temp = quantityByUser;
        		while(temp !=0)
        		{
        		result = connection.decrementcount(prodType, prodId);
        		temp--;
        		}
        		System.out.println("username and orderid in billing cntrl just before "
        				+ "setting in confirm = "+username+ ", "+orderid);
        		
        		mv.addObject("success","Done! Successully placed the order");
                mv.addObject("orderid", orderid);
                mv.addObject("username",username);
        		mv.setViewName("done");
        	}
        	else
        	{
        		mv.addObject("error",  "Database down! Please come back later.");
                mv.setViewName("error");
        	}
        }
        else {
            mv.addObject("error", "Invalid input! Please enter a valid Card.");
            mv.setViewName("error");
        }
      
        return mv;
    }
   
    @RequestMapping(value = "/showorder")
    public ModelAndView showOrder(@RequestParam("username") String username) {
       
       System.out.println("in showorder");
       DBConnection connection = new DBConnection();
       List<Custorder> custOrders = new ArrayList<Custorder>();
    
       custOrders = connection.displayOrder(username);
       
       for (Custorder order : custOrders) {
    	   System.out.println("Order Id in controller = " + order.getOrderId());
       }
       
        ModelAndView mv = new ModelAndView("showorder");
        mv.addObject("orders",custOrders);
        mv.addObject("username", username);
        return mv;
    }
    
    @RequestMapping(value = "/cancel")
    public ModelAndView showDone(@RequestParam("username") String username,
    		                     @RequestParam("orderid") int orderid) {
       
        System.out.println("in cancel order");
        System.out.println("Order id " + orderid);
        
        ModelAndView mv = new ModelAndView();
        DBConnection connection = new DBConnection();
        boolean result = connection.cancelOrder(orderid);
        
        if (result) {
        	mv.setViewName("cancel");
        	mv.addObject("username", username);
        	mv.addObject("orderid", orderid);
        }
        else {
            mv.addObject("error", "Sorry! Cannot cancel the order");
            mv.setViewName("error");
        }
        
        return mv;
    }
   
}