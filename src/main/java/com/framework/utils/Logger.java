package com.framework.utils;

import java.sql.Timestamp;
import java.util.Date;

public class Logger {
	
	public static void info(String data) {
//		CurrentThreadInstance.getScenario().log(new Timestamp(new Date().getTime()) + "LogInfo {"+data+"} ***");
		ExtentReportManager.getExtentTest().info(new Timestamp(new Date().getTime()) + "LogInfo {"+data+"} ***");
	}
	
	public static void warning(String data) {
		ExtentReportManager.getExtentTest().warning(new Timestamp(new Date().getTime()) + "LogInfo {"+data+"} ***");
	}
}
