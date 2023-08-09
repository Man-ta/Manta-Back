package com.manta.Manta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.dto.PoiDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PoiDetailService {
    private final ObjectMapper objectMapper;

    @Autowired
    public PoiDetailService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonNode getDetailInfo(PoiDetailDto poiDetailDto) throws IOException {
        //명칭(POI) 상세 정보 검색 서비스
        String poiInfo=poiDetailDto.getPoiInfo();
        int version= poiDetailDto.getVersion();
        String findOption=poiDetailDto.getFindOption();
        String resCoordType= poiDetailDto.getResCoordType();

        try {


            String apiUrl = "https://apis.openapi.sk.com/tmap/pois/"+poiInfo;
            //'https://apis.openapi.sk.com/tmap/pois/1128603?version=1&findOption=id&resCoordType=WGS84GEO
            String parameter = "?version=" + version + "&findOption=" + findOption +
                    "&resCoordType=" + resCoordType;
            String fullUrl = apiUrl + parameter;


            URL url = new URL(fullUrl);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("appkey", "GIus98D87O1NAVDh5d0iB7BRUTtA7NX77DbSioES")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode jsonNode = objectMapper.readTree(response.body());
            System.out.println(response.body());
            return jsonNode;


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}