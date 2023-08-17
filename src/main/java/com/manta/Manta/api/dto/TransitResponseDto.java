package com.manta.Manta.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransitResponseDto {
    private String startX;
    private String startY;
    private String endX;
    private String endY;
    private String searchDttm;
}
