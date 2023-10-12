package com.planner.planner.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Common.Page;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.Notification.NotificationMessage;
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.NotificationDao;
import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.NotFoundPlanner;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.PlannerService;

@Service
@Transactional
public class PlannerServiceImpl implements PlannerService {
	private AccountDao accountDao;
	private PlannerDao plannerDao;
	private PlanMemberDao planMemberDao;
	private NotificationDao notificationDao;

	public PlannerServiceImpl(AccountDao accountDao, PlannerDao plannerDao, PlanMemberDao planMemberDao, NotificationDao notificationDao) {
		this.accountDao = accountDao;
		this.plannerDao = plannerDao;
		this.planMemberDao = planMemberDao;
		this.notificationDao = notificationDao;
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
			planMemberDao.insertPlanMember(plannerId, user.getAccountId());
			
			// 초대자들에게만 알림 생성
			if(user.getAccountId() != plannerDto.getAccountId()) {
				NotificationDto notificationDto = new NotificationDto.Builder()
						.setAccountId(user.getAccountId())
						.setContent(String.format(NotificationMessage.PLANNER_INVAITE, plannerDto.getCreator()))
						.build();
				notificationDao.createNotification(user.getAccountId(), notificationDto);	
			}
		}

		planMemberDao.acceptInvitation(plannerId, plannerDto.getAccountId());
		
		return plannerId;
	}

	@Override
	public PlannerDto findPlannerByPlannerId(Integer accountId, int plannerId) throws Exception {
		PlannerDto planner = plannerDao.findPlannerByPlannerId(accountId, plannerId);
		if (planner == null) {
			throw new NotFoundPlanner("존재하지 않는 플래너 입니다.");
		}
		return planner;
	}

	@Override
	public Page<PlannerDto> findPlannersByAccountId(Integer accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		PageInfo pInfo = new PageInfo.Builder()
				.setPageNum(commonRequestParamDto.getPageNum())
				.setPageItemCount(commonRequestParamDto.getItemCount())
				.build();
		List<PlannerDto> plannerList = plannerDao.findPlannersByAccountId(accountId, commonRequestParamDto.getSortCriteria(), commonRequestParamDto.getKeyword(), pInfo);
		
		int totalCount = 0;
		String keyword = commonRequestParamDto.getKeyword();
		
		if(keyword != null && !keyword.isEmpty()) {
			totalCount = plannerDao.getTotalCountByKeyword(accountId, keyword);
		}
		else {
			totalCount = plannerDao.getTotalCount(accountId);
		}

		Page<PlannerDto> plannerListPage = new Page.Builder<PlannerDto>()
				.setList(plannerList)
				.setPageInfo(pInfo)
				.setTotalCount(totalCount).build();

		return plannerListPage;
	}

	@Override
	public Page<PlannerDto> findPlannerAll(Integer accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		PageInfo pInfo = new PageInfo.Builder()
				.setPageNum(commonRequestParamDto.getPageNum())
				.setPageItemCount(commonRequestParamDto.getItemCount())
				.build();
		List<PlannerDto> plannerList = plannerDao.findPlannerAll(accountId, commonRequestParamDto.getSortCriteria(), commonRequestParamDto.getKeyword(), pInfo);
		
		int totalCount = 0;
		String keyword = commonRequestParamDto.getKeyword();
		
		if(keyword != null && !keyword.isEmpty()) {
			totalCount = plannerDao.getTotalCountByKeyword(keyword);
		}
		else {
			totalCount = plannerDao.getTotalCount();
		}
		
		Page<PlannerDto> plannerListPage = new Page.Builder<PlannerDto>()
				.setList(plannerList)
				.setPageInfo(pInfo)
				.setTotalCount(totalCount).build();

		return plannerListPage;
	}
	

	@Override
	public Page<PlannerDto> getLikePlannerList(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		PageInfo pInfo = new PageInfo.Builder().setPageNum(commonRequestParamDto.getPageNum()).setPageItemCount(commonRequestParamDto.getItemCount()).build();
		List<PlannerDto> plannerList = plannerDao.findLikePlannerList(accountId, commonRequestParamDto.getSortCriteria(), commonRequestParamDto.getKeyword(), pInfo);

		int totalCount = 0;
		String keyword = commonRequestParamDto.getKeyword();
		
		if(keyword != null && !keyword.isEmpty()) {
			totalCount = plannerDao.getTotalCountByLike(accountId, keyword);
		}
		else {
			totalCount = plannerDao.getTotalCountByLike(accountId);
		}
		
		Page<PlannerDto> plannerListPage = new Page.Builder<PlannerDto>()
				.setList(plannerList)
				.setPageInfo(pInfo)
				.setTotalCount(totalCount).build();

		return plannerListPage;
	}

	@Override
	public void updatePlanner(int plannerId, PlannerDto plannerDto) throws Exception {
		plannerDao.updatePlanner(plannerId, plannerDto);
	}

	@Override
	public void deletePlanner(int plannerId) throws Exception {
		plannerDao.deletePlanner(plannerId);
	}

}
