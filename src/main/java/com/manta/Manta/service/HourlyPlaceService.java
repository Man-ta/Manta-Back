package com.manta.Manta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.dto.HourlyPlaceReponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@CrossOrigin(origins = "*")
public class HourlyPlaceService {
    private final ObjectMapper objectMapper; // Jackson ObjectMapper 주입
    @Autowired
    public HourlyPlaceService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<List<String>> getHourlyPlaceInfo(HourlyPlaceReponseDto hourlyPlaceReponseDto) throws IOException {
        List<List<String>> HourlyPlaceInfoList = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("0.00000");

        try {
            String poiId = hourlyPlaceReponseDto.getPoiId();

            String apiUrl = ("https://apis.openapi.sk.com/puzzle/congestion/raw/hourly/pois/" + poiId);
            String date = hourlyPlaceReponseDto.getDate();

            String parameter ="?date=" + date;
            String fullUrl = apiUrl + parameter;
            System.out.println("주소:" + fullUrl);

            URL url = new URL(fullUrl);
            //HttpRequest 라이브러리
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("appkey", "GIus98D87O1NAVDh5d0iB7BRUTtA7NX77DbSioES")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            // JSON 결과 파싱
            JsonNode jsonNode = objectMapper.readTree(response.body());

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

            HourlyPlaceInfoList.add(List.of(poi_Id, poiName));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return HourlyPlaceInfoList;
    }
}
