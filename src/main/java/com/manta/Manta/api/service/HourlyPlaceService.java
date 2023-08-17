package com.manta.Manta.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.api.dto.HourlyPlaceReponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@CrossOrigin(origins = "*")
public class HourlyPlaceService {
    @Value("${SK-KEY}")
    private String key;
    private final ObjectMapper objectMapper; // Jackson ObjectMapper 주입

    @Autowired
    public HourlyPlaceService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //사용자가 조회한 장소의 시간대별 혼잡도를 제공하는 서비스
    public JsonNode getHourlyPlaceInfo(HourlyPlaceReponseDto hourlyPlaceReponseDto) throws IOException {

        try {
            String poiId = hourlyPlaceReponseDto.getPoiId();

            String apiUrl = ("https://apis.openapi.sk.com/puzzle/congestion/raw/hourly/pois/" + poiId);
            String date = hourlyPlaceReponseDto.getDate();

            String parameter = "?date=" + date;
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



