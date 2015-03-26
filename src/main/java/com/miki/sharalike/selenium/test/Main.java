package com.miki.sharalike.selenium.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by mcsere on 3/25/2015.
 */
public class Main {

    private static List<String> imageList = new ArrayList<String>();
    private static Properties prop = new Properties();

    public static void main(String[] args) throws Exception {
        int howManyTimesToRun = 1;
        if (args.length > 0) {
            try {
                howManyTimesToRun = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                howManyTimesToRun = 1;
            }
        }

        for (int i = 0; i < howManyTimesToRun; i++) {
            System.out.println("STARTING SERIES NUMBER : " + i);
            readConfig();
            start();
        }
        System.exit(0);
    }

    private static void readConfig() throws Exception {
        FileReader fileReader = new FileReader("images.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        imageList.clear();
        while ((line = bufferedReader.readLine()) != null) {
            imageList.add(line);
        }
        bufferedReader.close();
        fileReader.close();

        InputStream input = new FileInputStream("config.properties");
        // load a properties file
        prop.load(input);
        System.setProperty("webdriver.chrome.driver", prop.getProperty("web.driver.location"));
    }

    public static UserActions generateUser(WebDriver driver) {
        return new UserActions(driver, "miki" + System.currentTimeMillis() + new Random().nextInt(100) + "@miki.com", "secret");
    }

    public static void start() throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        try {
            // And now use this to visit Google
            driver.get(String.valueOf(prop.get("target.server")));
            UserActions userActions = generateUser(driver);
            userActions.register();
            userActions.createFirstAlbum(imageList);
            userActions.createFirstSmartShow();
            Thread.sleep(50000L);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

}
