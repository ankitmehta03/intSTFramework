package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.network.Network;
import org.openqa.selenium.devtools.network.model.BlockedReason;
import org.openqa.selenium.devtools.network.model.ResourceType;
import org.openqa.selenium.devtools.network.model.Response;
import org.openqa.selenium.devtools.network.model.ResponseBody;
import org.openqa.selenium.devtools.network.model.ResponseReceived;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.devtools.network.Network.loadingFailed;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.google.common.collect.ImmutableList;


public class initWebDriver{

	public static WebDriver initializeDriver(String Browser) throws IOException
	{
		WebDriver driver = null;
		
		 BufferedWriter writer = new BufferedWriter(new FileWriter("c:/temp/samplefile1.txt"));
		   
		    
		    
		switch(Browser.toLowerCase()){
		case "chrome": 
			 System.setProperty("webdriver.chrome.driver", "src/main/java/resources/chromedriver.exe");
		       HashMap<String,Object> map = new HashMap<String, Object>();
		        map.put("profile.default_content_setting_values.notifications", 2);
		        ChromeOptions options = new ChromeOptions();
		        options.setExperimentalOption("prefs", map);
		        driver = new ChromeDriver(options);
		        driver.manage().window().maximize();
		        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		        
		        DevTools chromeDevTools = ((ChromeDriver) driver).getDevTools();
		        chromeDevTools.createSession();
		        chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		        chromeDevTools.addListener(Network.requestWillBeSent(),
		                entry -> {
		                	
		                	 System.out.println("Request URI : " + entry.getRequest().getUrl()+"\n"
		                    + " With method : "+entry.getRequest().getMethod() + "\n"+Network.getResponseBody(entry.getRequestId()));
		                	
		                	 try {
									writer.write(("Request URI : " + entry.getRequest().getUrl()+"\n"
									        + " With method : "+entry.getRequest().getMethod() + "\n"+Network.getResponseBody(entry.getRequestId())+ "\n"));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			        		   
		                	 entry.getRequest().getMethod();
		                    
		                });
		        chromeDevTools.addListener(Network.responseReceived(),
		 		     entry2->{
                    Response response = entry2.getResponse();
                    System.out.println("----------------------------------------");
                    System.out.println("URL       : " + response.getUrl());
                    System.out.println("Status    : HTTP " + response.getStatus().intValue() + " " + response.getStatusText());
                    System.out.println("Mime Type : " + response.getMimeType());
                    Command<ResponseBody> rb = Network.getResponseBody(entry2.getRequestId());
                    if ( rb != null ) {
                        String body = rb.toString();
                        System.out.println("Content   : " + body.substring(0, body.length() > 1024 ? 1024 : body.length()));
                    }
		        
		                });
		        
		        //add event listener to verify that css and png are blocked
		      /*  chromeDevTools.addListener(loadingFailed(), loadingFailed -> {

		            if (loadingFailed.getResourceType().equals(ResourceType.Stylesheet)) {
		                assertEquals(loadingFailed.getBlockedReason(), BlockedReason.inspector);
		            }

		            else if (loadingFailed.getResourceType().equals(ResourceType.Image)) {
		                assertEquals(loadingFailed.getBlockedReason(), BlockedReason.mixedContent);
		            }

		        });*/
		        
			return driver;
		case "firefox": 
			 driver = new ChromeDriver();
			return driver;
		}
		writer.close();
		return driver;
		
	}

	
	 private static void interceptNetwork(DevTools chromeDevTools) {
	        chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

	        //set blocked URL patterns
	        chromeDevTools.send(Network.setBlockedURLs(ImmutableList.of("*.css", "*.jpg")));

	        //add event listener to verify that css and png are blocked
	        chromeDevTools.addListener(loadingFailed(), loadingFailed -> {

	            if (loadingFailed.getResourceType().equals(ResourceType.Stylesheet)) {
	                assertEquals(loadingFailed.getBlockedReason(), BlockedReason.inspector);
	            }

	            else if (loadingFailed.getResourceType().equals(ResourceType.Image)) {
	                assertEquals(loadingFailed.getBlockedReason(), BlockedReason.mixedContent);
	            }

	        });

	

	 }
	 
	 
	 public void sendReport()
	 {
		 String connectionString = "<CONNECTION STRING to EVENT HUBS NAMESPACE>";
		 String eventHubName = "<EVENT HUB NAME>";
		 
		 EventHubProducerClient producer = new EventHubClientBuilder()
				    .connectionString(connectionString, eventHubName)
				    .buildProducerClient();
		 EventDataBatch batch = producer.createBatch();
		 batch.tryAdd(new EventData("First event"));
		 
		  // send the batch of events to the event hub
	        producer.send(batch);

	        // close the producer
	        producer.close();
		 
	 }
}