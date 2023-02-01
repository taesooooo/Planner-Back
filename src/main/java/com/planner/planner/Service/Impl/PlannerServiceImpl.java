package com.planner.planner.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
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
		
		AccountDto creator = accountDao.findAccountIdByEmail(plannerDto.getCreatorEmail());
		users.add(creator);
		
		for(String email : plannerDto.getPlanMemberEmails()) {
			AccountDto user = accountDao.findAccountIdByEmail(email);
			if(user == null) {
				throw new NotFoundUserException(email + "에 해당하는 사용자를 찾지 못했습니다.");
			}
			users.add(user);
		}
		for(AccountDto user: users) {
			plannerDao.insertPlanMember(plannerId, user.getAccountId());
		}
		
		plannerDao.acceptInvitation(plannerId, creator.getAccountId());
		return plannerId;
	}

	@Override
	public PlannerDto findPlannerByPlannerId(int plannerId) throws Exception {
		PlannerDto planner = plannerDao.findPlannerByPlannerId(plannerId);
		if(planner == null) {
			throw new NotFoundPlanner("존재하지 않는 플래너 입니다.");
		}
		return planner;
	}

	@Override
	public List<PlannerDto> findPlannersByAccountId(int accountId) throws Exception {
		List<PlannerDto> planner = plannerDao.findPlannersByAccountId(accountId);
		if(planner.isEmpty()) {
			throw new NotFoundPlanner("존재하지 않는 플래너 입니다.");
		}
		return planner;
	}

	@Override
	public List<PlannerDto> findPlannerAll() throws Exception {
		List<PlannerDto> planner = plannerDao.findPlannerAll();
		if(planner.isEmpty()) {
			throw new NotFoundPlanner("생성된 플래너가 없습니다.");
		}
		return planner;
	}

	@Override
	public void modifyPlanner(PlannerDto plannerDto) throws Exception {
		// 플래너 기본 정보 업데이트(컨트롤러에서 접근권한 체크 후 해야함)
		plannerDao.updatePlanner(plannerDto.getPlannerId(), plannerDto);
	}

	@Override
	public void deletePlanner(int plannerId) throws Exception {
		// 컨트롤러에서 접근권한 체크 후 해야함
		plannerDao.deletePlanner(plannerId);
	}

	@Override
	public List<PlanMemberDto> findMembersByPlannerId(int plannerId) throws Exception {
		return plannerDao.findMembersByPlannerId(plannerId);
	}

	@Override
	public void inviteMembers(int plannerId, List<String> emails) throws Exception {
		List<AccountDto> users = new ArrayList<AccountDto>();
		for(String email : emails) {
			AccountDto user = accountDao.findAccountIdByEmail(email);
			if(user == null) {
				throw new NotFoundUserException(email + "에 해당하는 사용자를 찾지 못했습니다.");
			}
			users.add(user);
		}
		for(AccountDto user: users) {
			plannerDao.insertPlanMember(plannerId, user.getAccountId());
		}
	}

	@Override
	public void deleteMember(int plannerId, String memberEmail) throws Exception {
		List<PlanMemberDto> members = plannerDao.findMembersByPlannerId(plannerId);
		AccountDto user = accountDao.findAccountIdByEmail(memberEmail);
		if(user == null) {
			throw new NotFoundUserException("사용자를 찾을 수 없습니다.");
		}
		boolean isMatch = members.stream().anyMatch(m -> m.getAccountId() == user.getAccountId());
		if(!isMatch) {
			throw new NotFoundMemberException("멤버를 찾을 수 없습니다.");
		}
		plannerDao.deletePlanMember(plannerId, user.getAccountId());
	}

	@Override
	public int newPlan(PlanDto planDto) throws Exception {
		return plannerDao.insertPlan(planDto);
	}

	@Override
	public void deletePlan(int plannerId, int planId) throws Exception {
		plannerDao.deletePlan(plannerId, planId);
	}

	@Override
	public int newPlanLocation(PlanLocationDto planLocationDto) throws Exception {
		return plannerDao.insertPlanLocation(planLocationDto);
	}

	@Override
	public void deletePlanLocation(int planId, int planLocationId) throws Exception {
		plannerDao.deletePlanLocation(planId, planLocationId);
	}
	
	
}
