package com.planner.planner.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.planner.planner.Common.ValidationGroups.PlannerCreateGroup;
import com.planner.planner.Common.ValidationGroups.PlannerUpdateGroup;

@JsonInclude(value = Include.NON_NULL)
public class PlannerDto {
	private int plannerId;
	private int accountId;
	
	@NotBlank(message = "생성자는 필수입니다.", groups = { PlannerCreateGroup.class } )
	private String creator;
	
	@PositiveOrZero(message = "지역코드는 숫자이어야 합니다.", groups = { PlannerCreateGroup.class, PlannerUpdateGroup.class })
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
	

	public static class Builder {
		private int plannerId;
		private int accountId;
		private Integer areaCode;
		private String creator;
		private String title;
		private LocalDate planDateStart;
		private LocalDate planDateEnd;
		private List<String> planMembers;
		private int expense;
		private int memberCount;
		private int memberTypeId;
		private int likeCount;
		private boolean likeState;
		private String thumbnail;
		private List<PlanMemoDto> planMemos;
		private List<PlanDto> plans;
		private LocalDateTime createDate;
		private LocalDateTime updateDate;
		
		public Builder setPlannerId(int plannerId) {
			this.plannerId = plannerId;
			return this;
		}

		public Builder setAccountId(int accountId) {
			this.accountId = accountId;
			return this;
		}

		public Builder setAreaCode(Integer areaCode) {
			this.areaCode = areaCode;
			return this;
		}

		public Builder setCreator(String creator) {
			this.creator = creator;
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setPlanDateStart(LocalDate planDateStart) {
			this.planDateStart = planDateStart;
			return this;
		}

		public Builder setPlanDateEnd(LocalDate planDateEnd) {
			this.planDateEnd = planDateEnd;
			return this;
		}

		public Builder setExpense(int expense) {
			this.expense = expense;
			return this;
		}

		public Builder setMemberCount(int memberCount) {
			this.memberCount = memberCount;
			return this;
		}

		public Builder setMemberTypeId(int memberTypeId) {
			this.memberTypeId = memberTypeId;
			return this;
		}

		public Builder setPlanMembers(List<String> planMembers) {
			this.planMembers = planMembers;
			return this;
		}

		public Builder setLikeCount(int likeCount) {
			this.likeCount = likeCount;
			return this;
		}
		
		public Builder setLikeState(boolean likeState) {
			this.likeState = likeState;
			return this;
		}

		public Builder setPlanMemos(List<PlanMemoDto> planMemos) {
			this.planMemos = planMemos;
			return this;
		}

		public Builder setPlans(List<PlanDto> plans) {
			this.plans = plans;
			return this;
		}
		
		public Builder setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
			return this;
		}

		public Builder setCreateDate(LocalDateTime createDate) {
			this.createDate = createDate;
			return this;
		}

		public Builder setUpdateDate(LocalDateTime updateDate) {
			this.updateDate = updateDate;
			return this;
		}


		public PlannerDto build() {
			return new PlannerDto(this);
		}
	}

	public PlannerDto() {

	}

	public PlannerDto(Builder builder) {
		this.plannerId = builder.plannerId;
		this.accountId = builder.accountId;
		this.areaCode = builder.areaCode;
		this.creator = builder.creator;
		this.title = builder.title;
		this.planDateStart = builder.planDateStart;
		this.planDateEnd = builder.planDateEnd;
		this.planMembers = builder.planMembers;
		this.expense = builder.expense;
		this.memberCount = builder.memberCount;
		this.memberTypeId = builder.memberTypeId;
		this.likeCount = builder.likeCount;
		this.likeState = builder.likeState;
		this.thumbnail = builder.thumbnail;
		this.planMemos = builder.planMemos;
		this.plans = builder.plans;
		this.createDate = builder.createDate;
		this.updateDate = builder.updateDate;
	}

	public int getPlannerId() {
		return plannerId;
	}

	public int getAccountId() {
		return accountId;
	}

	public Integer getAreaCode() {
		return areaCode;
	}

	public String getCreator() {
		return creator;
	}

	public String getTitle() {
		return title;
	}

	public LocalDate getPlanDateStart() {
		return planDateStart;
	}

	public LocalDate getPlanDateEnd() {
		return planDateEnd;
	}

	public List<String> getPlanMembers() {
		return planMembers;
	}

	public int getExpense() {
		return expense;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public int getMemberTypeId() {
		return memberTypeId;
	}

	public int getLikeCount() {
		return likeCount;
	}
	
	public boolean isLikeState() {
		return likeState;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public List<PlanMemoDto> getPlanMemos() {
		return planMemos;
	}

	public List<PlanDto> getPlans() {
		return plans;
	}
	
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	
}
