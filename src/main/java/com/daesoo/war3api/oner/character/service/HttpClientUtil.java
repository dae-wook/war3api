package com.daesoo.war3api.oner.character.service;

import com.daesoo.war3api.oner.character.model.ObjectData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class HttpClientUtil {
    public List<ObjectData> sendPostRequest(String nickname) throws IOException, JSONException {
        String url = "https://Third.M16Tool.xyz/api/ThirdPartySupport/ObjectCharacterList";
        String requestBody = "{\"UserId\":\"" + nickname + "\"}";
//        String requestBody = "{\"UserId\":\"Agumon\"}";

        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

        // Set the request method to POST
        connection.setRequestMethod("POST");

        // Set the request headers
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Key", "64b66522526350336b7ab7b8");

        // Enable output (POST data) for the connection
        connection.setDoOutput(true);

        // Write the request body to the connection
        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }

        // Get the response code from the connection
        int responseCode = connection.getResponseCode();

        // Read the response from the connection
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Process the response
            String responseBody = response.toString();
//            System.out.println("Response Code: " + responseCode);
//            System.out.println("Response Body: " + responseBody);

            List<ObjectData> objectList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray dataArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                String objectCode = dataObject.getString("ObjectCode");
                String saveDate = dataObject.getString("UpdateDate");
                objectList.add(new ObjectData(objectCode, saveDate));
            }

            return objectList;
//            return responseBody;
    } finally {
            // Close the connection
            connection.disconnect();
        }
    }
}
