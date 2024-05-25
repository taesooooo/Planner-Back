package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;

import com.planner.planner.Dao.InvitationDao;
import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Dto.InvitationDto;
import com.planner.planner.Exception.NotFoundDataException;
import com.planner.planner.Service.InvitationService;

@Service
public class InvitationServiceImpl implements InvitationService {
	private InvitationDao invitationDao;
	private PlanMemberDao planMemberDao;
	
	public InvitationServiceImpl(InvitationDao invitationDao, PlanMemberDao planMemberDao) {
		this.invitationDao = invitationDao;
		this.planMemberDao = planMemberDao;
	}

	@Override
	public void invite(InvitationDto invitationDto) throws Exception {
		invitationDao.createInvitation(invitationDto);
	}

	@Override
	public InvitationDto findById(int id) throws Exception {
		InvitationDto invitation = invitationDao.findById(id);
		if(invitation == null) {
			throw new NotFoundDataException("초대 데이터가 존재하지 않습니다.");
		}
		
		return invitation;
	}
	
	@Override
	public void acceptInvite(int id) throws Exception {
		InvitationDto invitation = invitationDao.findById(id);
		if(invitation == null) {
			throw new NotFoundDataException("초대 데이터가 존재하지 않습니다.");
		}
		
		invitationDao.deleteById(id);
		planMemberDao.insertPlanMember(invitation.getPlannerId(), invitation.getAccountId());
	}

	@Override
	public void rejectInvite(int id) throws Exception {
		InvitationDto invitation = invitationDao.findById(id);
		if(invitation == null) {
			throw new NotFoundDataException("초대 데이터가 존재하지 않습니다.");
		}
		
		invitationDao.deleteById(id);
		planMemberDao.deletePlanMember(invitation.getPlannerId(), invitation.getAccountId());
	}

}
