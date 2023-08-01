package com.manta.Manta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manta.Manta.dto.TrainResponseDto;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainService {

    public List<String> train(TrainResponseDto trainResponseDto) throws IOException {
        try {
            URL url = new URL("https://apis.openapi.sk.com/puzzle/subway/congestion/stat/car/stations/{stationCode}");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();   //클라이언트가 전봇대1이고 카카오페이서버가 전봇대2면 이건 그 두개를 연결 시켜주는 전깃줄.(서버 연결)
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("appkey", "Glus98D8701NAVDh5d0iB7BRUTtA7NX77DbSioES");
            httpURLConnection.setRequestProperty("Content-type",  "application/json");
            httpURLConnection.setDoOutput(true);    //서버에게 전해줄 것의 유무, input은 기본값이 true라 안해줘도됨.

            String subwayLine = trainResponseDto.getSubwayLine();
            String stationName = trainResponseDto.getStationName();
            String stationCode = trainResponseDto.getStationCode(); // required
            String startStationCode = trainResponseDto.getStartStationCode();
            String startStationName = trainResponseDto.getStartStationName();
            String endStationCode = trainResponseDto.getEndStationCode();
            String endStationName = trainResponseDto.getEndStationName();
            String prevStationCode = trainResponseDto.getPrevStationCode();
            String prevStationName = trainResponseDto.getPrevStationName();
            String dow = trainResponseDto.getDow();
            String hh = trainResponseDto.getHh();
            String mm = trainResponseDto.getMm();

            String parameter = stationCode + "&" +
                    "dow=" + dow + "&" + "hh=" + hh;
//
            OutputStream outputStream = httpURLConnection.getOutputStream();        //줄 애
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream); //주는 애
            dataOutputStream.writeBytes(parameter);
            dataOutputStream.close();

            int result = httpURLConnection.getResponseCode();

            InputStream inputStream;    //받는 애
            if (result == 200) {
                inputStream = httpURLConnection.getInputStream();
            } else {
                inputStream = httpURLConnection.getErrorStream();
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);   //읽는애
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);      //형변환하는애


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }}