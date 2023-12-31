package com.manta.Manta.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.manta.Manta.api.service.LostService;
import com.manta.Manta.api.dto.LostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/place")
public class LostController {
    private final LostService lostService;

    @Autowired
    public LostController(LostService lostService) {
        this.lostService = lostService;
    }

    @GetMapping("/lost")
    public ResponseEntity<JsonNode> getLostInfo(@ModelAttribute LostDto lostDto) {
        try {
            JsonNode lostInfo = lostService.getLostInfo(lostDto);
            return new ResponseEntity<>(lostInfo, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
