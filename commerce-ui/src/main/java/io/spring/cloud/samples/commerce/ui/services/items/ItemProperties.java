/**
 * 
 */
package io.spring.cloud.samples.commerce.ui.services.items;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import io.spring.cloud.samples.commerce.ui.services.items.Item;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;



/**
 * @author Sonali
 * Description: Built on the fortune teller. This java file has the configuration properties 
 * details.
 *
 */
@ConfigurationProperties(prefix = "commerce")
@RefreshScope
public class ItemProperties {

	  public String getItemFromProperty() {
		  String error = "<html><h1><font color = red> No items available for you to see.<br><br>Sorry, can't Display the result!</h1></font></html>";
		  return error;
  }
	  
	  
}



