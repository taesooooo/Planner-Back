package com.planner.planner.Config.Properites;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SENS {
	private String smsURL;
	private String signatureURL;
	private String serviceId;
	private String accessKey;
	private String secretKey;
	private String fromPhone;
}