package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.planner.planner.Dao.CommentDao;
import com.planner.planner.Exception.NotFoundCommentException;
import com.planner.planner.Service.Impl.CommentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
	
	@InjectMocks
	private CommentServiceImpl commentService;
	
	@Mock
	private CommentDao commentDao;

	@BeforeEach
	public void setUp() throws Exception {
//		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void 댓글_가져오기_없는경우() throws Exception {
		when(commentDao.selectCommentByCommentId(anyInt())).thenReturn(null);
		
		 
		assertThatThrownBy(() -> commentService.findByCommentId(1))
				.isExactlyInstanceOf(NotFoundCommentException.class);
	}
}
