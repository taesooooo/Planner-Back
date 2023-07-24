package com.planner.planner.Service.Impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Dao.AuthenticationCodeDao;
import com.planner.planner.Dto.SENS.Messages;
import com.planner.planner.Dto.SENS.SMSRequestDto;
import com.planner.planner.Dto.SENS.SMSResponesDto;
import com.planner.planner.Service.SENSService;
import com.planner.planner.Util.RandomCode;

@Service
public class SENSServiceImpl implements SENSService {
	
	private RestTemplate restTemplate;
	
	private AuthenticationCodeDao authentcationCodeDao;
		
	@Value("${SENS.smsURL}")
	private String smsURL;
	@Value("${SENS.smsSignatureURL}")
	private String signatureURL;
	@Value("${SENS.serviceId}")
	private String serviceId;
	@Value("${SENS.accessKey}")
	private String accessKey;
	@Value("${SENS.secretKey}")
	private String secretKey;
	@Value("${SENS.fromPhone}")
	private String fromPhone;
	
	private ObjectMapper objectMapper;
	
	public SENSServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public boolean authenticationCodeSMSSend(String phone, String code) throws Exception {
		String currentTimeMillis = String.valueOf(System.currentTimeMillis());
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		header.add("x-ncp-apigw-timestamp", currentTimeMillis);
		header.add("x-ncp-iam-access-key", accessKey);
		header.add("x-ncp-apigw-signature-v2", makeSignature(RequestMethod.POST.toString(), signatureURL, currentTimeMillis));
		
		SMSRequestDto request = new SMSRequestDto.Builder()
				.setType("SMS")
				.setContentType("COMM")
				.setCountryCode("82")
				.setFrom(fromPhone)
				.setContent("한국다봄 인증코드:" + code)
				.setMessages(Arrays.asList(new Messages.Builder().setTo(phone).build()))
				.build();

		String body = this.objectMapper.writeValueAsString(request);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(body, header);

		SMSResponesDto response = this.restTemplate.postForObject(smsURL, httpEntity, SMSResponesDto.class);
		
		int statusCode = Integer.parseInt(response.getStatusCode());
		
		if(statusCode == 202) {
			return true;
		}
		
		return false;
	}
	
	private String makeSignature(String method, String signatureUrl, String currentTimeMillis) throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {
		String space = " ";
		String newLine = "\n";

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(signatureUrl)
			.append(newLine)
			.append(currentTimeMillis)
			.append(newLine)
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

	  return encodeBase64String;
	}
}
