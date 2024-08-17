package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dto.InvitationDto;
import com.planner.planner.Exception.DataNotFoundException;
import com.planner.planner.Mapper.InvitationMapper;
import com.planner.planner.Mapper.PlanMemberMapper;
import com.planner.planner.Service.InvitationService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
	private final InvitationMapper invitationMapper;
	private final PlanMemberMapper planMemberMapper;
	
	@Override
	public void invite(InvitationDto invitationDto) throws Exception {
		invitationMapper.createInvitation(invitationDto);
	}

	@Override
	public InvitationDto findById(int id) throws Exception {
		InvitationDto invitation = invitationMapper.findById(id);
		if(invitation == null) {
			throw new DataNotFoundException("초대 데이터가 존재하지 않습니다.");
		}
		
		return invitation;
	}
	
	@Override
	public void acceptInvite(int id) throws Exception {
		InvitationDto invitation = invitationMapper.findById(id);
		if(invitation == null) {
			throw new DataNotFoundException("초대 데이터가 존재하지 않습니다.");
		}
		
		invitationMapper.deleteById(id);
		planMemberMapper.insertPlanMember(invitation.getPlannerId(), invitation.getAccountId());
	}

	@Override
	public void rejectInvite(int id) throws Exception {
		InvitationDto invitation = invitationMapper.findById(id);
		if(invitation == null) {
			throw new DataNotFoundException("초대 데이터가 존재하지 않습니다.");
		}
		
		invitationMapper.deleteById(id);
		planMemberMapper.deletePlanMember(invitation.getPlannerId(), invitation.getAccountId());
	}

}
