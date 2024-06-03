package com.planner.planner.Dto;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.planner.planner.Common.ValidationGroups.AccountUpdateGroup;
import com.planner.planner.Common.ValidationGroups.LoginGroup;
import com.planner.planner.Common.ValidationGroups.RegisterGroup;
import com.planner.planner.Common.Security.UserIdentifier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(value = Include.NON_NULL)
@Builder
@Getter
public class AccountDto implements UserDetails, UserIdentifier {

	private int accountId;
	@NotBlank(message = "이메일은 필수 항목입니다.", groups = { RegisterGroup.class, LoginGroup.class })
	@Email(message = "이메일 형식이 아닙니다.", groups = { RegisterGroup.class, LoginGroup.class })
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "비밀번호는 필수 항목입니다.", groups = { RegisterGroup.class, LoginGroup.class })
	@Pattern(regexp = "^(?=.*[\\w])(?=.*[~!@#$%^&*()+|=])[\\w~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16글자 및 특수문자가 들어가야합니다.", groups = { RegisterGroup.class, LoginGroup.class })
	private String password;
	
	@NotBlank(message = "이름은 필수 항목입니다.", groups = RegisterGroup.class)
	private String username;
	
	@NotBlank(message = "닉네임은 필수 항목입니다.", groups = { RegisterGroup.class, AccountUpdateGroup.class })
	private String nickname;
	
	@Size(min = 11, max = 11, message = "휴대폰 번호를 다시 작성해주세요.", groups = { RegisterGroup.class, AccountUpdateGroup.class })
	private String phone;
	private String image;
	private Collection<? extends GrantedAuthority> authorities;
	
	@JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
	private LocalDateTime updateDate;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String toString() {
		return "AccountDto [accountId=" + accountId + ", email=" + email + ", userName=" + username + ", nickName="
				+ nickname + ", phone=" + phone + ", image=" + image + "]";
	}
}
