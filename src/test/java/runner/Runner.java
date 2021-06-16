package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"json:target/cucumber.json", "rerun:target/rerun.txt"},
        features = "src/test/resources/features",
        glue = "steps",
        tags = "@HR-0614",
        // run only those feature file that has @smoke and @regression tag.
        // tags = "@HR-5 or @HR-6", // run only those feature file that has either @HR-5 or @HR-6 tag.
        dryRun = false
)
public class Runner {

}
