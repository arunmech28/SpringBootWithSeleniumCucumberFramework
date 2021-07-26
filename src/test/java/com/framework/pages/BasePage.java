package com.framework.pages;

import com.framework.constants.FrameworkConstants;
import com.framework.utils.CurrentThreadInstance;
import com.framework.application.SpringBootWithSeleniumCucumberFrameworkApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import java.util.Set;

@Configuration
@CucumberContextConfiguration
@ContextConfiguration(classes = SpringBootWithSeleniumCucumberFrameworkApplication.class, loader = SpringBootContextLoader.class)
@PropertySource("classpath:/application.yml")
public abstract class BasePage {

	public void explicitlyWait(WebElement element) {
		WebDriverWait wait = new WebDriverWait(CurrentThreadInstance.getDriver(), FrameworkConstants.EXPLICITWAIT);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void explicitlyWait(WebElement element, long timeout) {
		WebDriverWait wait = new WebDriverWait(CurrentThreadInstance.getDriver(), timeout);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void goToUrl(String url) {
		CurrentThreadInstance.getDriver().get(url);
		System.out.println(elementGetText(By.xpath("//a[text()='Gmail']")));
	}

	public String elementGetText(By by) {
		return elementGetText(CurrentThreadInstance.getDriver().findElement(by));
	}

	public void click(By by) {
		click(CurrentThreadInstance.getDriver().findElement(by));
	}

	public void sendkeys(By by, String text) {
		sendkeys(CurrentThreadInstance.getDriver().findElement(by), text);
	}

	public void selectByValue(By by, String text) {
		selectByValue(CurrentThreadInstance.getDriver().findElement(by), text);
	}

	public void selectByVisibleText(By by, String text) {
		selectByVisibleText(CurrentThreadInstance.getDriver().findElement(by), text);
	}

	public void selectByIndex(By by, int index) {
		selectByIndex(CurrentThreadInstance.getDriver().findElement(by), index);
	}
	public void moveToElement(By by) {
		moveToElement(CurrentThreadInstance.getDriver().findElement(by));
	}

	public void switchToNewWindow() {
		String parentWinHandle = CurrentThreadInstance.getDriver().getWindowHandle();
		Set<String> winHandles = CurrentThreadInstance.getDriver().getWindowHandles();
		for (String temp : winHandles) {
			if (!temp.equalsIgnoreCase(parentWinHandle)) {
				CurrentThreadInstance.getDriver().switchTo().window(temp);
			}
		}
	}

	public String elementGetText(WebElement element) {
		return element.getText();
	}

	public void click(WebElement element) {
		explicitlyWait(element);
		highlightElement(element);
		element.click();
	}

	public void sendkeys(WebElement element, String text) {
		explicitlyWait(element);
		highlightElement(element);
		element.sendKeys(text);
	}

	public void moveToElement(WebElement element) {
		Actions actions = new Actions(CurrentThreadInstance.getDriver());
		actions.moveToElement(element).build().perform();
	}

	private void highlightElement(WebElement element) {
		((JavascriptExecutor) CurrentThreadInstance.getDriver())
				.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	public void selectByValue(WebElement element, String text) {
		new Select(element).selectByValue(text);
	}

	public void selectByVisibleText(WebElement element, String text) {
		new Select(element).selectByVisibleText(text);
	}

	public void selectByIndex(WebElement element, int index) {
		new Select(element).selectByIndex(index);
	}

}
