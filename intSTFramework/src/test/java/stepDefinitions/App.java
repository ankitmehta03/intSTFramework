package stepDefinitions;

import java.io.IOException;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.network.Network;

import com.google.common.collect.ImmutableList;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import utilities.webElementUtil;

public class App extends webElementUtil{

	
	@Given("open {string} and start application")
		public void open_browser_and_start_application(String browser) throws IOException {
		    WebDriver driver = initializeDriver(browser);
		    
		    driver.get("https://amazon.com");
		    
	        
		    click(waitAndFindElement(driver, By.xpath("//span[@class='nav-cart-icon nav-sprite']")));
		}

		@When("I click on Login")
		public void i_click_on_login() {
		    // Write code here that turns the phrase above into concrete actions
		    
		}
		@When("I enter valid {string} and valid {string}")
		public void i_enter_valid_and_valid(String string, String string2) {
		    // Write code here that turns the phrase above into concrete actions
		    
		}
		@Then("I click on Loginbutton")
		public void i_click_on_loginbutton() {
		    // Write code here that turns the phrase above into concrete actions
		    
		}
		@Then("User should be able to login successfully")
		public void user_should_be_able_to_login_successfully() {
		    // Write code here that turns the phrase above into concrete actions
		    
		}



}
