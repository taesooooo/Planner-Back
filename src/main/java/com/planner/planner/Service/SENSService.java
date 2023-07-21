package com.planner.planner.Service;

public interface SENSService {

	public boolean authenticationCodeSMSSend(String phone, String code) throws Exception;
}
