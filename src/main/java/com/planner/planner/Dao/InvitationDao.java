package com.planner.planner.Dao;

import org.apache.ibatis.annotations.Param;

import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.InvitationDto;

public interface InvitationDao extends UserIdentifierDao{

	public int createInvitation(@Param("invitationDto") InvitationDto invitationDto) throws Exception;

	public InvitationDto findById(int id) throws Exception;

	public void deleteById(int id) throws Exception;
}
