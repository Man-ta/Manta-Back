package com.manta.Manta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//사용자가 조회한 장소의 시간대별 혼잡도를 제공
@Getter
@Setter
@AllArgsConstructor
@ToString
public class HourlyPlaceReponseDto {
    private String poiId;
    private String date;
}
