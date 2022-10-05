package com.planner.planner.Dto;

public class SpotLikeStateDto {
	private Integer contentId;
	private Boolean state;

	public Integer getContentId() {
		return contentId;
	}

	public Boolean getState() {
		return state;
	}

	public SpotLikeStateDto(Integer contentId, Boolean state) {
		this.contentId = contentId;
		this.state = state;
	}

	@Override
	public String toString() {
		return "SpotLikeState [contentId=" + contentId + ", state=" + state + "]";
	}



}
