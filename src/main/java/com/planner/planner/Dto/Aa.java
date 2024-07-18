package com.planner.planner.Dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Aa {
	private int accountId;
	private String email;
	private String password;
	private String username;
	private String nickname;
	private String phone;
	private String image;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private List<Test> authorities;
}
