package com.manta.Manta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.Manta.dto.PoiSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class PoiSearchService {
    @Value("${SK-KEY}")
    private String key;
    private final ObjectMapper objectMapper;

    @Autowired
    public PoiSearchService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonNode getPoiInfo(PoiSearchDto poiSearchDto) throws IOException {
        //장소(POI) 통합 검색 서비스

        try {
            String version = poiSearchDto.getVersion();
            String searchKeyword = poiSearchDto.getSearchKeyword();
            String searchType = poiSearchDto.getSearchType();
            String areaLLCode = poiSearchDto.getAreaLLCode();
            String searchtypCd = poiSearchDto.getSearchtypCd();
            float centerLon = poiSearchDto.getCenterLon();
            float centerLat = poiSearchDto.getCenterLat();
            String reqCoordType = poiSearchDto.getReqCoordType();
            String resCoordType = poiSearchDto.getResCoordType();
            int radius = poiSearchDto.getRadius();
            int page = poiSearchDto.getPage();
            int count = poiSearchDto.getCount();
            String multiPoint = poiSearchDto.getMultiPoint();
            String poiGroupYn = poiSearchDto.getPoiGroupYn();

            String apiUrl = "https://apis.openapi.sk.com/tmap/pois";
            String parameter = "?version=" + version + "&searchKeyword=" + searchKeyword +
                    "&searchType=" + searchType + "&areaLLCode=" + areaLLCode + "&searchtypCd=" + searchtypCd +
                    "&centerLon=" + centerLon + "&centerLat=" + centerLat + "&reqCoordType=" + reqCoordType +
                    "&resCoordType=" + resCoordType + "&radius=" + radius + "&page=" + page +
                    "&count=" + count + "&multiPoint=" + multiPoint + "&poiGroupYn=" + poiGroupYn;
            String fullUrl = apiUrl + parameter;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("appkey", key)
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