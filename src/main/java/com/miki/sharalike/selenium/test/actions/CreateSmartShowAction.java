package com.miki.sharalike.selenium.test.actions;

import com.miki.sharalike.selenium.test.actions.abstr.AbstractAction;
import com.miki.sharalike.selenium.test.actions.abstr.IAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by mcsere on 3/27/2015.
 */
public class CreateSmartShowAction extends AbstractAction implements IAction {
    public CreateSmartShowAction(WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean before() throws InterruptedException {
        Thread.sleep(7000L);
        return false;
    }

    private boolean openAlbum(int retries) throws InterruptedException {
        if (retries < 0) {
            return false;
        }
        try {
            Thread.sleep(1250L);
            driver.findElements(By.className("album_preview_title")).get(0).click();
        } catch (Exception e) {

        }
        Thread.sleep(250L);
        if (driver.getCurrentUrl().contains("photos/")) {
            return true;
        } else {
            openAlbum(--retries);
        }
        return true;
    }

    @Override
    protected boolean actualAction() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        this.clickElement("overlay_close_message", 1000L);
        this.openAlbum(10);
        this.clickElement("overlay_close_message", 1000L);

        Thread.sleep(1000L);
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
        return true;
    }

    @Override
    protected boolean after() {
        return false;
    }

    @Override
    protected String getActionName() {
        return "CREATE_SMARTSHOW";
    }
}
