package com.framework.stepdefinitions;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.framework.constants.FrameworkConstants;
import com.framework.runner.CucumberRunnerClass;
import com.framework.utils.CurrentThreadInstance;
import com.framework.utils.Driver;
import com.framework.utils.ExcelUtil;
import com.framework.utils.ExtentReportManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Hook {

    public static Scenario scenario;

    @Before
    public void setUp(Scenario scenario) throws IOException {
        checkScreenshotFolderPath(scenario.getName());
        CurrentThreadInstance.setScenario(scenario);
        CurrentThreadInstance.setCurrentScenarioName(scenario.getName());
        if (scenario.getName().contains("iteration")) {
            String[] arr = scenario.getName().split("-");
            String iteration = arr[arr.length - 1];
            String currentScenarioName = scenario.getName().replace("-iteration-" + iteration, "");
            CurrentThreadInstance
                    .setCurrentScenarioData(ExcelUtil.getCurrentScenarioData(currentScenarioName, iteration));
        } else {
            CurrentThreadInstance.setCurrentScenarioData(ExcelUtil.getCurrentScenarioData(scenario.getName(), "1"));
        }
        new Driver();
        CurrentThreadInstance.setCurrentScenarioContext(new HashMap<String, String>());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.getStatus().toString().equalsIgnoreCase("PASSED")) {
            scenario.attach(TestUtil.takeScreenshotsAsByteArray(),
                    "image/png", scenario.getName() + " is passed");
            ExtentReportManager.getExtentTest().pass(scenario.getName() + " is passed ",
                    MediaEntityBuilder
                            .createScreenCaptureFromBase64String(TestUtil.takeScreenshotAsBase64()).build());
        } else if (scenario.getStatus().toString().equalsIgnoreCase("FAILED")) {
            scenario.attach(TestUtil.takeScreenshotsAsByteArray(),
                    "image/png", scenario.getName() + " is failed");
            ExtentReportManager.getExtentTest().fail(scenario.getName() + " is passed ",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(TestUtil.takeScreenshotAsBase64()).build());
        }

        CurrentThreadInstance.getDriver().close();
        ExtentReportManager.getExtentInstance().flush();
    }

    public void checkScreenshotFolderPath(String scenarioName) {
        File currentRunFolder = new File(FrameworkConstants.SCREENSHOTSFOLDERPATH + CucumberRunnerClass.folderName +
                "\\" + scenarioName);
        if (!currentRunFolder.exists()) {
            currentRunFolder.mkdir();
        }
    }

}
