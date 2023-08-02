package com.manta.Manta.service;

import com.manta.Manta.dto.CongestionResponseDto;
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
public class CongestionService {
    public List<String> placeConInfo(CongestionResponseDto congestionResponseDto) throws IOException {
        List<String> placeConList = new ArrayList<>();

        try {
            String poiId = congestionResponseDto.getPoiId();

            String apiUrl = ("https://apis.openapi.sk.com/puzzle/congestion/rltm/pois/" + poiId);

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

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return placeConList;
    }

}
