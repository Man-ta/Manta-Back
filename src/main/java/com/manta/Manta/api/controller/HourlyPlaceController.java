package com.manta.Manta.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.manta.Manta.api.dto.HourlyPlaceReponseDto;
import com.manta.Manta.api.service.HourlyPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/place")
public class HourlyPlaceController {

    private final HourlyPlaceService hourlyPlaceService;

    @Autowired
    public HourlyPlaceController(HourlyPlaceService hourlyPlaceService) {
        this.hourlyPlaceService = hourlyPlaceService;
    }

    @GetMapping("/hourly")
    public ResponseEntity<JsonNode> getHourlyPlaceInfo(@RequestParam String poiId, @RequestParam String date) {
        //사용자가 조회한 장소의 시간대별 혼잡도를 제공하는 컨트롤러
        try {
            HourlyPlaceReponseDto hourlyPlaceReponseDto = new HourlyPlaceReponseDto(poiId, date);
            JsonNode hourlyPlaceInfo = hourlyPlaceService.getHourlyPlaceInfo(hourlyPlaceReponseDto);
            return new ResponseEntity<>(hourlyPlaceInfo, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

