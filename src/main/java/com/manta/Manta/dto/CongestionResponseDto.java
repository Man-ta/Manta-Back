package com.manta.Manta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
// 실시간 장소 혼잡도
public class CongestionResponseDto {
    private String poiId;

}
