package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.planner.planner.Dao.InvitationDao;
import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Exception.DataNotFoundException;
import com.planner.planner.Service.Impl.InvitationServiceImpl;

public class InvitationServiceTest {
	@Mock
	private InvitationDao invitationDao;
	@Mock
	private PlanMemberDao planMemberDao;
	
	@InjectMocks
	private InvitationServiceImpl invitationServiceImpl;
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void 초대_조회_없는경우() throws Exception {
		when(invitationDao.findById(anyInt())).thenReturn(null);
		
		assertThatThrownBy(() -> invitationServiceImpl.findById(anyInt()))
				.isExactlyInstanceOf(DataNotFoundException.class);
	}
	
	@Test
	public void 초대_수락_초대_데이터_없는경우() throws Exception {
		when(invitationDao.findById(anyInt())).thenReturn(null);
		
		assertThatThrownBy(() -> invitationServiceImpl.acceptInvite(anyInt()))
				.isExactlyInstanceOf(DataNotFoundException.class);
	}
	
	@Test
	public void 초대_거절_초대_데이터_없는경우() throws Exception {
		when(invitationDao.findById(anyInt())).thenReturn(null);
		
		assertThatThrownBy(() -> invitationServiceImpl.rejectInvite(anyInt()))
				.isExactlyInstanceOf(DataNotFoundException.class);
	}
}
