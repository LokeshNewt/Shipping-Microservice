package com.newt.controller;

import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service; 
import com.newt.model.Orders; 
import com.newt.repository.ShippingRepository;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/shipping")
public class ShippingController {
	
	@Autowired
	private ShippingRepository orderRepository;
	@Autowired
    private Notifications notifications;
	private static final Logger logger = Logger.getLogger(ShippingController.class);
	
	@RequestMapping(value = "/{id}/{orderStatus}" ,method = RequestMethod.PUT)
	@ApiOperation(value = "shipping orders")
	public Orders shipOrder(@PathVariable("id") int id,@PathVariable("orderStatus") String orderStatus){
		logger.info("Updating Orderstatus for " + id);
		Orders order = orderRepository.findOrdersByorderId(id);    
	   try{
		
	    if(order.getOrderId()==id){
	    	logger.info("Orders is  Id is  found");
	    	if(order.getOrderStatus().equalsIgnoreCase("processing")){
	    		 order.setOrderStatus("shipped");
	    		 orderRepository.save(order);
	    		}else if(!(order.getOrderStatus().equalsIgnoreCase("processing"))){
	    			//sent mail to user
	    			  notifications.sendNotification("order is not shipped");
	    			  
	    			  logger.info("mail sent successfully.....");
	    		}
	    	 
	    	}
	    	
	    	
	 
	else{
	          	
		logger.info("Order is not found..");
		}
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	    	    
		return order ;
}
}
