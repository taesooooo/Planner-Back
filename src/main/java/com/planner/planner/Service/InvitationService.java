package com.planner.planner.Service;

import com.planner.planner.Dto.InvitationDto;

public interface InvitationService {
	public void invite(InvitationDto invitationDto) throws Exception;
	public InvitationDto findById(int id) throws Exception;
	public void acceptInvite(int id) throws Exception;
	public void rejectInvite(int id) throws Exception;
}
