package com.manta.Manta.controller;

import com.manta.Manta.dto.CongestionResponseDto;
import com.manta.Manta.service.CongestionService;
import com.manta.Manta.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/place")
public class PlaceController {


    private final PlaceService placeService;
    // [데이터 제공 가능 장소] poiId를 이용해 혼잡도 구할 수 있을 것으로 예상
    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService=placeService;
    }


    @GetMapping("/data")
    public ResponseEntity<List<List<String>>> getPlaceInfo() {
        try {
            List<List<String>> placeConList = placeService.placeInfo();
            return new ResponseEntity<>(placeConList, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

