package com.planner.planner.Service;

import com.planner.planner.Dto.PlanMemoDto;

public interface PlanMemoService {
	// 메모 추가, 수정, 삭제
	public int newMemo(int plannerId, PlanMemoDto planMemoDto);

	public void updateMemo(int memoId, PlanMemoDto planMemoDto);

	public void deleteMemo(int memoId);
}
