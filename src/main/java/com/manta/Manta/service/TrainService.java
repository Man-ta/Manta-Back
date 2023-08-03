package com.manta.Manta.service;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.dto.TrainResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@CrossOrigin(origins = "*") //cors정책 -> 이게 없으면 통신이 안됨.

public class TrainService {
//    @Autowired
//    TrainResponseDto trainResponseDto;
    private final ObjectMapper objectMapper; // Jackson ObjectMapper 주입

    @Autowired
    public TrainService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<String> getTrainInfo(TrainResponseDto trainResponseDto) throws IOException {
        List<String> trainInfoList = new ArrayList<>();

        try {
            String stationCode = trainResponseDto.getStationCode();

            String apiUrl = ("https://apis.openapi.sk.com/puzzle/subway/congestion/stat/car/stations/" + stationCode);
            String dow = trainResponseDto.getDow();
            String hh = trainResponseDto.getHh();

            String parameter ="?dow=" + dow + "&hh=" + hh ;
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
            String subwayLine = jsonNode.path("contents").path("subwayLine").asText();
            String stationName = jsonNode.path("contents").path("stationName").asText();

            // congestionCar 값 추출
            JsonNode statArray = jsonNode.path("contents").path("stat");
            for (JsonNode statNode : statArray) {
                JsonNode data = statNode.path("data");
                for (JsonNode dataNode : data) {
                    JsonNode congestionCarNode = dataNode.path("congestionCar");
                    // congestionCar 값 추출하여 리스트에 추가
                    String congestionCarValue = congestionCarNode.toString();
                    trainInfoList.add(congestionCarValue);
                }
            }

            trainInfoList.add(subwayLine);
            trainInfoList.add(stationName);




        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return trainInfoList;
    }

}
