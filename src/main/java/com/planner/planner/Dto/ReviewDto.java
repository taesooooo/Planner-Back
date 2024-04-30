package com.planner.planner.Dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.planner.planner.Common.ValidationGroups.ReviewCreateGroup;
import com.planner.planner.Common.ValidationGroups.ReviewUpdateGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewDto {
	private int reviewId;
	private Integer plannerId;
	
	@NotBlank(message = "제목은 필수 항목입니다.", groups = {ReviewCreateGroup.class, ReviewUpdateGroup.class})
	private String title;
	
	private String writer;
	private int writerId;
	
	@NotBlank(message = "내용을 적어주세요.", groups = {ReviewCreateGroup.class, ReviewUpdateGroup.class})
	private String content;
	@PositiveOrZero(message = "지역 코드는 음수일 수 없습니다.", groups = {ReviewCreateGroup.class, ReviewUpdateGroup.class})
	private Integer areaCode;
	private String thumbnail;
	private int likeCount;
	
	@JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
	private LocalDateTime updateDate;
	
	private List<String> fileNames;
	private List<CommentDto> comments;
}
