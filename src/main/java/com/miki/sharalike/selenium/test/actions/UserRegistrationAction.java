package com.miki.sharalike.selenium.test.actions;

import com.miki.sharalike.selenium.test.actions.abstr.AbstractAction;
import com.miki.sharalike.selenium.test.actions.abstr.IAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by mcsere on 3/27/2015.
 */
public class UserRegistrationAction extends AbstractAction implements IAction {


    public UserRegistrationAction(WebDriver driver) {
        super(driver);
    }

    public UserRegistrationAction(WebDriver driver, String usr, String pwd) {
        super(driver, usr, pwd);
    }

    @Override
    protected boolean before() {
        return true;
    }

    @Override
    protected boolean actualAction() {
        WebElement signByMail = driver.findElement(By.className("email_signup"));
        signByMail.click();
        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement pwdInput = driver.findElement(By.id("password"));
        emailInput.sendKeys(user);
        pwdInput.sendKeys(password);
        WebElement signUpBtn = driver.findElement(By.id("register"));
        signUpBtn.click();
        LOG.info(this.getActionName() + ": User has signed up: " + user);
        this.waitForPage("/albums", 1000L);
        this.clickElement("overlay_close_message", 1000L);
        return true;
    }

    @Override
    protected boolean after() {
        return true;

    }

    @Override
    protected String getActionName() {
        return "USER_REGISTRATION";
    }


}
