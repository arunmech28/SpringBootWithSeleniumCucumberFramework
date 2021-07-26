package com.framework.constants;

public class FrameworkConstants {

    public static final String PROJECTPATH = System.getProperty("user.dir");
    public static final String CHROMEDRIVERPATH = System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe";
    public static final String GECKODRIVERPATH = System.getProperty("user.dir") + "\\src\\test\\resources\\geckodriver.exe";
    public static final int EXPLICITWAIT = 10;
    public static final String TESTDATASHEETNAME = "TestData";
    public static final String RUNMANAGERPATH = System.getProperty("user.dir") + "\\src\\test\\resources\\RunManager.xlsx";
    public static final String GLOBALCONFIGURATION = System.getProperty("user.dir") + "\\src\\main\\resources\\GlobalConfig.properties";
    public static final String CONSOLIDATEDREPORTDIRECTORY = System.getProperty("user.dir") + "\\test-output\\consolidated-reports\\";
    public static final String CONSOLIDATEDREPORTFILE = System.getProperty("user.dir") + "\\test-output\\consolidated-reports\\Consolidated_Report.xlsx";
    public static final String SCREENSHOTSFOLDERPATH = System.getProperty("user.dir") + "\\test-output\\screenshots\\";
}
