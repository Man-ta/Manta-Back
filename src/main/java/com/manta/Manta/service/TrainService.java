package com.manta.Manta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.dto.TrainResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@CrossOrigin(origins = "*") //cors정책 -> 이게 없으면 통신이 안됨.

public class TrainService {
    @Value("${SK-KEY}")
    private String key;

    private final ObjectMapper objectMapper; // Jackson ObjectMapper 주입

    @Autowired
    public TrainService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonNode getTrainInfo(TrainResponseDto trainResponseDto) throws IOException {

        try {
            String stationCode = trainResponseDto.getStationCode();

            String apiUrl = ("https://apis.openapi.sk.com/puzzle/subway/congestion/stat/car/stations/" + stationCode);
            String dow = trainResponseDto.getDow();
            String hh = trainResponseDto.getHh();

            String parameter ="?dow=" + dow + "&hh=" + hh ;
            String fullUrl = apiUrl + parameter;
            System.out.println("주소:" + fullUrl);

            //HttpRequest 라이브러리
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("appkey", key)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());


            // JSON 결과 파싱
            JsonNode jsonNode = objectMapper.readTree(response.body());

            return jsonNode;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}