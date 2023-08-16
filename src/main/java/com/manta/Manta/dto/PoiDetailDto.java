package com.manta.Manta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PoiDetailDto {
   // 명칭(POI) 상세 정보 검색 DTO

    private String poiInfo; //POI ID 또는 POI 식별자(pkey)
    private int version;
    private String findOption; //id : POI ID로 조회하는 경우(기본값)
    private String resCoordType;
    //응답 좌표계 유형
    //- WGS84GEO: 경위도
    //- EPSG3857: Google Mercator
    //- KATECH: TM128(Transverse Mercator, 횡메카토르), 한국 표준
}
