package com.manta.Manta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.dto.CongestionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "*")
public class PlaceService {
    private final ObjectMapper objectMapper; // Jackson ObjectMapper 주입
    @Autowired
    public PlaceService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<String> placeInfo() throws IOException {
        // [데이터 제공 가능 장소] poiId를 이용해 혼잡도 구할 수 있을 것으로 예상
        //데이터 정상적으로 넘어가지 않아 다시 시도해볼 예정
        List<String> placeList = new ArrayList<>();

        try {

            String apiUrl = ("https://apis.openapi.sk.com/puzzle/pois");

            String fullUrl = apiUrl;
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


            placeList.add(poi_Id);
            placeList.add(poiName);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return placeList;
    }

}
