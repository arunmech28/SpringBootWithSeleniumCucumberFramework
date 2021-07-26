package com.framework.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Driver {

	public WebDriver driver = null;

	public Driver() {
		WebDriverManager.chromedriver().driverVersion(PropertyUtililty.getProperty("chromedriver_version")).setup();
		driver = new ChromeDriver();
		CurrentThreadInstance.setWebDriver(driver);
	}

}
