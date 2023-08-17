package com.manta.Manta.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class LostDto {
    private String key;
    private String type;
    private String service;
    private String start_Index;
    private String end_Index;
    private String id;
    private String date;



}
