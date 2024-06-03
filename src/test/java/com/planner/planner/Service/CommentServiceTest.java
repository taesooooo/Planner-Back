package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.CommentDao;
import com.planner.planner.Dao.NotificationDao;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommentDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Exception.CommentNotFoundException;
import com.planner.planner.Exception.ReviewNotFoundException;
import com.planner.planner.Exception.UserNotFoundException;
import com.planner.planner.Service.Impl.CommentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
	
	@InjectMocks
	private CommentServiceImpl commentService;
	
	@Mock
	private CommentDao commentDao;
	
	@Mock
	private AccountDao accountDao;
	
	@Mock
	private ReviewDao reviewDao;
	
	@Mock
	private NotificationDao notificationDao;

	@BeforeEach
	public void setUp() throws Exception {
//		MockitoAnnotations.openMocks(this);
	}
	
	@DisplayName("댓글 작성 성공")
	@Test
	public void 댓글_작성_성공() throws Exception {
		when(accountDao.findById(anyInt())).thenReturn(AccountDto.builder()
				.accountId(1)
				.nickname("test")
				.build());
		when(commentDao.insertComment(anyInt(), anyInt(), any(CommentDto.class))).thenReturn(1);
		when(reviewDao.findById(anyInt())).thenReturn(ReviewDto.builder()
				.writerId(1)
				.writer("test")
				.build());
		CommentDto newComment = CommentDto.builder()
				.commentId(1)
				.content("댓글 테스트")
				.build();
		
		int result = commentService.newComment(1, 1, newComment);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("댓글 작성시 요청된 사용자가 없는 경우")
	@Test
	public void 댓글_작성_요청_사용자_없는_경우() throws Exception {
		when(accountDao.findById(anyInt())).thenReturn(null);

		CommentDto newComment = CommentDto.builder()
				.commentId(1)
				.content("댓글 테스트")
				.build();
		
		assertThatThrownBy(() -> commentService.newComment(1, 1, newComment))
				.isExactlyInstanceOf(UserNotFoundException.class);
	}
	
	@DisplayName("댓글 작성시 게시글이 없는 경우")
	@Test
	public void 댓글_작성_게시글_없는_경우() throws Exception {
		when(accountDao.findById(anyInt())).thenReturn(AccountDto.builder()
				.accountId(1)
				.nickname("test")
				.build());
		when(commentDao.insertComment(anyInt(), anyInt(), any(CommentDto.class))).thenReturn(1);
		when(reviewDao.findById(anyInt())).thenReturn(null);
		CommentDto newComment = CommentDto.builder()
				.commentId(1)
				.content("댓글 테스트")
				.build();
		
		assertThatThrownBy(() -> commentService.newComment(1, 1, newComment))
				.isExactlyInstanceOf(ReviewNotFoundException.class);
	}
	
	@DisplayName("댓글 가져오기 - 댓글이 없는 경우")
	@Test
	public void 댓글_가져오기_없는경우() throws Exception {
		when(commentDao.findById(anyInt())).thenReturn(null);
		
		 
		assertThatThrownBy(() -> commentService.findByCommentId(1))
				.isExactlyInstanceOf(CommentNotFoundException.class);
	}
}