package com.my.retail.myretailapi.businesslogic.impl;

import com.my.retail.myretailapi.businesslogic.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionImpl implements Connection {

    public String getResponseFromURL(String targetURL, long id) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(targetURL + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        br.close();
        return result.toString();
    }
}
