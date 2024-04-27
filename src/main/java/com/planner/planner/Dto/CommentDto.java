package com.planner.planner.Dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentDto {
	private int commentId;
	
	@Min(value = 1, message = "잘못된 리뷰 정보입니다.")
	private int reviewId;
	
	@Min(value = 1, message = "잘못된 사용자 정보입니다.")
	private int writerId;
	
	private String writer;
	
	@NotBlank(message = "댓글 내용을 적어주세요.")
	private String content;
	
	@Min(value = 1, message = "잘못된 댓글 정보입니다.")
	private Integer parentId;
	
	@JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
	private LocalDateTime updateDate;
	
	@Builder.Default
	private List<CommentDto> reComments = new ArrayList<CommentDto>();
}
