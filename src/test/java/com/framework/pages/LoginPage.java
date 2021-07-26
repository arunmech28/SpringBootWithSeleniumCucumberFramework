package com.framework.pages;

import com.framework.annotations.Page;
import org.openqa.selenium.By;

@Page
public class LoginPage extends BasePage {

    public By txtbox_username = By.name("username");
    public By txtbox_password = By.name("password");

    public void login(String username1, String password) {

        System.out.println("data");
    }

    public void navigateToUrl(String url) {
                goToUrl(url);
    }
}
