package com.dita.xd.util.helper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
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
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ImageTransferHelper {
    private static final String HOST = "http://hxlab.co.kr:9001";
    private static final String DOWNLOAD_LINK = HOST + "/photo";
    private static final String UPLOAD_LINK = HOST + "/upload";

    public static ImageIcon downloadImage(String uuid) {
        ImageIcon result = null;

        try {
            URL url = new URL(DOWNLOAD_LINK + "/" + uuid);
            result = new ImageIcon(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }   // -- End of function

    public static String uploadImage(Path path) {
        final String boundary = new BigInteger(256, new Random()).toString();
        final Map<Object, Object> data = new LinkedHashMap<>();
        String result = null;
        data.put("token", "some-token-value");
        data.put("file", path);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(UPLOAD_LINK))
                    .header("Content-Type", "multipart/form-data;boundary=" + boundary)
                    .POST(ofMimeMultipartData(data, boundary))
                    .build();
            String responseBody = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .get();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(responseBody);
            result = ((String) jsonObject.get("filename")).split("\\.")[0];
        } catch (IOException | ExecutionException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }
        return result;
    }   // -- End of function

    public static String uploadImage(File file) {
        return uploadImage(file.toPath());
    }   // -- End of function

    private static HttpRequest.BodyPublisher ofMimeMultipartData(Map<Object, Object> data, String boundary)
            throws IOException {
        final byte[] separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=")
                .getBytes(StandardCharsets.UTF_8);
        List<byte[]> byteArrays = new ArrayList<>();

        /* Iterating over data entries */
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            /* Opening boundary */
            byteArrays.add(separator);

            // If value is type of Path (file) append content type with file name and file binaries,
            // otherwise simply append key=value
            if (entry.getValue() instanceof Path path) {
                String mimeType = Files.probeContentType(path);

                byteArrays.add(("\"" + entry.getKey() + "\"; filename=\"" + path.getFileName()
                        + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue() + "\r\n")
                        .getBytes(StandardCharsets.UTF_8));
            }   // End of if-else
        }   // -- End of for-loop
        // Closing boundary
        byteArrays.add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));

        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }   // -- End of function
}
