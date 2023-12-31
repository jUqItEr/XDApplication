package com.dita.xd.driver;

import com.dita.xd.controller.ActivityController;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.UserBean;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

public class ActivityDriver {
    public static void main(String[] args) {
        final ActivityController controller = new ActivityController();
        String text = "@123";

        // Vector<Object> vec = Optional.ofNullable(controller.search(text)).orElse(new Vector<>());

        Vector<Object> vec = controller.search(text);

        if (vec != null) {
            if (vec.firstElement() instanceof FeedBean) {
                // contentSearchPanel.setFeed(vec.stream().map(bean -> (FeedBean) bean)
                //        .collect(Collectors.toCollection(Vector::new)));
                // mgr.show("content");
            } else if (vec.firstElement() instanceof UserBean) {
                // userSearchPanel.setUser(vec);
                // mgr.show("user");
            }
        }
    }
}
