package com.planner.planner.Dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dto.AccountDto;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class AccountDaoTest {

	@Autowired
	private AccountDao accountDao;
	
	@BeforeEach
	void setUp() throws Exception {
		
	}
	
	@DisplayName("유저 생성")
	@Test
	public void createAccountTest() {
		AccountDto user = AccountDto.builder()
				.email("test123@")
				.username("test123")
				.password("test123")
				.nickname("test123")
				.phone("01012345678")
				.build();
				
		boolean result = accountDao.create(user);
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("유저 아이디로 유저 정보 가져오기")
	@Test
	public void findByIdTest() {
		AccountDto testUser = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.username("테스트")
				.password(null)
				.nickname("test")
				.phone("01012345678")
				.image("")
				.createDate(null)
				.updateDate(null)
				.build();
		
		AccountDto user = accountDao.findById(1);
		
		assertThat(user).usingRecursiveComparison()
				.ignoringFields("password","createDate","updateDate")
				.isEqualTo(testUser);
	}
	
	@DisplayName("유저 닉네임으로 유저 정보 가져오기")
	@Test
	public void findByNicknameTest() {
		AccountDto testUser = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.username("테스트")
				.password(null)
				.nickname("test")
				.phone("01012345678")
				.image("")
				.createDate(null)
				.updateDate(null)
				.build();
		
		AccountDto user = accountDao.findAccountIdByNickName("test");
		
		assertThat(user).usingRecursiveComparison()
				.ignoringFields("password","createDate","updateDate")
				.isEqualTo(testUser);
	}
	
	@DisplayName("유저 이름, 휴대폰 번호로 유저 정보 가져오기")
	@Test
	public void findByNameAndPhoneTest() {
		List<AccountDto> testUsers = new ArrayList<AccountDto>();
		testUsers.add(AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.username("테스트")
				.password(null)
				.nickname("test")
				.phone("01012345678")
				.image("")
				.createDate(null)
				.updateDate(null)
				.build());

		List<AccountDto> user = accountDao.findByNameAndPhone("테스트", "01012345678");
		
		assertThat(user).usingRecursiveComparison()
				.ignoringFields("password","createDate","updateDate")
				.isEqualTo(testUsers);
	}
	
	@DisplayName("유저 휴대폰 번호로 아이디 가져오기")
	@Test
	public void findEmailByPhone() {
		List<String> testEmails = Arrays.asList("test@naver.com","test2@naver.com","test3@naver.com");
		
		List<String> emails = accountDao.findEmailByPhone("01012345678");
		
		assertThat(emails).usingRecursiveComparison()
			.isEqualTo(testEmails);
	}
	
	@DisplayName("유저 이메일로 유저 정보 가져오기")
	@Test
	public void findByEmail() {
		AccountDto testUser = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.username("테스트")
				.password(null)
				.nickname("test")
				.phone("01012345678")
				.image("")
				.createDate(null)
				.updateDate(null)
				.build();
		
		AccountDto user = accountDao.findByEmail("test@naver.com");
		
		assertThat(user).usingRecursiveComparison()
				.ignoringFields("password","createDate","updateDate")
				.isEqualTo(testUser);
	}
	
	@DisplayName("유저 정보 변경")
	@Test
	public void update() {
		AccountDto testUser = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.username("테스트")
				.password(null)
				.nickname("변경 테스트")
				.phone("01056781234")
				.image("")
				.createDate(null)
				.updateDate(null)
				.build();
		
		boolean result = accountDao.update(testUser);
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("유저 정보 삭제")
	@Test
	public void delete() {
		AccountDto testUser = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.username("테스트")
				.password(null)
				.nickname("test")
				.phone("01012345678")
				.image("")
				.createDate(null)
				.updateDate(null)
				.build();
		
		boolean result = accountDao.delete(testUser);
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("유저 이미지 변경")
	@Test
	public void imageUpdate() {
		boolean result = accountDao.accountImageUpdate(1, "임시 이미지");
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("유저 비밀번호 변경")
	@Test
	public void passwordUpdate() {
		boolean result = accountDao.passwordUpdate(1, "비밀번호 변경 테스트");
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("유저 아이디 검색")
	@Test
	public void searchEmail() {
		AccountDto testUser = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.username("테스트")
				.password(null)
				.nickname("test")
				.phone("01012345678")
				.image("")
				.createDate(null)
				.updateDate(null)
				.build();
		
		AccountDto user = accountDao.searchEmail("test@naver.com");
		
		assertThat(user).usingRecursiveComparison()
			.ignoringFields("password","createDate","updateDate")
			.isEqualTo(testUser);
	}
}
