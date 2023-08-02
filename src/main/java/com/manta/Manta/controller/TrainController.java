package com.manta.Manta.controller;

import com.manta.Manta.dto.TrainResponseDto;
import com.manta.Manta.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*") //cors정책 -> 이게 없으면 통신이 안됨.
@RestController
@RequestMapping("/trains")
public class TrainController {

    private final TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }


    @GetMapping("/congestion")
    public ResponseEntity<List<String>> getTrainInfo(@RequestParam String stationCode,@RequestParam String dow, @RequestParam String hh) {
        try {
            TrainResponseDto trainResponseDto = new TrainResponseDto(stationCode, dow, hh);
            trainResponseDto.setStationCode(stationCode);
            trainResponseDto.setDow(dow);
            trainResponseDto.setHh(hh);

            List<String> trainInfoList = trainService.getTrainInfo(trainResponseDto);
            return new ResponseEntity<>(trainInfoList, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
