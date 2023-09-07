package com.dita.xd.driver;

import com.dita.xd.controller.FeedController;
import com.dita.xd.model.FeedBean;
import com.dita.xd.view.panel.main.MediaPanel;

import javax.swing.*;
import java.awt.*;

public class MediaDriver extends JFrame {
    FeedController controller = new FeedController();

    public MediaDriver() {
        setSize(304, 304);
        setLayout(new BorderLayout());

        FeedBean bean = controller.getFeed(41);

        MediaPanel pane = new MediaPanel(bean);


        add(pane);

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        MediaDriver driver = new MediaDriver();

        driver.setVisible(true);
    }
}
