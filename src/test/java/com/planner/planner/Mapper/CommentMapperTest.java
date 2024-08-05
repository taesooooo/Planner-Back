package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Dto.CommentDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CommentMapperTest {
	@Autowired
	private CommentMapper mapper;
	
	@DisplayName("댓글 작성")
	@Test
	public void insertComment() throws Exception {
		CommentDto testComment = CommentDto.builder()
				.content("댓글 작성 테스트")
				.parentId(null)
				.build();
		
		mapper.insertComment(1, 1, testComment);
		
		assertThat(testComment.getCommentId()).isEqualTo(4);
	}
	
	@DisplayName("댓글 아이디로 댓글 가져오기")
	@Test
	public void findById() throws Exception {
		CommentDto testComment = CommentDto.builder()
				.commentId(1)
				.reviewId(1)
				.writerId(1)
				.content("아니 이렇게 좋다구요?")
				.parentId(null)
				.writer("test")
				.build();
		
		CommentDto findComment = mapper.findById(1);
		
		assertThat(findComment).usingRecursiveComparison()
				.ignoringFields("createDate", "updateDate")
				.isEqualTo(testComment);
	}
	
	@DisplayName("리뷰아이디로 모든 댓글 가져오기")
	@Test
	public void findAll() throws Exception {
		List<CommentDto> testCommentList = Arrays.asList(
				CommentDto.builder()
				.commentId(1)
				.reviewId(1)
				.writerId(1)
				.content("아니 이렇게 좋다구요?")
				.parentId(null)
				.writer("test")
				.build(),
				CommentDto.builder()
				.commentId(2)
				.reviewId(1)
				.writerId(1)
				.content("아니 이렇게 좋다구요?")
				.parentId(1)
				.writer("test")
				.build(),
				CommentDto.builder()
				.commentId(3)
				.reviewId(1)
				.writerId(1)
				.content("아니 이렇게 좋다구요?")
				.parentId(2)
				.writer("test")
				.build()
				);
		
		List<CommentDto> findCommentList = mapper.findAll(1);
		
		assertThat(findCommentList).usingRecursiveComparison()
				.ignoringFields("createDate", "updateDate")
				.isEqualTo(testCommentList);
	}
	
	@DisplayName("댓글 수정")
	@Test
	public void updateComment() throws Exception {
		CommentDto testComment = CommentDto.builder()
				.commentId(1)
				.content("댓글 수정 테스트")
				.parentId(null)
				.build();
		
		int result = mapper.updateComment(1, testComment);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("댓글 삭제")
	@Test
	public void deleteComment() throws Exception {
		int result = mapper.deleteComment(1, 1);
		
		assertThat(result).isEqualTo(1);
	}
}
