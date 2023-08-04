package com.manta.Manta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceService {
    private final ObjectMapper objectMapper;

    @Autowired
    public PlaceService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<List<String>> placeInfo() throws IOException {
        List<List<String>> placeList = new ArrayList<>();

        try {
            String apiUrl = "https://apis.openapi.sk.com/puzzle/pois";

            URL fullUrl = new URL(apiUrl);
            System.out.println("주소:" + fullUrl);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("appkey", "GIus98D87O1NAVDh5d0iB7BRUTtA7NX77DbSioES")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // JSON 결과 파싱
            JsonNode jsonNode = objectMapper.readTree(response.body());
            System.out.println(response.body());

            // 필요한 데이터 추출
            JsonNode contentsArray = jsonNode.path("contents");
            for (JsonNode content : contentsArray) {
                String poi_Id = content.path("poiId").asText();
                String poiName = content.path("poiName").asText();
                List<String> placeInfo = new ArrayList<>();
                placeInfo.add(poi_Id);
                placeInfo.add(poiName);
                placeList.add(placeInfo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return placeList;
    }
}
