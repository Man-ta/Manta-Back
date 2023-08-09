package com.manta.Manta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PoiSearchDto {
    //장소(POI) 통합 검색 DTO
    private String version;// 현재 버전 1을 지원
    private String searchKeyword;//시설물명, 상호, 시설 유형, 주소, 전화번호를 검색어로 지정합니다. 장소 통합 검색 API는 지정한 검색어와 일치하는 항목을 결과로 제공
    private String searchType;//장소 통합 검색 API는 all(통합)
    private String areaLLCode;//지역 대분류 코드를 지정
    //private String areaLMCode;//지역 중분류 코드를 지정
    private String searchtypCd;//검색 결과 정렬 순서를 정확도순 또는 거리순으로 지정
    private float centerLon;//반경 검색에서 사용하는 중심 경도를 지정
    private float centerLat;//반경 검색에서 사용하는 중심 위도(centerLat)를 지정
    private String reqCoordType;//요청 좌표계를 지정
    private String resCoordType;//응답 좌표계를 지정
    private int radius;//검색 반경(radius)을 지정
    private int page; //검색 결과 페이지 번호를 지정
    private int count; //페이지당 검색 결과 수
    private String multiPoint;//모든 결괏값을 반환
    private String poiGroupYn;// 관심 장소(POI)의 부속 시설물 정보 미반환
    //private String callback;//콜백(callback) 함수명 정보를 지정
}
