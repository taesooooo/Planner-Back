package com.planner.planner.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class SimpleGrantedAuthorityTypeHandler extends BaseTypeHandler<List<SimpleGrantedAuthority>> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<SimpleGrantedAuthority> parameter, JdbcType jdbcType)
			throws SQLException {
//		ps.setString(i, parameter.get(0).getAuthority());
	}

	@Override
	public List<SimpleGrantedAuthority> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return createList(rs.getString(columnName));
	}

	@Override
	public List<SimpleGrantedAuthority> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return createList(rs.getString(columnIndex));
	}

	@Override
	public List<SimpleGrantedAuthority> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return createList(cs.getString(columnIndex));
	}
	
	private List<SimpleGrantedAuthority> createList(String role) {
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		list.add(new SimpleGrantedAuthority(role));
		
		return list;
	}
}
