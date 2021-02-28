package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		
			features={"src\\test\\resources\\features\\Feature1.feature"},
			glue= {"stepDefinitions"},
			plugin = {"pretty", "html:target/Report.html","json:target/cucumber-reports/json-reports/report.json"},
			monochrome=true
		)
public class RunTest {

}
