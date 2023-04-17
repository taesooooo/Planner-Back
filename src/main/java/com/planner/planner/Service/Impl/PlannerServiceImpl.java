package com.planner.planner.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Common.Page;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.NotFoundMemberException;
import com.planner.planner.Exception.NotFoundPlanner;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.PlannerService;

@Service
@Transactional
public class PlannerServiceImpl implements PlannerService {
	private PlannerDao plannerDao;
	private AccountDao accountDao;

	public PlannerServiceImpl(PlannerDao plannerDao, AccountDao accountDao) {
		this.plannerDao = plannerDao;
		this.accountDao = accountDao;
	}

	@Override
	public int newPlanner(PlannerDto plannerDto) throws Exception {
		// 플래너 생성
		int plannerId = plannerDao.insertPlanner(plannerDto);

		// 멤버 초대(멤버 추가시 기본상태 수락 대기, 단 생성자는 바로 수락 상태로 변경해야함)
		List<AccountDto> users = new ArrayList<AccountDto>();
		List<String> memberNickNames = plannerDto.getPlanMembers();
		memberNickNames.add(plannerDto.getCreator());
		for (String nickName : memberNickNames) {
			AccountDto user = accountDao.findAccountIdByNickName(nickName);
			if (user == null) {
				throw new NotFoundUserException(nickName + "에 해당하는 사용자를 찾지 못했습니다.");
			}
			users.add(user);
		}

		for (AccountDto user : users) {
			plannerDao.insertPlanMember(plannerId, user.getAccountId());
		}

		plannerDao.acceptInvitation(plannerId, plannerDto.getAccountId());
		return plannerId;
	}

	@Override
	public PlannerDto findPlannerByPlannerId(int plannerId) throws Exception {
		PlannerDto planner = plannerDao.findPlannerByPlannerId(plannerId);
		if (planner == null) {
			throw new NotFoundPlanner("존재하지 않는 플래너 입니다.");
		}
		return planner;
	}

	@Override
	public Page<PlannerDto> findPlannersByAccountId(int page, int accountId) throws Exception {
		PageInfo pInfo = new PageInfo.Builder().setPageNum(page).setPageItemCount(10).build();
		List<PlannerDto> plannerList = plannerDao.findPlannersByAccountId(accountId, pInfo);
		if (plannerList.isEmpty()) {
			throw new NotFoundPlanner();
		}

		int totalCount = plannerDao.getTotalCount(accountId);

		Page<PlannerDto> plannerListPage = new Page.Builder<PlannerDto>()
				.setList(plannerList)
				.setPageInfo(pInfo)
				.setTotalCount(totalCount).build();

		return plannerListPage;
	}

	@Override
	public Page<PlannerDto> findPlannerAll(int page) throws Exception {
		PageInfo pInfo = new PageInfo.Builder().setPageNum(page).setPageItemCount(10).build();
		List<PlannerDto> plannerList = plannerDao.findPlannerAll(pInfo);
		if (plannerList.isEmpty()) {
			throw new NotFoundPlanner();
		}

		int totalCount = plannerDao.getTotalCount();

		Page<PlannerDto> plannerListPage = new Page.Builder<PlannerDto>()
				.setList(plannerList)
				.setPageInfo(pInfo)
				.setTotalCount(totalCount).build();

		return plannerListPage;
	}

	@Override
	public void updatePlanner(PlannerDto plannerDto) throws Exception {
		// 플래너 기본 정보 업데이트(컨트롤러에서 접근권한 체크 후 해야함)
		plannerDao.updatePlanner(plannerDto.getPlannerId(), plannerDto);
	}

	@Override
	public void deletePlanner(int plannerId) throws Exception {
		// 컨트롤러에서 접근권한 체크 후 해야함
		plannerDao.deletePlanner(plannerId);
	}

	@Override
	public int newMemo(int plannerId, PlanMemoDto planMemoDto) {
		return plannerDao.insertPlanMemo(plannerId, planMemoDto);
	}

	@Override
	public void updateMemo(int memoId, PlanMemoDto planMemoDto) {
		plannerDao.updatePlanMemo(memoId, planMemoDto);
	}

	@Override
	public void deleteMemo(int memoId) {
		plannerDao.deletePlanMemo(memoId);
	}

	@Override
	public List<PlanMemberDto> findMembersByPlannerId(int plannerId) throws Exception {
		return plannerDao.findMembersByPlannerId(plannerId);
	}

	@Override
	public void inviteMembers(int plannerId, List<String> nickNames) throws Exception {
		List<AccountDto> users = new ArrayList<AccountDto>();
		for (String nickName : nickNames) {
			AccountDto user = accountDao.findAccountIdByNickName(nickName);
			if (user == null) {
				throw new NotFoundUserException(nickName + "에 해당하는 사용자를 찾지 못했습니다.");
			}
			users.add(user);
		}
		for (AccountDto user : users) {
			plannerDao.insertPlanMember(plannerId, user.getAccountId());
		}
	}

	@Override
	public void deleteMember(int plannerId, String nickName) throws Exception {
		List<PlanMemberDto> members = plannerDao.findMembersByPlannerId(plannerId);
		AccountDto user = accountDao.findAccountIdByNickName(nickName);
		if (user == null) {
			throw new NotFoundUserException("사용자를 찾을 수 없습니다.");
		}
		boolean isMatch = members.stream().anyMatch(m -> m.getAccountId() == user.getAccountId());
		if (!isMatch) {
			throw new NotFoundMemberException("멤버를 찾을 수 없습니다.");
		}
		plannerDao.deletePlanMember(plannerId, user.getAccountId());
	}

	@Override
	public int newPlan(PlanDto planDto) throws Exception {
		return plannerDao.insertPlan(planDto);
	}

	@Override
	public void updatePlan(int planId, PlanDto planDto) throws Exception {
		plannerDao.updatePlan(planId, planDto);
	}

	@Override
	public void deletePlan(int planId) throws Exception {
		plannerDao.deletePlan(planId);
	}

	@Override
	public int newPlanLocation(PlanLocationDto planLocationDto) throws Exception {
		return plannerDao.insertPlanLocation(planLocationDto);
	}

	@Override
	public void updatePlanLocation(int planLocationId, PlanLocationDto planLocationDto) throws Exception {
		plannerDao.updatePlanLocation(planLocationId, planLocationDto);
	}

	@Override
	public void deletePlanLocation(int planLocationId) throws Exception {
		plannerDao.deletePlanLocation(planLocationId);
	}

	@Override
	public void plannerLikeOrUnLike(int accountId, int plannerId) {
		boolean isLike = plannerDao.isLike(accountId, plannerId);
		if (isLike) {
			plannerDao.plannerUnLike(accountId, plannerId);
		} else {
			plannerDao.plannerLike(accountId, plannerId);
		}
	}

	@Override
	public Page<PlannerDto> getLikePlannerList(int page, int accountId) throws Exception {
		PageInfo pInfo = new PageInfo.Builder().setPageNum(page).setPageItemCount(10).build();
		List<PlannerDto> plannerList = plannerDao.likePlannerList(accountId, pInfo);
		if (plannerList.isEmpty()) {
			throw new NotFoundPlanner();
		}

		int totalCount = plannerDao.getTotalCountByLike(accountId);

		Page<PlannerDto> plannerListPage = new Page.Builder<PlannerDto>()
				.setList(plannerList)
				.setPageInfo(pInfo)
				.setTotalCount(totalCount).build();

		return plannerListPage;
	}

}
