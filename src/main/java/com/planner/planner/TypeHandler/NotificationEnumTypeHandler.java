package com.planner.planner.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.planner.planner.Common.Notification.NotificationType;

@MappedTypes(NotificationType.class)
public class NotificationEnumTypeHandler extends BaseTypeHandler<NotificationType>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, NotificationType parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	public NotificationType getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return getType(rs.getInt(columnName));
	}

	@Override
	public NotificationType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return getType(rs.getInt(columnIndex));
	}

	@Override
	public NotificationType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return getType(cs.getInt(columnIndex));
	}
	
	private NotificationType getType(int code) throws SQLException {
		return NotificationType.codeOf(code);
	}
}
