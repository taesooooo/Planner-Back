package com.planner.planner.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.InvitationDto;

@Mapper
public interface InvitationMapper extends UserIdentifierDao {
	public int createInvitation(InvitationDto invitationDto) throws Exception;

	public InvitationDto findById(int id) throws Exception;

	public int deleteById(int id) throws Exception;
}
