package com.planner.planner.Common;

public class SqlWhereMapper {
	private boolean existsWhere;
	private StringBuilder sb;
	
	public SqlWhereMapper(StringBuilder sb) {
		this.sb = sb;
	}
	
	public SqlWhereMapper(String query) {
		this.sb = new StringBuilder(query);
	}

	public boolean existsWhere() {
		return existsWhere;
	}
	public void setExistsWhere(boolean existsWhere) {
		this.existsWhere = existsWhere;
	}
	
	public SqlWhereMapper add(String sql) throws Exception {
		if(!existsWhere) {
			sb.append("WHERE " + sql + " ");
			existsWhere = true;
		}
		else {
			sb.append("AND " + sql);
		}
		
		return this;
	}
	
	public SqlWhereMapper addWhere(String whereQuery) throws Exception {
		if(existsWhere) {
			throw new Exception("");
		}
		
		sb.append(whereQuery);
		existsWhere = true;
		
		return this;
	}
	
	public SqlWhereMapper addAnd(String andQuery) throws Exception {
		if(!existsWhere) {
			throw new Exception();
		}
		
		sb.append(" " + andQuery);
		
		return this;
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
	
}
