package com.dita.xd.view.panel.main;

import com.dita.xd.model.FeedBean;
import com.dita.xd.model.MediaBean;
import com.dita.xd.view.base.JImageView;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.stream.Collectors;

public class MediaPanel extends JPanel {
    private final FeedBean feedBean;
    private final Vector<MediaBean> medium;

    public MediaPanel(FeedBean feedBean) {
        this.feedBean = feedBean;
        this.medium = feedBean.getMediaBeans();

        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());

        JPanel mainPane = new JPanel();
        JPanel leftPane = new JPanel();
        JPanel rightPane = new JPanel();
        InnerMediaIndexPanel[] wrapperPane = new InnerMediaIndexPanel[medium.size()];
        JImageView[] views = new JImageView[medium.size()];
        Vector<String> addresses = feedBean.getMediaBeans().stream().map(MediaBean::getContentAddress)
                        .collect(Collectors.toCollection(Vector::new));

        final int[][] widths = new int[][] {
                { 302, -1, -1, -1 }, { 150, 150, -1, -1 },
                { 150, 150, 150, -1 }, { 150, 150, 150, 150 }
        };
        final int[][] heights = new int[][] {
                { 302, -1, -1, -1 }, { 302, 302, -1, -1 },
                { 150, 150, 302, -1 }, { 150, 150, 150, 150 }
        };
        final int s = 3;

        for (int i = 0; i < s; ++i) {
            wrapperPane[i] = new InnerMediaIndexPanel(i);
            views[i] = new JImageView();
            views[i].setPreferredSize(new Dimension(widths[s - 1][i], heights[s - 1][i]));
            views[i].setMaximumSize(new Dimension(widths[s - 1][i], heights[s - 1][i]));
            wrapperPane[i].setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

            try {
                ImageIcon icon = new ImageIcon(new URL(addresses.get(i)));
                int width = icon.getIconWidth();
                int height = icon.getIconHeight();

                if (width > height) {
                    icon = new ImageIcon(icon.getImage()
                            .getScaledInstance(-1, heights[s - 1][i], Image.SCALE_SMOOTH));
                } else {
                    icon = new ImageIcon(icon.getImage()
                            .getScaledInstance(widths[s - 1][i], -1, Image.SCALE_SMOOTH));
                }
                views[i].setIcon(icon);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            wrapperPane[i].add(views[i]);
        }
        switch (s) {
            case 1 -> {
                mainPane.setLayout(new BorderLayout());
                mainPane.add(wrapperPane[0]);
            }
            case 2 -> {
                mainPane.setLayout(new GridLayout(0, 2));
                mainPane.add(wrapperPane[0]);
                mainPane.add(wrapperPane[1]);
            }
            case 3 -> {
                mainPane.setLayout(new GridLayout(0, 2));
                leftPane.setLayout(new GridLayout(2, 0));
                leftPane.add(wrapperPane[0]);
                leftPane.add(wrapperPane[1]);
                mainPane.add(leftPane);
                mainPane.add(wrapperPane[2]);
            }
            case 4 -> {
                mainPane.setLayout(new GridLayout(0, 2));
                leftPane.setLayout(new GridLayout(2, 0));
                rightPane.setLayout(new GridLayout(2, 0));
                leftPane.add(wrapperPane[0], 0);
                leftPane.add(wrapperPane[2], 1);
                rightPane.add(wrapperPane[1], 0);
                rightPane.add(wrapperPane[3], 1);
                mainPane.add(leftPane);
                mainPane.add(rightPane);
            }
        }
        add(mainPane);
    }

    public String getImageAddress(int index) {
        String result = null;

        if (0 <= index && index < medium.size()) {
            result = medium.get(index).getContentAddress();
        }
        return result;
    }

    protected static class InnerMediaIndexPanel extends JPanel {
        private int index;

        public InnerMediaIndexPanel(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }
}
