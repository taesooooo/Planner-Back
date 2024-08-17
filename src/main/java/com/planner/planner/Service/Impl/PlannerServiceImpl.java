package com.planner.planner.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Common.Page;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.Notification.NotificationLink;
import com.planner.planner.Common.Notification.NotificationMessage;
import com.planner.planner.Common.Notification.NotificationType;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.InvitationDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.PlannerNotFoundException;
import com.planner.planner.Exception.UserNotFoundException;
import com.planner.planner.Mapper.AccountMapper;
import com.planner.planner.Mapper.InvitationMapper;
import com.planner.planner.Mapper.NotificationMapper;
import com.planner.planner.Mapper.PlanMemberMapper;
import com.planner.planner.Mapper.PlannerMapper;
import com.planner.planner.Service.PlannerService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlannerServiceImpl implements PlannerService {
	private final AccountMapper accountMapper;
	private final PlannerMapper plannerMapper;
	private final PlanMemberMapper planMemberMapper;
	private final InvitationMapper invitationMapper;
	private final NotificationMapper notificationMapper;
	
	@Override
	public int newPlanner(int accountId, PlannerDto plannerDto) throws Exception {
		// 플래너 생성
		AccountDto creatorUser = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int plannerId = plannerMapper.createPlanner(creatorUser, plannerDto);

		List<AccountDto> users = new ArrayList<AccountDto>();
		List<String> memberNickNames = plannerDto.getPlanMembers();

		for (String nickName : memberNickNames) {
			AccountDto user = accountMapper.findByNickName(nickName);
			if (user == null) {
				throw new UserNotFoundException(nickName + "에 해당하는 사용자를 찾지 못했습니다.");
			}
			users.add(user);
		}
		
		// 생성자 바로 등록
		planMemberMapper.insertPlanMember(plannerId, creatorUser.getAccountId());

		// 초대
		for (AccountDto user : users) {
			// 생성자를 제외한 초대자들에게만 초대 및 알림 생성
			InvitationDto invitation = InvitationDto.builder()
					.accountId(user.getAccountId())
					.plannerId(plannerId).build();

			int inviteId = invitationMapper.createInvitation(invitation);

			NotificationDto notificationDto = NotificationDto.builder()
					.accountId(user.getAccountId())
					.content(String.format(NotificationMessage.PLANNER_INVAITE, plannerDto.getCreator()))
					.link(String.format(NotificationLink.PLANNER_INVITE_LINK, inviteId))
					.notificationType(NotificationType.PLANNER_INVITE).build();
			
			notificationMapper.insertNotification(user.getAccountId(), notificationDto);

		}
		
		return plannerId;
	}

	@Override
	public PlannerDto findPlannerByPlannerId(Integer accountId, int plannerId) throws Exception {
		PlannerDto planner = plannerMapper.findByPlannerId(accountId, plannerId);
		if (planner == null) {
			throw new PlannerNotFoundException("존재하지 않는 플래너 입니다.");
		}
		return planner;
	}

	@Override
	public Page<PlannerDto> findPlannersByAccountId(Integer accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		PageInfo pInfo = PageInfo.builder()
				.pageNum(commonRequestParamDto.getPageNum())
				.pageItemCount(commonRequestParamDto.getItemCount())
				.build();
		List<PlannerDto> plannerList = plannerMapper.findListByAccountId(accountId, commonRequestParamDto, pInfo);
		
		int totalCount = 0;
		String keyword = commonRequestParamDto.getKeyword();
		
//		if(keyword != null && !keyword.isEmpty()) {
//			totalCount = plannerMapper.getTotalCountByKeyword(accountId, keyword);
//		}
//		else {
//			totalCount = plannerMapper.getTotalCount(accountId);
//		}
		
		totalCount = plannerMapper.findListTotalCount(accountId, commonRequestParamDto);

		Page<PlannerDto> plannerListPage = new Page.Builder<PlannerDto>()
				.setList(plannerList)
				.setPageInfo(pInfo)
				.setTotalCount(totalCount).build();

		return plannerListPage;
	}

	@Override
	public Page<PlannerDto> findPlannerAll(Integer accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		PageInfo pInfo = PageInfo.builder()
				.pageNum(commonRequestParamDto.getPageNum())
				.pageItemCount(commonRequestParamDto.getItemCount())
				.build();
		List<PlannerDto> plannerList = plannerMapper.findAll(accountId, commonRequestParamDto, pInfo);
		
		int totalCount = 0;
		String keyword = commonRequestParamDto.getKeyword();
		
		totalCount = plannerMapper.findListTotalCount(null, commonRequestParamDto);
		
		Page<PlannerDto> plannerListPage = new Page.Builder<PlannerDto>()
				.setList(plannerList)
				.setPageInfo(pInfo)
				.setTotalCount(totalCount).build();

		return plannerListPage;
	}
	
	@Override
	public Page<PlannerDto> getLikePlannerList(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		PageInfo pInfo = PageInfo.builder()
				.pageNum(commonRequestParamDto.getPageNum())
				.pageItemCount(commonRequestParamDto.getItemCount())
				.build();
		List<PlannerDto> plannerList = plannerMapper.findLikeList(accountId, commonRequestParamDto, pInfo);

		int totalCount = 0;
		String keyword = commonRequestParamDto.getKeyword();
		
//		if(keyword != null && !keyword.isEmpty()) {
//			totalCount = plannerMapper.getTotalCountByLike(accountId, keyword);
//		}
//		else {
//			totalCount = plannerMapper.getTotalCountByLike(accountId);
//		}
		
		totalCount = plannerMapper.findLikeListTotalCount(accountId, commonRequestParamDto);
		
		Page<PlannerDto> plannerListPage = new Page.Builder<PlannerDto>()
				.setList(plannerList)
				.setPageInfo(pInfo)
				.setTotalCount(totalCount).build();

		return plannerListPage;
	}

	@Override
	public void updatePlanner(int plannerId, PlannerDto plannerDto) throws Exception {
		plannerMapper.updatePlanner(plannerId, plannerDto);
	}

	@Override
	public void deletePlanner(int plannerId) throws Exception {
		plannerMapper.deletePlanner(plannerId);
	}

}
