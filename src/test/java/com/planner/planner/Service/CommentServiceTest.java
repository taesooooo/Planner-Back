package com.planner.planner.Service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.planner.planner.Dao.CommentDao;
import com.planner.planner.Dto.CommentDto;
import com.planner.planner.Exception.NotFoundCommentException;
import com.planner.planner.Service.Impl.CommentServiceImpl;

public class CommentServiceTest {
	
	@InjectMocks
	private CommentServiceImpl commentService;
	
	@Mock
	private CommentDao commentDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test(expected = NotFoundCommentException.class)
	public void 댓글_가져오기_없는경우() throws Exception {
		int commentId = 1;
		
		when(commentDao.selectCommentByCommentId(anyInt())).thenReturn(null);
		
		CommentDto comment = commentService.findByCommentId(anyInt());
	}
}
