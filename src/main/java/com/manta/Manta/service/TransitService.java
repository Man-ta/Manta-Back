package com.manta.Manta.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.dto.TransitResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@CrossOrigin(origins = "*") //cors정책 -> 이게 없으면 통신이 안됨.
public class TransitService {
    private final ObjectMapper objectMapper; // Jackson ObjectMapper 주입

    @Autowired
    public TransitService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonNode transitInfo(TransitResponseDto transitResponseDto) throws IOException {

        try {
            String startX = transitResponseDto.getStartX();
            String startY = transitResponseDto.getStartY();
            String endX = transitResponseDto.getEndX();
            String endY = transitResponseDto.getEndY();
            String searchDttm = transitResponseDto.getSearchDttm();

            String requestBody = String.format(
                    "{\"startX\":\"%s\",\"startY\":\"%s\",\"endX\":\"%s\",\"endY\":\"%s\",\"lang\":0,\"format\":\"json\",\"count\":10,\"searchDttm\":\"%s\"}",
                    startX, startY, endX, endY, searchDttm);


            //HttpRequest 라이브러리
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://apis.openapi.sk.com/transit/routes"))
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .header("appKey", "GIus98D87O1NAVDh5d0iB7BRUTtA7NX77DbSioES")
                    .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
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
