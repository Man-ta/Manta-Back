package com.manta.Manta.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.api.dto.CongestionResponseDto;
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
public class CongestionService {
    @Value("${SK-KEY}")
    private String key;

    private final ObjectMapper objectMapper; // Jackson ObjectMapper 주입

    @Autowired
    public CongestionService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    //sendGetRequest 선언
    private String sendGetRequest(String fullUrl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("appkey", key)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        return response.body();
    }

    public JsonNode placeConInfo(CongestionResponseDto congestionResponseDto) throws IOException {
        // 실시간 장소 혼잡도를 제공하는 서비스
        try {
            String poiId = congestionResponseDto.getPoiId();
            String apiUrl = "https://apis.openapi.sk.com/puzzle/congestion/rltm/pois/" + poiId;

            String responseString = sendGetRequest(apiUrl);

            JsonNode jsonNode = objectMapper.readTree(responseString);
            return jsonNode;
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public JsonNode getStatisticalnInfo(CongestionResponseDto congestionResponseDto) throws IOException {
        // 통계성 장소 혼잡도를 제공하는 서비스
        try {
            String poiId = congestionResponseDto.getPoiId();
            String apiUrl = "https://apis.openapi.sk.com/puzzle/congestion/stat/hourly/pois/" + poiId;

            String responseString = sendGetRequest(apiUrl);

            JsonNode jsonNode = objectMapper.readTree(responseString);
            return jsonNode;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public JsonNode getVisitorCountInfo(CongestionResponseDto congestionResponseDto) throws IOException {
        // 일자별 추정 방문자수를 제공하는 서비스
        try {
            String poiId = congestionResponseDto.getPoiId();
            String apiUrl = "https://apis.openapi.sk.com/puzzle/visitor-count/raw/daily/pois/" + poiId;

            String responseString = sendGetRequest(apiUrl);

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
