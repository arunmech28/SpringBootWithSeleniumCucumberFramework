package com.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentReports> extentInstance = new ThreadLocal<ExtentReports>();

	public static ExtentReports getExtentInstance() {
		return extentInstance.get();
	}

	public static void setExtentInstance(ExtentReports extentInstance1) {
		extentInstance.set(extentInstance1);
	}

	public static ExtentTest getExtentTest() {
		return extentTest.get();
	}

	public static void setExtentTest(ExtentTest extTest1) {
		extentTest.set(extTest1);
	}

	public static void initReport(String scenarioName, String featureName, String folderName) {
		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir")
				+ "\\test-output\\individual-reports\\" +folderName+"\\"+ featureName + "\\" + scenarioName + ".html");
		extent.attachReporter(spark);
		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle(scenarioName + " Report");
		spark.config().setReportName(scenarioName);
		ExtentReportManager.setExtentTest(extent.createTest(scenarioName));
		ExtentReportManager.setExtentInstance(extent);
	}
	
}
