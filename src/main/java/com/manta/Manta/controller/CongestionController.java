package com.manta.Manta.controller;

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

        private final CongestionService congestionService;

        @Autowired
        public CongestionController(CongestionService congestionService) {
            this.congestionService=congestionService;
        }


        @GetMapping("/congestion")
        public ResponseEntity<List<String>> getTrainInfo(@RequestParam String poiId) {
            try {
                CongestionResponseDto congestionResponseDto=new CongestionResponseDto(poiId);
                congestionResponseDto.setPoiId(poiId);

                List<String> placeConList = congestionService.placeConInfo(congestionResponseDto);
                return new ResponseEntity<>(placeConList, HttpStatus.OK);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

