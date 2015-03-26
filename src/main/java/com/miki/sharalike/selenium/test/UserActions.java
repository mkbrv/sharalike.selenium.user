package com.miki.sharalike.selenium.test;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mcsere on 3/25/2015.
 */
public class UserActions {

    private final Logger LOG = Logger.getLogger(UserActions.class);

    private List<String> imagesList = new ArrayList<String>();
    private WebDriver driver;
    private String user;
    private String password;

    public UserActions(WebDriver webDriver, String user, String password) {
        this.driver = webDriver;
        this.user = user;
        this.password = password;
    }

    public boolean register() throws InterruptedException {
        Long start = System.currentTimeMillis();
        WebElement signByMail = driver.findElement(By.className("email_signup"));
        signByMail.click();

        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement pwdInput = driver.findElement(By.id("password"));

        emailInput.sendKeys(user);
        pwdInput.sendKeys(password);

        WebElement signUpBtn = driver.findElement(By.id("register"));
        signUpBtn.click();

        System.out.println("User has signed up: " + user);
        this.closeOverlay(5);
        LOG.warn("USER_REGISTERED;USER:" + this.user + " ;TIME:" + (System.currentTimeMillis() - start));
        return true;
    }

    private void closeOverlay(int retry) throws InterruptedException {
        if (retry > 0) {
            WebDriverWait wait = new WebDriverWait(driver, 180);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.className("overlay_close_message")));
            try {
                element.click();
            } catch (Exception e) {
                Thread.sleep(100L);
                closeOverlay(--retry);
            }
        }
    }

    public boolean createFirstAlbum(List<String> pictures) throws Exception {
        this.imagesList = pictures;
        driver.findElement(By.className("btn_import")).click();

        Thread.sleep(100L);
        StringBuilder uploadPicture = new StringBuilder();

        Long start = System.currentTimeMillis();
        for (int i = 0; i < pictures.size(); i++) {
            uploadPicture.append(pictures.get(i));
            if (i != pictures.size() - 1) {
                uploadPicture.append("\n");
            }
        }
        driver.findElement(By.id("newAlbumName")).sendKeys("test");
        driver.findElement(By.name("file[]")).sendKeys(uploadPicture.toString());
        Thread.sleep(100L);
        driver.findElement(By.className("upload")).click();
        driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
        this.closeOverlay(5);
        LOG.warn("FILES_UPLOADED;COUNT: " + pictures.size() + ";TIME:" + (System.currentTimeMillis() - start));
        return true;
    }


    public void createFirstSmartShow() throws Exception {
        Thread.sleep(5000L);
        Long start = System.currentTimeMillis();
        driver.findElements(By.className("album_preview_title")).get(0).click();

        this.closeOverlay(10);
        WebDriverWait wait = new WebDriverWait(driver, 480);

        List<WebElement> allPhotos = driver.findElements(By.className("file_preview_box"));
        for (WebElement photo : allPhotos) {
            photo.click();
        }

        driver.findElement(By.id("playsmartshow")).click();

        //driver.findElement(By.id("saveSmartshow")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("copy_link_button")));
        Thread.sleep(100L);
        driver.findElement(By.className("copy_link_button")).click();
        Thread.sleep(100L);
        driver.findElement(By.id("smartshowName")).sendKeys("test");
        driver.findElement(By.id("save_smartshowButton")).click();
        Thread.sleep(100L);

        //let's share it
        String link = driver.findElement(By.id("copy_link_input")).getAttribute("value");
        if (link.contains("undefined")) {
            link = "";
        }

        LOG.warn("SMARTSHOW_CREATED;URL:" + link + ";TIME:" + (System.currentTimeMillis() - start));
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String toString() {
        return this.user;
    }
}
