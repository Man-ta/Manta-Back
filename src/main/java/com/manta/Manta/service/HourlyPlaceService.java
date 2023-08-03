package com.manta.Manta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.dto.HourlyPlaceReponseDto;
import com.manta.Manta.dto.TrainResponseDto;
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
//사용자가 조회한 장소의 시간대별 혼잡도를 제공
@Service
@CrossOrigin(origins = "*") //cors정책 -> 이게 없으면 통신이 안됨.

public class HourlyPlaceService {
    //    @Autowired
//    TrainResponseDto trainResponseDto;
    private final ObjectMapper objectMapper; // Jackson ObjectMapper 주입

    @Autowired
    public HourlyPlaceService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<String> getHourlyPlaceInfo(HourlyPlaceReponseDto hourlyPlaceReponseDto) throws IOException {
        List<String> HourlyPlaceInfoList = new ArrayList<>();

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
                String congestionValue = rawNode.path("congestion").asText();
                String congestionLevel=rawNode.path("congestionLevel").toString();
                // congestion,congestionLevel 값을 리스트에 추가
                HourlyPlaceInfoList.add(congestionValue);
                HourlyPlaceInfoList.add(congestionLevel);
            }

            HourlyPlaceInfoList.add(poi_Id);
            HourlyPlaceInfoList.add(poiName);




        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return HourlyPlaceInfoList;
    }

}
