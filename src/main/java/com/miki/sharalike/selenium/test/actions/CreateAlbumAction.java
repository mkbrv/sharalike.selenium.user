package com.miki.sharalike.selenium.test.actions;

import com.miki.sharalike.selenium.test.actions.abstr.AbstractAction;
import com.miki.sharalike.selenium.test.actions.abstr.IAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Created by mcsere on 3/27/2015.
 */
public class CreateAlbumAction extends AbstractAction implements IAction {

    private List<String> pictures;

    public CreateAlbumAction(WebDriver driver, List<String> pictures) {
        super(driver);
        this.pictures = pictures;
    }

    @Override
    protected boolean before() throws InterruptedException {
        Thread.sleep(2500L);
        return true;

    }

    @Override
    protected boolean actualAction() throws InterruptedException {
        this.clickElement("overlay_close_message", 20L);
        driver.findElement(By.className("albums_upload")).click();
        StringBuilder uploadPicture = new StringBuilder();
        Thread.sleep(1000L);
        Long start = System.currentTimeMillis();
        for (int i = 0; i < pictures.size(); i++) {
            uploadPicture.append(pictures.get(i));
            if (i != pictures.size() - 1) {
                uploadPicture.append("\n");
            }
        }
        Thread.sleep(1000L);
        driver.findElement(By.id("newAlbumName")).sendKeys("test");
        driver.findElement(By.name("file[]")).sendKeys(uploadPicture.toString());
        driver.findElement(By.className("upload")).click();
        Thread.sleep(1000L);
        this.clickElement("overlay_close_message", 5 * 1000L);// seconds for uploading pictures timeout
        return true;
    }

    @Override
    protected boolean after() {
        return true;
    }

    @Override
    protected String getActionName() {
        return "CREATE_ALBUM: SIZE: " + pictures.size() + ";";
    }
}
