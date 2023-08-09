package com.manta.Manta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PlaceService {
    private final ObjectMapper objectMapper;

    @Autowired
    public PlaceService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private String sendGetRequest(String fullUrl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("appkey", "2g1pkfbjAB3LXPV8ymxV87iexe1q2KZbzmqgnbIf")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        return response.body();
    }

    public JsonNode placeInfo() throws IOException {
        //데이터 제공 가능 장소 서비스

        try {
            String apiUrl = "https://apis.openapi.sk.com/puzzle/pois";
            String responseString = sendGetRequest(apiUrl);

            // JSON 결과 파싱
            JsonNode jsonNode = objectMapper.readTree(responseString);
            System.out.println(responseString);
            return jsonNode;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}
