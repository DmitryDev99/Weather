package com.example.weather.core.newtwork;

import androidx.annotation.NonNull;

import com.example.weather.core.newtwork.callbacks.ProgressDownloadListener;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class NetworkProtocol {
    private final String baseUrl;
    public NetworkProtocol(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String syncGet() throws IOException {
        return syncGet(baseUrl);
    }

    public String syncGet(@NotNull String urlSource) throws IOException {
        URL url = new URL(urlSource);
        HttpURLConnection connection = createConnection(url);
        return readResponse(connection);
    }

    public File syncGetFile(
            @NotNull File fileDir,
            @NotNull String fileName,
            ProgressDownloadListener listener
    ) throws IOException {
        return syncGetFile(baseUrl, fileDir, fileName, listener);
    }

    public File syncGetFile(
            @NotNull String urlSource,
            @NotNull File fileDir,
            @NotNull String fileName,
            ProgressDownloadListener listener
    ) throws IOException {
        URL url = new URL(urlSource);
        HttpURLConnection connection = createConnection(url);
        return writeResponseToFile(fileDir, fileName, connection, listener);
    }
    @NonNull
    private String readResponse(@NotNull HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    @NonNull
    private File writeResponseToFile(
        @NotNull File fileDir,
        @NotNull String fileName,
        @NotNull HttpURLConnection connection,
        ProgressDownloadListener listener
    ) throws IOException {
        connection.connect();
        try {
            File newFile = new File(fileDir, fileName + "." +getContentType(connection.getContentType()));
            FileOutputStream fileStream = new FileOutputStream(newFile);
            InputStream inputStream = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int bufferLength;
            long total = 0;
            int fileLength = connection.getContentLength();
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                total += bufferLength;
                if (fileLength > 0) {
                    listener.onProgress((int) total * 100 / fileLength);
                }
                fileStream.write(buffer, 0, bufferLength);
            }
            fileStream.flush();
            fileStream.close();
            return newFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    private HttpURLConnection createConnection(@NonNull URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(EnabledTypesRequest.GET.name());
        connection.setConnectTimeout(DEFAULT_TIME_OUT);
        connection.setReadTimeout(DEFAULT_TIME_OUT);
        return connection;
    }

    @NonNull
    private String getContentType(String typeHeader) {
        if (typeHeader == null) {
            return "";
        }
        int startDividerIndex = typeHeader.indexOf('/') + 1;
        if (startDividerIndex > 0) {
            return typeHeader.substring(startDividerIndex);
        }
        return "";
    }

    private enum EnabledTypesRequest {
        GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE
    }
    private static final int DEFAULT_TIME_OUT = 30_000;
}
