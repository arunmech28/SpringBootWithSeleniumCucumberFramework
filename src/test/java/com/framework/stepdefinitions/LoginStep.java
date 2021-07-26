package com.framework.stepdefinitions;

import com.framework.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

//@Scope("cucumber-glue")
//@CucumberContextConfiguration
//@ContextConfiguration(classes = {SeleniumSpringBootWithCucumberApplication.class},loader = SpringBootContextLoader.class)
//@SpringBootTest(classes = {SeleniumSpringBootWithCucumberApplication.class})
//@SpringBootApplication
//@CucumberContextConfiguration
//@SpringBootTest(classes = CucumberRunnerClass.class)
@SpringBootTest
//@CucumberContextConfiguration
//@ContextConfiguration(classes = SeleniumSpringBootWithCucumberApplication.class)
public class LoginStep {

    @Autowired
   public LoginPage loginPage;

    @Given("^I am on login page$")
    public void navigateToLoginPage()  {
//        Logger.info("in login step");
        loginPage.navigateToUrl("https://www.google.co.in/");
        System.out.println("in navigate to login page method");
        TestUtil.setIteration("1");
        System.out.println(TestUtil.getIterationData("status"));
        TestUtil.setIteration("2");
        System.out.println(TestUtil.getIterationData("status"));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        TestUtil.setScenarioContext("testKey", "Arun Test Data for Scenario Context "+ CurrentThreadInstance.getCurrentScenarioName());
//        Logger.warning("in login step");
    }

    @And("^I login using username and password$")
    public void enterUsername_Password() {
//        loginPage.login("arun","password");
        System.out.println("enter username and password");
    }
}

