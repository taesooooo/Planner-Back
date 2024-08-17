package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.InvitationDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlanMemberInviteDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.DuplicatePlanMemberException;
import com.planner.planner.Exception.MemberNotFoundException;
import com.planner.planner.Exception.UserNotFoundException;
import com.planner.planner.Mapper.AccountMapper;
import com.planner.planner.Mapper.InvitationMapper;
import com.planner.planner.Mapper.NotificationMapper;
import com.planner.planner.Mapper.PlanMemberMapper;
import com.planner.planner.Mapper.PlannerMapper;
import com.planner.planner.Service.Impl.PlanMemberServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PlanMemberServiceTest {
	@Mock
	private AccountMapper accountMapper;
	@Mock
	private PlanMemberMapper planMemberMapper;
	@Mock
	private PlannerMapper plannerMapper;
	@Mock
	private InvitationMapper invitationMapper;
	@Mock
	private NotificationMapper notificationMapper;

	@InjectMocks
	private PlanMemberServiceImpl planMemberService;
	
	@BeforeEach
	public void setUp() throws Exception {
//		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void 플래너_멤버_초대() throws Exception {
		int plannerId = 1;
		List<String>  inviteMemberEmails = new ArrayList<String>();
		inviteMemberEmails.add("test3@naver.com");
		
		PlanMemberInviteDto invitenMembers= PlanMemberInviteDto.builder().members(inviteMemberEmails).build();
		
		AccountDto inviteMember = AccountDto.builder().accountId(3).build();
		
		// 간략하게 필요한 데이터만 넣어 생성
		PlannerDto planner = PlannerDto.builder().accountId(1)
				.plannerId(1)
				.build();
		
		List<PlanMemberDto> invitedMember = Arrays.asList(
				PlanMemberDto.builder().planMemberId(1).accountId(1).plannerId(1).build());
		
		
		when(accountMapper.findByNickName(anyString())).thenReturn(inviteMember);
		when(plannerMapper.findByPlannerId(isNull(), anyInt())).thenReturn(planner);
		when(planMemberMapper.findPlanMemberListByPlannerId(anyInt())).thenReturn(invitedMember);
		
		planMemberService.inviteMembers(plannerId, invitenMembers);
		
		verify(accountMapper, times(1)).findByNickName(anyString());
		verify(plannerMapper, times(1)).findByPlannerId(isNull(), anyInt());
		verify(planMemberMapper, times(1)).findPlanMemberListByPlannerId(anyInt());
		verify(invitationMapper, times(1)).createInvitation(any(InvitationDto.class));
		verify(notificationMapper, times(1)).insertNotification(anyInt(), any(NotificationDto.class));
	}
	
	@Test
	public void 플래너_멤버_초대_중복() throws Exception {
		int plannerId = 1;
		List<String>  inviteMemberEmails = new ArrayList<String>();
		inviteMemberEmails.add("test2@naver.com");
		
		PlanMemberInviteDto invitenMembers= PlanMemberInviteDto.builder().members(inviteMemberEmails).build();
		
		AccountDto inviteMember = AccountDto.builder().accountId(2).build();
		
		// 간략하게 필요한 데이터만 넣어 생성
		PlannerDto planner = PlannerDto.builder().accountId(1)
				.plannerId(1)
				.build();
		
		List<PlanMemberDto> invitedMember = Arrays.asList(
				PlanMemberDto.builder().planMemberId(1).accountId(1).plannerId(1).build(),
				PlanMemberDto.builder().planMemberId(2).accountId(2).plannerId(1).build());
		
		when(accountMapper.findByNickName(anyString())).thenReturn(inviteMember);
		when(plannerMapper.findByPlannerId(isNull(), anyInt())).thenReturn(planner);
		when(planMemberMapper.findPlanMemberListByPlannerId(anyInt())).thenReturn(invitedMember);
		
		assertThatThrownBy(() -> planMemberService.inviteMembers(plannerId, invitenMembers))
				.isExactlyInstanceOf(DuplicatePlanMemberException.class);
		
		verify(accountMapper, times(1)).findByNickName(anyString());
		verify(plannerMapper, times(1)).findByPlannerId(isNull(), anyInt());
		verify(planMemberMapper, times(1)).findPlanMemberListByPlannerId(anyInt());
	}
	
	@Test
	public void 플래너_멤버_초대_사용자가없을때() throws Exception {
		int plannerId = 1;
		List<String>  inviteMemberEmails = new ArrayList<String>();
		inviteMemberEmails.add("test3@naver.com");
		
		PlanMemberInviteDto invitenMembers= PlanMemberInviteDto.builder().members(inviteMemberEmails).build();
		
		when(accountMapper.findByNickName(anyString())).thenReturn(null);
		
		assertThatThrownBy(() -> planMemberService.inviteMembers(plannerId, invitenMembers))
				.isExactlyInstanceOf(UserNotFoundException.class);
		
		verify(accountMapper, times(1)).findByNickName(anyString());
	}
	
	@Test
	public void 플래너_멤버_삭제() throws Exception {
		int plannerId = 1;
		String testNickName = "test2";
		List<PlanMemberDto> members = new ArrayList<PlanMemberDto>();
		members.add(PlanMemberDto.builder().planMemberId(1).accountId(1).plannerId(1).build());
		members.add(PlanMemberDto.builder().planMemberId(2).accountId(2).plannerId(1).build());
		AccountDto user = AccountDto.builder().accountId(2).build();
		
		when(planMemberMapper.findPlanMemberListByPlannerId(plannerId)).thenReturn(members);
		when(accountMapper.findByNickName(testNickName)).thenReturn(user);
		
		planMemberService.deleteMember(plannerId, testNickName);

		verify(planMemberMapper).findPlanMemberListByPlannerId(anyInt());
		verify(accountMapper).findByNickName(anyString());
		verify(planMemberMapper).deletePlanMember(anyInt(), anyInt());
	}
	
	@Test
	public void 플래너_멤버_삭제_사용자가없을때() throws Exception {
		int plannerId = 1;
		String testNickName = "test2";
		List<PlanMemberDto> members = new ArrayList<PlanMemberDto>();
		members.add(PlanMemberDto.builder().planMemberId(1).accountId(1).plannerId(1).build());
		members.add(PlanMemberDto.builder().planMemberId(2).accountId(2).plannerId(1).build());

		when(planMemberMapper.findPlanMemberListByPlannerId(plannerId)).thenReturn(members);
		when(accountMapper.findByNickName(testNickName)).thenReturn(null);
		
		assertThatThrownBy(() -> planMemberService.deleteMember(plannerId, testNickName))
				.isExactlyInstanceOf(UserNotFoundException.class);
		
		verify(planMemberMapper).findPlanMemberListByPlannerId(anyInt());
		verify(accountMapper).findByNickName(anyString());
	}
	
	@Test
	public void 플래너_멤버_삭제_멤버가없을때() throws Exception {
		int plannerId = 1;
		String testNickName = "test3";
		List<PlanMemberDto> members = new ArrayList<PlanMemberDto>();
		members.add(PlanMemberDto.builder().planMemberId(1).accountId(1).plannerId(1).build());
		members.add(PlanMemberDto.builder().planMemberId(2).accountId(2).plannerId(1).build());
		AccountDto user = AccountDto.builder().accountId(3).build();
		
		when(planMemberMapper.findPlanMemberListByPlannerId(plannerId)).thenReturn(members);
		when(accountMapper.findByNickName(testNickName)).thenReturn(user);
		
		assertThatThrownBy(() -> planMemberService.deleteMember(plannerId, testNickName))
				.isExactlyInstanceOf(MemberNotFoundException.class);

		verify(planMemberMapper).findPlanMemberListByPlannerId(anyInt());
		verify(accountMapper).findByNickName(anyString());
	}

}
