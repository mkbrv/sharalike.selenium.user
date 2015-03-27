package com.miki.sharalike.selenium.test;

import com.miki.sharalike.selenium.test.actions.CreateAlbumAction;
import com.miki.sharalike.selenium.test.actions.CreateSmartShowAction;
import com.miki.sharalike.selenium.test.actions.abstr.IAction;
import com.miki.sharalike.selenium.test.actions.UserRegistrationAction;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcsere on 3/25/2015.
 */
public class UserActions {

    private final Logger LOG = Logger.getLogger(UserActions.class);
    private final static List<IAction> userActions;

    static {
        userActions = new ArrayList<IAction>();
    }


    public UserActions(WebDriver webDriver, String user, String password, List<String> picturesList) {
        userActions.add(new UserRegistrationAction(webDriver, user, password));
        userActions.add(new CreateAlbumAction(webDriver, picturesList));
        userActions.add(new CreateSmartShowAction(webDriver));
    }

    public void start() {
        try {
            for (IAction action : userActions) {
                action.doAction();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
