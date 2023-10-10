package com.planner.planner.Dao;

import com.planner.planner.Dto.InvitationDto;

public interface InvitationDao {

	public void createInvitation(InvitationDto invitationDto) throws Exception;

	public InvitationDto findById(int id) throws Exception;

	public void deleteById(int id) throws Exception;
}
