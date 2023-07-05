package com.planner.planner.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Exception.NotFoundMemberException;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.PlanMemberService;

@Service
public class PlanMemberServiceImpl implements PlanMemberService {
	private AccountDao accountDao;
	private PlanMemberDao planMemberDao;
	

	public PlanMemberServiceImpl(AccountDao accountDao, PlanMemberDao planMemberDao) {
		this.accountDao = accountDao;
		this.planMemberDao = planMemberDao;
	}

	@Override
	public List<PlanMemberDto> findMembersByPlannerId(int plannerId) throws Exception {
		return planMemberDao.findMembersByPlannerId(plannerId);
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
			planMemberDao.insertPlanMember(plannerId, user.getAccountId());
		}
	}

	@Override
	public void deleteMember(int plannerId, String nickName) throws Exception {
		List<PlanMemberDto> members = planMemberDao.findMembersByPlannerId(plannerId);
		AccountDto user = accountDao.findAccountIdByNickName(nickName);
		if (user == null) {
			throw new NotFoundUserException("사용자를 찾을 수 없습니다.");
		}
		boolean isMatch = members.stream().anyMatch(m -> m.getAccountId() == user.getAccountId());
		if (!isMatch) {
			throw new NotFoundMemberException("멤버를 찾을 수 없습니다.");
		}
		planMemberDao.deletePlanMember(plannerId, user.getAccountId());
	}

}
