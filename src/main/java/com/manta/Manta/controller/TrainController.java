package com.manta.Manta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.manta.Manta.service.TrainService;
import com.manta.Manta.repository.TrainRepository;

import java.util.List;
@CrossOrigin(origins = "*") //cors정책 -> 이게 없으면 통신이 안됨.

@RestController
@RequestMapping("/train")

public class TrainController {

    @Autowired
    TrainService trainService;

    @Autowired
    TrainRepository trainRepository;



}
