package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Dto.PasswordResetkeyDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PasswordResetKeyMapperTest {
	@Autowired
	private PasswordResetKeyMapper mapper;
	
	@DisplayName("비밀번호 리셋키 생성")
	@Test
	public void createPasswordResetKey() {
		int result = mapper.createPasswordResetKey("testKey", 1);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("키로 키 정보 가져오기")
	@Test
	public void findByResetKey() {
		PasswordResetkeyDto testResetKey = PasswordResetkeyDto.builder()
				.id(1)
				.resetKey("b7d2703cc82093b67c55cf33e8c40ed46f92bd7bcebe6323ab35fcbbfb9cdf2e")
				.accountId(1)
				.build();
		
		PasswordResetkeyDto findResetKey = mapper.findByResetKey(testResetKey.getResetKey());
		
		assertThat(findResetKey).usingRecursiveComparison()
				.ignoringFields("expireDate", "createDate")
				.isEqualTo(testResetKey);
		
		assertThat(findResetKey.getExpireDate()).isExactlyInstanceOf(LocalDateTime.class);
		assertThat(findResetKey.getCreateDate()).isExactlyInstanceOf(LocalDateTime.class);
	}
	
	@DisplayName("리셋키 삭제")
	@Test
	public void deleteByResetKey() {
		int result = mapper.deleteByResetKey("b7d2703cc82093b67c55cf33e8c40ed46f92bd7bcebe6323ab35fcbbfb9cdf2e");
		
		assertThat(result).isEqualTo(1);
	}
}
