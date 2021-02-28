package utilities;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


public class webElementUtil extends initWebDriver {

	
	public WebElement waitAndFindElement(WebDriver driver, By by) {
		// Waiting 30 seconds for an element to be present on the page, checking
				// for its presence once every 5 seconds.
		
		  System.out.println("wait for it");
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(30))
				  .pollingEvery(Duration.ofSeconds(5))
				  .ignoring(NoSuchElementException.class);

				WebElement element = wait.until(new Function<WebDriver, WebElement>() {
				  public WebElement apply(WebDriver driver) {
					
				    return driver.findElement(by);
				  }
				});
				return element;
	}
	
	
	public static void click(WebElement element)
	{
		element.click();
	}
	
	
}
