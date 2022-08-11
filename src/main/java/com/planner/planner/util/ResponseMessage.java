package com.planner.planner.util;


public class ResponseMessage {
	private boolean state;
	private String message;
	private Object data;

	public ResponseMessage(boolean state,String message) {
		this.state = state;
		this.message = message;
	}
	public ResponseMessage(boolean state,String message, Object data) {
		this.state = state;
		this.message = message;
		this.data = data;
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
