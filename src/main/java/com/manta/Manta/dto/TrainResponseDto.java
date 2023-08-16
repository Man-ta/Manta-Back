package com.manta.Manta.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor //모든 생성자 만들어줌.
@ToString
//프론트로부터 다음과 같은 데이터를 받아서 controller로 전달하는 객체
public class TrainResponseDto {
//    private String subwayLine;
//    private String stationName;
    private String stationCode;
////    private Arrays stat;
//    private String startStationCode; //시작역 코드
//    private String startStationName;
//    private String endStationCode; //종착역 코드
//    private String endStationName;
//    private String prevStationCode;
//    private String prevStationName;
    private String dow; //검색 기준 요일
    private String hh; //검색 기준 시간
    //private String mm; //검색 기준 분 (10분단위)

}
