package com.manta.Manta.service;

import com.manta.Manta.dto.TrainResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@CrossOrigin(origins = "*") //cors정책 -> 이게 없으면 통신이 안됨.

public class TrainService {

    public List<String> getTrainInfo(TrainResponseDto trainResponseDto) throws IOException {
        List<String> trainInfoList = new ArrayList<>();

        try {
            String stationCode = trainResponseDto.getStationCode();

            String apiUrl = ("https://apis.openapi.sk.com/puzzle/subway/congestion/stat/car/stations/" + stationCode);
            String dow = trainResponseDto.getDow();
            System.out.println("dow :" + dow);

            String hh = trainResponseDto.getHh();

            String parameter ="?dow=" + dow + "&hh=" + hh;
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

            // HttpURLConnection 라이브러리
//            System.out.println("마지막 주소" + url);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("GET");
//            httpURLConnection.setRequestProperty("Content-type", "application/json");
//            httpURLConnection.setRequestProperty("appkey", "GIus98D87O1NAVDh5d0iB7BRUTtA7NX77DbSioES");
//
//            httpURLConnection.setDoOutput(true);
//
//            OutputStream outputStream = httpURLConnection.getOutputStream();
//            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
//            dataOutputStream.writeBytes(parameter);
//            dataOutputStream.close();
//
//            System.out.println("주소:" + parameter);
//
//
//            int responseCode = httpURLConnection.getResponseCode();
//
//            InputStream inputStream;
//            if (responseCode == 200) {
//                inputStream = httpURLConnection.getInputStream();
//            } else {
//                inputStream = httpURLConnection.getErrorStream();
//            }
//
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            StringBuilder response = new StringBuilder();
//
//
//            while ((line = bufferedReader.readLine()) != null) {
//                response.append(line);
//            }
//            bufferedReader.close();
//
//            System.out.println("JSON 응답 결과:");
//            System.out.println(response.toString());
//
//            trainInfoList.add(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return trainInfoList;
    }

}
