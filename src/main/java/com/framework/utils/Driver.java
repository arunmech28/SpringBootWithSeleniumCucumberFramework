package com.framework.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * The Class Driver.
 */
public class Driver {

	public WebDriver driver = null;

	/**
	 * Instantiates a new driver.
	 */
	public Driver() {
		WebDriverManager.chromedriver().driverVersion(PropertyUtililty.getProperty("chromedriver_version")).setup();
		driver = new ChromeDriver();
		CurrentThreadInstance.setWebDriver(driver);
	}

}
