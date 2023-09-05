package com.dita.xd.driver;

import com.dita.xd.controller.ActivityController;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.UserBean;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Vector;

public class ActivityDriver {
    public static void main(String[] args) {
        final ActivityController controller = new ActivityController();
        String text = "@123";

        Vector<Object> vec = Optional.ofNullable(controller.search(text)).orElse(new Vector<>());

        if (vec.size() != 0) {
            if (vec.firstElement() instanceof FeedBean) {
                // contentSearchPanel.setFeed(vec);
                // mgr.show("content");
            } else if (vec.firstElement() instanceof UserBean) {
                // userSearchPanel.setUser(vec);
                // mgr.show("user");
            }
        }
    }
}
