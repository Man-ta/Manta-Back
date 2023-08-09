package com.manta.Manta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.dto.HourlyPlaceReponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
                .header("appkey", "GIus98D87O1NAVDh5d0iB7BRUTtA7NX77DbSioES")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        return response.body();
    }

    public JsonNode placeInfo() throws IOException {
        //데이터 제공 가능 장소 서비스
        List<List<String>> placeList = new ArrayList<>();

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

    public JsonNode getHourlyPlaceInfo(HourlyPlaceReponseDto hourlyPlaceReponseDto) throws IOException {
        //실시간 혼잡도 제공 서비스
        List<List<String>> HourlyPlaceInfoList = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("0.00000");

        try {
            String poiId = hourlyPlaceReponseDto.getPoiId();
            String apiUrl = "https://apis.openapi.sk.com/puzzle/congestion/raw/hourly/pois/" + poiId;
            String date = hourlyPlaceReponseDto.getDate();

            String parameter = "?date=" + date;
            String fullUrl = apiUrl + parameter;
            System.out.println("주소:" + fullUrl);

            String responseString = sendGetRequest(fullUrl);
            // JSON 결과 파싱
            JsonNode jsonNode = objectMapper.readTree(responseString);
            return jsonNode;



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
// 필요한 데이터 추출
/*
    JsonNode contentsArray = jsonNode.path("contents");
            for (JsonNode content : contentsArray) {
                    String poi_Id = content.path("poiId").asText();
                    String poiName = content.path("poiName").asText();
                    List<String> placeInfo = new ArrayList<>();
        placeInfo.add(poi_Id);
        placeInfo.add(poiName);
        placeList.add(placeInfo);
        }*/

 /*

    // 필요한 데이터 추출
    String poi_Id = jsonNode.path("contents").path("poiId").asText();
    String poiName = jsonNode.path("contents").path("poiName").asText();

    // congestion,congestionLevel 값 추출
    JsonNode rawArray = jsonNode.path("contents").get("raw");
            for (JsonNode rawNode : rawArray) {
                    double congestionValue = rawNode.path("congestion").asDouble();
                    int congestionLevel = rawNode.path("congestionLevel").asInt();
                    String datetime = rawNode.path("datetime").asText();
                    List<String> placeInfo = new ArrayList<>();
        placeInfo.add(decimalFormat.format(congestionValue));
        placeInfo.add(String.valueOf(congestionLevel));
        placeInfo.add(datetime);
        HourlyPlaceInfoList.add(placeInfo);
        }

        HourlyPlaceInfoList.add(List.of(poi_Id, poiName));*/
