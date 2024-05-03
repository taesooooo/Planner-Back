package com.planner.planner.Dto.OpenApi;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CommonDetailDto extends CommonBasedDto{
	private String homepage; // 홈페이지주소
	private String telname; // 전화번호명
	private String zipcode; // 우편번호
	private String overview; // 개요
}
