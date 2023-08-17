package com.manta.Manta.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.api.dto.LostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class LostService {

    private final ObjectMapper objectMapper;

    @Autowired
    public LostService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonNode getLostInfo(LostDto lostDto) throws IOException, InterruptedException {
        //분실물 찾기 서비스
        try {
            String key = lostDto.getKey();
            String type = lostDto.getType();
            String service = lostDto.getService();
            String start_Index = lostDto.getStart_Index();
            String end_Index = lostDto.getEnd_Index();
           // String id = lostDto.getId();
            String date = lostDto.getDate();

            String apiUrl = "http://openapi.seoul.go.kr:8088";
            String parameter = "/" + key + "/" + type + "/" + service
                    + "/" + start_Index + "/" + end_Index + "/" + date;

            String fullUrl = apiUrl + parameter;
            System.out.println("주소 : " + fullUrl);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // JSON 결과 파싱
            JsonNode jsonNode = objectMapper.readTree(response.body());

            return jsonNode;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
