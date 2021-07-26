package com.framework.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.framework.stepdefinitions"},
        plugin = {"com.framework.runner.CustomReportListener", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "json:target/cucumber-report.json"}
)
public class CucumberRunnerClass extends AbstractTestNGCucumberTests {

  /*  @Autowired
    private ObjectMapper objectMapper;

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        super.runScenario(pickleWrapper,featureWrapper);
    }

    @Override
    @DataProvider(parallel = true, name = "scenarios")
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @PostConstruct
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }
*/

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    public static String folderName;

    static {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyhhmmss");
        folderName = formatter.format(date);
    }
}
