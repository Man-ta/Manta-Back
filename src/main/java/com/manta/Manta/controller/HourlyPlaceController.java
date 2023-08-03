package com.manta.Manta.controller;

import com.manta.Manta.dto.CongestionResponseDto;
import com.manta.Manta.dto.HourlyPlaceReponseDto;
import com.manta.Manta.service.CongestionService;
import com.manta.Manta.service.HourlyPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

//사용자가 조회한 장소의 시간대별 혼잡도를 제공
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/place")
public class HourlyPlaceController {


    private final HourlyPlaceService hourlyPlaceService;
    @Autowired
    public HourlyPlaceController(HourlyPlaceService hourlyPlaceService) {
        this.hourlyPlaceService=hourlyPlaceService;
    }

    @GetMapping("/hourly")
    public ResponseEntity<List<String>> HourlyPlaceInfo(@RequestParam String poiId,@RequestParam String date) {
        try {


            HourlyPlaceReponseDto hourlyPlaceReponseDto=new HourlyPlaceReponseDto(poiId,date);
            hourlyPlaceReponseDto.setPoiId(poiId);;
            hourlyPlaceReponseDto.setDate(date);

            List<String> hourlyPlaceList = hourlyPlaceService.getHourlyPlaceInfo(hourlyPlaceReponseDto);
            return new ResponseEntity<>(hourlyPlaceList, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
