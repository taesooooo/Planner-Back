package com.planner.planner.Common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PageInfo {
	private int pageNum;
	private int pageItemCount;
	private int pageOffSet;
	
	@Builder
	public PageInfo(int pageNum, int pageItemCount) {
		this.pageNum = pageNum;
		this.pageItemCount = pageItemCount;
		this.pageOffSet = (pageNum * pageItemCount) - pageItemCount;
	}
}
