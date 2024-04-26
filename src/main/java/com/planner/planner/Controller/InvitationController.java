package com.planner.planner.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.planner.planner.Service.InvitationService;
import com.planner.planner.Util.ResponseMessage;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/api/invitation")
public class InvitationController {
	private InvitationService invitationService;
	
	public InvitationController(InvitationService invitationService) {
		this.invitationService = invitationService;
	}
	
	@PostMapping("/{inviteId}/accept")
	public ResponseEntity<Object> inviteAccept(HttpServletRequest req, @PathVariable int inviteId) throws Exception {
		invitationService.acceptInvite(inviteId);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping("/{inviteId}/reject")
	public ResponseEntity<Object> inviteReject(HttpServletRequest req, @PathVariable int inviteId) throws Exception {
		invitationService.rejectInvite(inviteId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
}
