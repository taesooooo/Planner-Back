package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.planner.planner.Dto.AccountDto;

public class AccountResultSetExtrator implements ResultSetExtractor<AccountDto>{

	@Override
	public AccountDto extractData(ResultSet rs) throws SQLException, DataAccessException {
		AccountDto account = null;
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		while (rs.next()) {
			if (account == null) {
				account = AccountDto.builder()
						.accountId(rs.getInt(1))
						.email(rs.getString(2))
						.password(rs.getString(3))
						.username(rs.getString(4))
						.nickname(rs.getString(5))
						.phone(rs.getString(6))
						.image(rs.getString(7))
						.authorities(authorities)
						.createDate(rs.getTimestamp(8).toLocalDateTime())
						.updateDate(rs.getTimestamp(9).toLocalDateTime()).build();
			}
			
			String auth = rs.getString("authority");
			if(auth != null) {
				GrantedAuthority authority = new SimpleGrantedAuthority(auth);
				authorities.add(authority);				
			}
		}
		
		
		return account;
	}

}
