package com.manta.Manta.api.controller;

import com.fasterxml.jackson.databind.JsonNode;

import com.manta.Manta.api.dto.TransitResponseDto;
import com.manta.Manta.api.service.TransitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*") //cors정책 -> 이게 없으면 통신이 안됨.
@RestController
@RequestMapping("/transit")
public class TransitController {

    private final TransitService transitService;

    @Autowired
    public TransitController(TransitService transitService) {
        this.transitService = transitService;
    }

    @PostMapping("/route")
    public ResponseEntity<JsonNode> transitInfo(@RequestBody TransitResponseDto transitResponseDto) {
        try {
            JsonNode transitInfo = transitService.transitInfo(transitResponseDto);
            return ResponseEntity.ok().body(transitInfo);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
