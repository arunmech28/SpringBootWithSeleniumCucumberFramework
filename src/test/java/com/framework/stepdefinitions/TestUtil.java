package com.framework.stepdefinitions;

import com.framework.utils.CurrentThreadInstance;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class TestUtil {

    public static ThreadLocal<String> iteration = new ThreadLocal<String>();

    public static String getIteration() {
        return iteration.get();
    }

    public static void setIteration(String itr) {
        iteration.set(itr);
    }

    public static String getData(String fieldName) {
        return CurrentThreadInstance.getCurrentScenarioData().get(fieldName);
    }

    public static String getIterationData(String fieldName) {
        if (TestUtil.getIteration().equalsIgnoreCase("1") || TestUtil.getIteration().isEmpty()){
            return CurrentThreadInstance.getCurrentScenarioData().get(fieldName);
        } else {
            return CurrentThreadInstance.getCurrentScenarioData().get(fieldName+TestUtil.getIteration());
        }
    }

    public static String getScenarioContext(String field) {
        return CurrentThreadInstance.getCurrentScenarioContext().get(field);
    }

    public static void setScenarioContext(String field, String value) {
        HashMap<String, String> scenarioContext = new HashMap<String, String>();
        scenarioContext = CurrentThreadInstance.getCurrentScenarioContext();
        scenarioContext.put(field, value);
        CurrentThreadInstance.setCurrentScenarioContext(scenarioContext);
    }

    public static void takeScreenshot() {
        TakesScreenshot scrShot = ((TakesScreenshot) CurrentThreadInstance.getDriver());
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("");
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String takeScreenshotAsBase64() {
         return ((TakesScreenshot) CurrentThreadInstance.getDriver()).getScreenshotAs(OutputType.BASE64);
    }

    public static byte[] takeScreenshotsAsByteArray() {
        File src = ((TakesScreenshot) CurrentThreadInstance.getDriver()).getScreenshotAs(OutputType.FILE);
        byte[] byteArr = new byte[0];
        try {
            byteArr =  FileUtils.readFileToByteArray(src);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArr;
    }
}
