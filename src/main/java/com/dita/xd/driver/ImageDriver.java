package com.dita.xd.driver;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static com.dita.xd.util.helper.ImageTransferHelper.downloadImage;
import static com.dita.xd.util.helper.ImageTransferHelper.uploadImage;

public class ImageDriver extends JFrame {
    JLabel lblPreview = new JLabel();

    public ImageDriver() {
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(600, 800));

        JFileChooser chooser = new JFileChooser();
        JButton btnLoad = new JButton("불러오기");

        btnLoad.addActionListener(e -> {
            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();

                lblPreview.setIcon(downloadImage(uploadImage(selectedFile)));
            }
        });
        add(btnLoad, BorderLayout.NORTH);
        add(lblPreview);
    }

    public static void main(String[] args) {
        ImageDriver driver = new ImageDriver();
        driver.setVisible(true);
    }
}
