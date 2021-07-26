package com.framework.utils;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class CurrentThreadInstance {

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public static ThreadLocal<String> scenarioName = new ThreadLocal<String>();

    public static ThreadLocal<Scenario> scenario = new ThreadLocal<Scenario>();

    public static Scenario getScenario() {
        return scenario.get();
    }

    public static void setScenario(Scenario scenarioInstance) {
        scenario.set(scenarioInstance);
    }

    public static ThreadLocal<HashMap<String, String>> currentScenarioData = new ThreadLocal<HashMap<String, String>>();

    public static ThreadLocal<HashMap<String, String>> currentScenarioContext = new ThreadLocal<HashMap<String, String>>();

    public static HashMap<String, String> getCurrentScenarioContext() {
        return currentScenarioContext.get();
    }

    public static void setCurrentScenarioContext(HashMap<String, String> scenarioContext) {
        currentScenarioContext.set(scenarioContext);
    }

    public static WebDriver getDriver() {

        return driver.get();

    }

    public static void setWebDriver(WebDriver driverparam) {

        driver.set(driverparam);
    }

    public static String getCurrentScenarioName() {

        return scenarioName.get();

    }

    public static void setCurrentScenarioName(String scenario) {

        scenarioName.set(scenario);
    }

    public static Map<String, String> getCurrentScenarioData() {

        return currentScenarioData.get();

    }

    public static void setCurrentScenarioData(HashMap<String, String> alldata) {

        currentScenarioData.set(alldata);
    }

    public static String getFieldData(String fieldName) {
        return currentScenarioData.get().get(fieldName);
    }
}
