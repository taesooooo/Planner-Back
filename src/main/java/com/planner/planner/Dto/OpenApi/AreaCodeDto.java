package com.planner.planner.Dto.OpenApi;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AreaCodeDto {
	private String rnum;
	private String code;
	private String name;
}
