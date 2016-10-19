package io.spring.cloud.samples.commerce.ui.controllers;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.spring.cloud.samples.commerce.ui.services.items.Item;
import io.spring.cloud.samples.commerce.ui.services.items.ItemService;



@RestController
public class UiController {

    @Autowired
    ItemService service;

    @RequestMapping("/itemsDetails")
    public String getAllItems(){
    	return service.getAllItems();
   }
   
    @RequestMapping("/items")
    public String getAllItemPrice() throws JSONException {
  	    return service.getAllItemPrice();
    } 
    
    
    @RequestMapping("/prices")
    public String getAllPrices() throws JSONException{
    	return service.getAllPrices();
    }
    
    @RequestMapping("/category/{cat}")
    public String getDetailsByCategory(@PathVariable("cat") String category) throws JSONException{
    	return service.getDetailsByCategory(category);
    }
//    public String getByCategory(@PathVariable("cat") String category){
//    	return service.getByCategory(category);
    


    @RequestMapping("/item/{id}")
    public String getDetailsByItemId(@PathVariable("id") long id) throws JSONException {
  	    return service.getDetailsByItemId(id);
    } 
    
//    public String getByItemId(@PathVariable("id") long id) {
//  	    return service.getByItemId(id);
//    } 
//    
   
  
}