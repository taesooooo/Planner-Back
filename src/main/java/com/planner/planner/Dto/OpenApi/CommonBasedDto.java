package com.planner.planner.Dto.OpenApi;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CommonBasedDto {
	private String sigunguCode; // 시군구코드
	private String tel; // 전화번호
	private String title; // 제목
	private String addr1; // 주소
	private String addr2; // 상세주소
	private String areaCode; // 지역코드
	private String bookTour; // 교과서속여행지 여부
	private String cat1; // 대분류
	private String cat2; // 중분류
	private String cat3; // 소분류
	private String contentId; // 콘텐츠ID
	private String contentTypeId; // 콘텐츠타입ID
	private String createdTime; // 등록일
	private String firstImage; // 대표이미지(원본)
	private String firstImage2; // 대표이미지(썸네일)
	private String mapx; // GPS X좌표
	private String mapy; // GPS Y좌표
	private String mlevel; // Map Level
	private String modifiedTime; // 수정일
	private String zipcode; // 우편번호
}