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

public class ImageDriver extends JFrame {
    public ImageDriver() {
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setSize(new Dimension(600, 800));

        JFileChooser chooser = new JFileChooser();
        JButton btnLoad = new JButton("불러오기");
        JLabel lblPreview = new JLabel();

        btnLoad.addActionListener(e -> {
            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();

                final String fileServer = "http://hxlab.co.kr:9001/upload";

                HttpClient client = HttpClient.newHttpClient();

                try {
                    Map<Object, Object> data = new LinkedHashMap<>();
                    data.put("token", "some-token-value");
                    data.put("file", selectedFile.toPath());

                    // add extra parameters if needed

                    // Random 256 length string is used as multipart boundary
                    String boundary = new BigInteger(256, new Random()).toString();

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(fileServer))
                            .header("Content-Type", "multipart/form-data;boundary=" + boundary)
                            .POST(ofMimeMultipartData(data, boundary))
                            .build();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());

                    System.out.println(response.body());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(btnLoad, BorderLayout.NORTH);
        add(lblPreview);

        JLabel lblThumbnail = null;
        try {
            URL url = new URL("http://hxlab.co.kr:9001/photo/88247569-fa9c-4837-98b3-3ba0a92f69f6");
            lblThumbnail = new JLabel(new ImageIcon(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (lblThumbnail != null) {
            add(lblThumbnail);
        }
    }

    public static void main(String[] args) {
        ImageDriver driver = new ImageDriver();
        driver.setVisible(true);
    }

    public static HttpRequest.BodyPublisher ofMimeMultipartData(Map<Object, Object> data,
                                                                String boundary) throws IOException {
        // Result request body
        java.util.List<byte[]> byteArrays = new ArrayList<>();

        // Separator with boundary
        byte[] separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=").getBytes(StandardCharsets.UTF_8);

        // Iterating over data parts
        for (Map.Entry<Object, Object> entry : data.entrySet()) {

            // Opening boundary
            byteArrays.add(separator);

            // If value is type of Path (file) append content type with file name and file binaries, otherwise simply append key=value
            if (entry.getValue() instanceof Path path) {
                String mimeType = Files.probeContentType(path);
                byteArrays.add(("\"" + entry.getKey() + "\"; filename=\"" + path.getFileName()
                        + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue() + "\r\n")
                        .getBytes(StandardCharsets.UTF_8));
            }
        }

        // Closing boundary
        byteArrays.add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));

        // Serializing as byte array
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }
}
