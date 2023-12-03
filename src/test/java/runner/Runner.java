package runner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions (
        features = "src/test/java/features",
        // in the line below we manually indicate format of reports and where we want to store them
        plugin = {"pretty", "json:target/jsonReports/cucumber-report.json", "html:target/jsonReports/cucumber-report.html"},
      //  monochrome = true,
        glue = {"stepDefinition"}
      //  tags = "@DeletePlace"
)
public class Runner {

}
