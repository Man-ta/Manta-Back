package com.manta.Manta.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.manta.Manta.api.dto.PoiDetailDto;
import com.manta.Manta.api.service.PoiDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/place")
public class PoiDetailController {
    //명칭(POI) 상세 정보 검색 컨트롤러
    private final PoiDetailService poiDetailService;
    @Autowired
    public PoiDetailController(PoiDetailService poiDetailService){
        this.poiDetailService=poiDetailService;
    }
    @GetMapping("/detail")
    public ResponseEntity<JsonNode> getPoiDetailInfo(@ModelAttribute PoiDetailDto poiDetailDto) {
        try {
            JsonNode resultList = poiDetailService.getDetailInfo(poiDetailDto);
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
