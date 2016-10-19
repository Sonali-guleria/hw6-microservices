/**
 * 
 */
package io.spring.cloud.samples.commerce.ui.services.items;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.spring.cloud.samples.commerce.ui.services.items.Item;
import io.spring.cloud.samples.commerce.ui.services.items.ItemProperties;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ser.SerializerCache.TypeKey;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Sonali
 *
 */
@Service
@EnableConfigurationProperties(ItemProperties.class)
public class ItemService {

	  
	@Autowired
	ItemProperties itemProperties;
	
	@Autowired
    RestTemplate restTemplate;
	
	ArrayList<String> items = new ArrayList<String>();
	HashMap<String,String> itemHash ;
	HashMap<String,String> priceHash;
	HashMap<String,String> priceItem;
	
	
	
	//getting all the items
	//@HystrixCommand(fallbackMethod = "fallbackFortune")

	public String getAllItems() {

		//tutorial baeldung
		ResponseEntity<String> response = 
				  restTemplate.getForEntity("http://Item/items", String.class);
		return response.getBody();
		
//		return restTemplate.getForObject("http://Item/items", Item[].class);

		}
	
	
	//getting all the prices
	public String getAllPrices(){
		
		ResponseEntity<String> response = 
				  restTemplate.getForEntity("http://Price/prices", String.class);
		
		return response.getBody();
		
	}
	

	
	public String getByCategory(String cat){
		
		ResponseEntity<String> response = 
				  restTemplate.getForEntity("http://Item/category/"+cat,String.class);
		return response.getBody();		
		
	}
	
	public String getByItemId(long id){
		
		ResponseEntity<String> response = 
				  restTemplate.getForEntity("http://Item/item/"+id,String.class);
		return response.getBody();		
		
	}
	
	
	//below method is used to create hashmap by parsing the Item details
	public HashMap<String, String> parseJsonItems(String str) throws JSONException{
		
		
		
		HashMap<String, String> item = new HashMap<String, String>();
		JSONArray jsonarray = new JSONArray(str);
		for (int i = 0; i < jsonarray.length(); i++) {
		    JSONObject jsonobject = jsonarray.getJSONObject(i);
		    Long idLong = jsonobject.getLong("id");
		    String id = Long.toString(idLong);
		    String name = jsonobject.getString("name");
		    String description = jsonobject.getString("description");
		    String category = jsonobject.getString("category");
		    String details = "&nbsp&nbsp&nbsp&nbsp<b>Name:</b> "+name+ "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <b>Description:</b> "+description+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <b>Category:</b> "+category;
		    item.put(id,details);
		    
		}
		return item;
	
	}

	//below method is used to create hashmap by parsing the Item and price
	public HashMap<String, String> parseJPrices(String str){
		
		
		HashMap<String, String> priceMap = new HashMap<String, String>();
		String price = str.replaceAll("[\"{}]", "");
		String priceArr[] = price.split(",");
		
		for (String s: priceArr) {           
	       
			String temp[] = s.split(":");
			String priceValue = "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<b>Price: </b>"+temp[1];
			priceMap.put(temp[0], priceValue);

	    }
		
		return priceMap;
	
	}


	
	
	public HashMap<String, String> getMergedHash(HashMap<String, String> item, HashMap<String, String> price)
	{
		//reference http://stackoverflow.com/questions/3584625/merge-hashmaps-retaining-values-java
		
		HashMap<String, String> mergedHash = new HashMap<String, String>();
		
		for (String x : item.keySet()) {
			   String y = price.get(x);
			   if (y==null) {
				   mergedHash.put(x, item.get(x));
			   } else {
				   
				   String str1 = item.get(x);
				   String str2 = str1+y;
				   
				   mergedHash.put(x, str2);
			   }
			}

//			for (String x : price.keySet()) {
//			   if (mergedHash.get(x) == null) {
//				   mergedHash.put(x, price.get(x));
//			   }
//			} 
			
			return mergedHash;
			
		
	}
	
	public String getDetails(HashMap<String, String> mergedHash) {
		
		HashMap<String, String> mhash = mergedHash;
		
		String str = "<html>";
		str += "<h2> <font color =#FF5733>The requested details are as below: </font> </h2><br>";
		
		for (String key : mhash.keySet()) {

			str += "<b>Item ID: </b>" + key + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + mhash.get(key);
			str += "<br>";
			}
		return str;
		
		
	}
	
	@HystrixCommand(fallbackMethod = "fallbackItems")
	public String getAllItemPrice() throws JSONException{
		
		String itemData = getAllItems();
		String priceData = getAllPrices();
		
		
		itemHash = parseJsonItems(itemData);
		priceHash = parseJPrices(priceData);
		
		priceItem=getMergedHash(itemHash,priceHash);
		String details = getDetails(priceItem);
		return(details);
		
	}
	
	@HystrixCommand(fallbackMethod = "fallbackCategory")
	public String getDetailsByCategory(String cat) throws JSONException{
		
		String itemData = getByCategory(cat);
		String priceData = getAllPrices();
		
		itemHash = parseJsonItems(itemData);
		priceHash = parseJPrices(priceData);

		priceItem=getMergedHash(itemHash,priceHash);
		String details = getDetails(priceItem);
		return(details);
		
	}
	
	@HystrixCommand(fallbackMethod = "fallbackItem")
	public String getDetailsByItemId(Long id) throws JSONException{
		
		String itemData = getByItemId(id);
		String priceData = getAllPrices();
		
		itemHash = parseJsonItems(itemData);
		priceHash = parseJPrices(priceData);

		priceItem=getMergedHash(itemHash,priceHash);
		String details = getDetails(priceItem);
		return(details);
		
	}
	
	
	//Fall back methods for each type
	
	private String fallbackItems() {
		return itemProperties.getItemFromProperty();
 }	
	
	private String fallbackCategory(String cat) {
		return itemProperties.getItemFromProperty();
 }	
	private String fallbackItem(Long id) {
		return itemProperties.getItemFromProperty();
 }	
	
	
}
