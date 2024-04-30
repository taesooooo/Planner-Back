package com.planner.planner.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.planner.planner.Common.ValidationGroups.PlannerCreateGroup;
import com.planner.planner.Common.ValidationGroups.PlannerUpdateGroup;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

//@JsonInclude(value = Include.NON_NULL)
@Builder
@Getter
public class PlannerDto {
	private int plannerId;
	private int accountId;
	
	@NotBlank(message = "생성자는 필수입니다.", groups = { PlannerCreateGroup.class } )
	private String creator;
	
	@PositiveOrZero(message = "지역 코드는 음수일 수 없습니다.", groups = { PlannerCreateGroup.class, PlannerUpdateGroup.class })
	private Integer areaCode;
	
	@NotBlank(message = "제목을 입력해주세요.", groups = { PlannerCreateGroup.class, PlannerUpdateGroup.class })
	private String title;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "시작 날짜는 필수 항목입니다.", groups = { PlannerCreateGroup.class, PlannerUpdateGroup.class })
	private LocalDate planDateStart;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "종료 날짜는 필수 항목입니다.", groups = { PlannerCreateGroup.class, PlannerUpdateGroup.class })
	private LocalDate planDateEnd;
	
	private List<String> planMembers;
	private int expense;
	
	@Min(value = 1,  message = "멤버는 0명일 수 없습니다.", groups = { PlannerCreateGroup.class, PlannerUpdateGroup.class })
	private int memberCount;
	
	@Min(value = 1,  message = "멤버 유형이 잘못되었습니다.", groups = { PlannerCreateGroup.class, PlannerUpdateGroup.class })
	@Max(value = 4,  message = "멤버 유형이 잘못되었습니다.", groups = { PlannerCreateGroup.class, PlannerUpdateGroup.class })
	private int memberTypeId;
	private int likeCount;
	private boolean likeState;
	private String thumbnail;
	
	private List<PlanMemoDto> planMemos;
	private List<PlanDto> plans;
	
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	private LocalDateTime updateDate;
	
	@AssertTrue(message = "종료 날짜가 시작 날짜보다 늦을수 없습니다.", groups = { PlannerCreateGroup.class, PlannerUpdateGroup.class })
	private boolean isDateCheck() {
		if(planDateStart == null || planDateEnd == null) {
			return false;
		}
		
		if(planDateEnd.isAfter(planDateStart) || planDateEnd.isEqual(planDateStart)) {
			return true;
		}
		else {
			return false;
		}
	}
}
