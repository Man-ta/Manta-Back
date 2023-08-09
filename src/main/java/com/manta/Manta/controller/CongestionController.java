package com.manta.Manta.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.manta.Manta.dto.CongestionResponseDto;
import com.manta.Manta.service.CongestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/place")
public class CongestionController {
    //혼잡도 관련 컨트롤러
    private final CongestionService congestionService;

    @Autowired
    public CongestionController(CongestionService congestionService) {
        this.congestionService=congestionService;
    }


    @GetMapping("/congestion")
    public ResponseEntity<JsonNode> getCongestionInfo(@RequestParam String poiId) {
        // 실시간 장소 혼잡도를 제공
        try {
            CongestionResponseDto congestionResponseDto=new CongestionResponseDto(poiId);
            congestionResponseDto.setPoiId(poiId);

            JsonNode placeConList = congestionService.placeConInfo(congestionResponseDto);
            return new ResponseEntity<>(placeConList, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/Statistical")
    public ResponseEntity<JsonNode> getStatisticalInfo(@RequestParam String poiId) {
        //통계성 혼잡도를 제공
        try {
            CongestionResponseDto congestionResponseDto = new CongestionResponseDto(poiId);
            congestionResponseDto.setPoiId(poiId);

            JsonNode statisticalList = congestionService.StatisticalnInfo(congestionResponseDto);
            return new ResponseEntity<>(statisticalList, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/visitor")
    public ResponseEntity<JsonNode> getVisitorCountInfo(@RequestParam String poiId) {
        //일자별 추정 방문자수를 제공
        try {
            CongestionResponseDto congestionResponseDto = new CongestionResponseDto(poiId);
            congestionResponseDto.setPoiId(poiId);

            JsonNode visitorList = congestionService.getVisitorCountInfo(congestionResponseDto);
            return new ResponseEntity<>(visitorList, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}