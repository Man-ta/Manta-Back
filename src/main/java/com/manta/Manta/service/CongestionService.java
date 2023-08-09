package com.manta.Manta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.dto.CongestionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@CrossOrigin(origins = "*")
public class CongestionService {
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
                .header("appkey", "GIus98D87O1NAVDh5d0iB7BRUTtA7NX77DbSioES")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        return response.body();
    }


    public JsonNode placeConInfo(CongestionResponseDto congestionResponseDto) throws IOException {
        // 실시간 장소 혼잡도를 제공하는 서비스
        List<String> placeConList = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("0.00000");


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


    public JsonNode StatisticalnInfo(CongestionResponseDto congestionResponseDto) throws IOException {
        // 통계성 장소 혼잡도를 제공하는 서비스
        List<List<String>> StatisticalnList = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("0.00000");

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
        List<List<String>> visitorList = new ArrayList<>();
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
   /* String poi_Id = jsonNode.path("contents").path("poiId").asText();
    String poiName = jsonNode.path("contents").path("poiName").asText();

    JsonNode statArray = jsonNode.path("contents").path("stat");
            for (JsonNode statNode : statArray) {
                    double congestionValue = statNode.path("congestion").asDouble();
                    String congestionLevel = statNode.path("congestionLevel").toString();
                    String dow = statNode.path("dow").toString().replaceAll("\"", "");
                    String hh = statNode.path("hh").toString().replaceAll("\"", "");

                    List<String> congestionData = new ArrayList<>();
        congestionData.add(decimalFormat.format(congestionValue));
        congestionData.add(congestionLevel);
        congestionData.add(dow);
        congestionData.add(hh);

        StatisticalnList.add(congestionData);
        }

        List<String> poiInfo = new ArrayList<>();
        poiInfo.add(poi_Id);
        poiInfo.add(poiName);
        StatisticalnList.add(poiInfo);
*/

 /*   // 필요한 데이터 추출
    String poi_Id = jsonNode.path("contents").path("poiId").asText();
    String poiName = jsonNode.path("contents").path("poiName").asText();

    JsonNode rawArray = jsonNode.path("contents").path("raw");
            if (rawArray != null) {
                    for (JsonNode rawNode : rawArray) {
                    String approxVisitorCount = rawNode.path("approxVisitorCount").toString();
                    String date = rawNode.path("date").asText();

                    List<String> visitorInfo = new ArrayList<>();
        visitorInfo.add(String.valueOf(approxVisitorCount));
        visitorInfo.add(date);

        visitorList.add(visitorInfo);
        }
        }

        // poiId와 poiName을 마지막으로 추가
        List<String> poiInfo = new ArrayList<>();
        poiInfo.add(poi_Id);
        poiInfo.add(poiName);
        visitorList.add(poiInfo);*/