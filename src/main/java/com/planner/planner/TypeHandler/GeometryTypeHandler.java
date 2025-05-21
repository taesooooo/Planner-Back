package com.planner.planner.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import com.planner.planner.Common.Coordinate;

@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class GeometryTypeHandler implements TypeHandler<List<Coordinate>> {

	@Override
	public void setParameter(PreparedStatement ps, int i, List<Coordinate> parameter, JdbcType jdbcType)
			throws SQLException {
		// TODO Auto-generated method stub
		String kwt = coordinateListToWKT(parameter);
		
		ps.setString(i, kwt);
	}

	@Override
	public List<Coordinate> getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		String kwt = rs.getString(columnName);
		
		if(kwt == null || kwt.isBlank()) {
			return null;
		}
		
		List<Coordinate> coordinateList = WKTToCoordinateList(kwt);
		
		return coordinateList;
	}

	@Override
	public List<Coordinate> getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		String kwt = rs.getString(columnIndex);
		
		if(kwt == null || kwt.isBlank()) {
			return null;
		}
		
		List<Coordinate> coordinateList = WKTToCoordinateList(kwt);
		
		return coordinateList;
	}

	@Override
	public List<Coordinate> getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String kwt = cs.getString(columnIndex);
		
		if(kwt == null || kwt.isBlank()) {
			return null;
		}
		
		List<Coordinate> coordinateList = WKTToCoordinateList(kwt);
		
		return coordinateList;
	}
	
	private String coordinateListToWKT(List<Coordinate> routeList) {
		// LINESTRING(0 0, 1 1, 2 2)
		StringBuilder sb = new StringBuilder();
		sb.append("LINESTRING(");
		for(int i=0;i<routeList.size(); i++) {
			Coordinate point = routeList.get(i);
			sb.append(point.getLongitude() + " " + point.getLatitude());
			
			if(i != routeList.size() - 1) {
				sb.append(", ");
			}
			
		}
		
		sb.append(")");
		
		return sb.toString();
	}

	private List<Coordinate> WKTToCoordinateList(String route) {
		// LINESTRING(0 0, 1 1, 2 2)
		
		// \\d+\\s\\d+
		// \\d+(\\.\\d+)?\\s\\d+(\\.\\d+)?
		List<Coordinate> list = new ArrayList<Coordinate>();
		
		String expression = "\\d+(\\.\\d+)?\\s\\d+(\\.\\d+)?";
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(route);
		
		while(matcher.find()) {
			String[] result = matcher.group().split(" ");
			list.add(new Coordinate(Double.parseDouble(result[1]), Double.parseDouble(result[0])));
		}
		
		return list;
	}
}
