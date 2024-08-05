package com.planner.planner.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.planner.planner.Dto.AuthenticationCodeDto;

@Mapper
public interface AuthenticationCodeMapper {
	public boolean createByPhone(String phone, String code);

	public boolean createByEmail(String email, String code);

	public AuthenticationCodeDto findByPhone(@Param("phone")String phone);

	public AuthenticationCodeDto findByEmail(@Param("email")String email);

	public boolean updateCodeConfirmByPhone(String phone);

	public boolean updateCodeConfirmByEmail(String email);

	public boolean deleteByPhone(String phone);
}
