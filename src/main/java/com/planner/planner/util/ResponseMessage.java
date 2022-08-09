package com.planner.planner.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ResponseMessage {
	private boolean state;
	private String message;

	public ResponseMessage(boolean state,String message) {
		this.state = state;
		this.message = message;
	}
	
	public ResponseMessage(boolean state, Object object) {
		this.state = state;
		ObjectMapper om = new ObjectMapper();
		try {
			this.message = om.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
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

	public String getMessage() {
		return message;
	}
	
}
