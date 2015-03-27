package com.miki.sharalike.selenium.test.actions.abstr;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by mcsere on 3/27/2015.
 */
public abstract class AbstractAction implements IAction {

    protected Logger LOG = Logger.getLogger(getClass());
    protected WebDriver driver;
    protected String user;
    protected String password;

    public AbstractAction(WebDriver driver) {
        this.driver = driver;
    }

    public AbstractAction(WebDriver driver, String usr, String pwd) {
        this.driver = driver;
        this.user = usr;
        this.password = pwd;
    }

    @Override
    public void doAction() {
        try {
            this.before();
            long start = System.currentTimeMillis();
            this.actualAction();
            LOG.warn(this.getActionName() + " TIME: " + (System.currentTimeMillis() - start));
            this.after();
        } catch (Exception e) {
            LOG.error(this.getActionName() + ": has crashed;");
        }
    }

    protected boolean clickElement(String clickElement, Long timeout) {
        try {
            if (timeout < 0) {
                return false;
            }
            if (driver.findElements(By.className(clickElement)).isEmpty()) {
                Thread.sleep(250L);
                this.clickElement(clickElement, timeout - 250L);
            } else {
                WebDriverWait wait = new WebDriverWait(driver, 5);
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.className(clickElement)));
                element.click();
            }
        } catch (Exception e) {
            //e.printStackTrace();
            // this.clickElement(clickElement, timeout -250L );
        }
        return true;
    }

    protected boolean waitForPage(String url, Long timeout) {
        if (timeout < 0) {
            return false;
        }
        try {
            if (driver.getCurrentUrl().contains(url)) {
                return true;
            } else {
                Thread.sleep(250L);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return waitForPage(url, timeout - 250L);
    }

    protected abstract boolean before() throws InterruptedException;

    protected abstract boolean actualAction() throws InterruptedException;

    protected abstract boolean after();

    protected abstract String getActionName();
}
