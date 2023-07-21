package com.planner.planner.Util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseMessage {
	private boolean state;
	private Object message;
	private Object data;
	@JsonInclude(value = Include.NON_NULL)
	private String token;

	public ResponseMessage(boolean state,Object message) {
		this.state = state;
		this.message = message;
	}

	public ResponseMessage(boolean state,Object message, Object data) {
		this.state = state;
		this.message = message;
		this.data = data;
	}
	public ResponseMessage(boolean state,Object message, Object data, String token) {
		this.state = state;
		this.message = message;
		this.data = data;
		this.token = token;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
