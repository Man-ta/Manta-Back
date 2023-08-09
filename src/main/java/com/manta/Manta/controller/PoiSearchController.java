package com.manta.Manta.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.manta.Manta.dto.PoiSearchDto;
import com.manta.Manta.dto.TrainResponseDto;
import com.manta.Manta.service.PlaceService;
import com.manta.Manta.service.PoiSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/place")
public class PoiSearchController {
    //장소(POI) 통합 검색 컨트롤러
    private final PoiSearchService poiSearchService;
    @Autowired
    public PoiSearchController(PoiSearchService poiSearchService){
        this.poiSearchService=poiSearchService;
    }
    @GetMapping("/search")
    public ResponseEntity<JsonNode> getPoiInfo(@ModelAttribute PoiSearchDto poiSearchDto) {
        try {
            JsonNode resultList = poiSearchService.getPoiInfo(poiSearchDto);
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
