package com.planner.planner.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Common.Page;
import com.planner.planner.Common.ValidationGroups.PlanCreateGroup;
import com.planner.planner.Common.ValidationGroups.PlanLocationCreateGroup;
import com.planner.planner.Common.ValidationGroups.PlanLocationUpdateGroup;
import com.planner.planner.Common.ValidationGroups.PlanUpdateGroup;
import com.planner.planner.Common.ValidationGroups.PlannerCreateGroup;
import com.planner.planner.Common.ValidationGroups.PlannerUpdateGroup;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberInviteDto;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Service.AccountService;
import com.planner.planner.Service.PlanLocationService;
import com.planner.planner.Service.PlanMemberService;
import com.planner.planner.Service.PlanMemoService;
import com.planner.planner.Service.PlanService;
import com.planner.planner.Service.PlannerLikeService;
import com.planner.planner.Service.PlannerService;
import com.planner.planner.Util.ResponseMessage;
import com.planner.planner.Util.UserIdUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/api/planners")
public class PlannerContorller {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerContorller.class);

	private AccountService accountService;
	private PlannerService plannerService;
	private PlanMemberService planMemberService;
	private PlanMemoService planMemoService;
	private PlanService planService;
	private PlanLocationService planLocationService;
	private PlannerLikeService plannerLikeService;
	
	public PlannerContorller(AccountService accountService, PlannerService plannerService, PlanMemberService planMemberService,
			PlanMemoService planMemoService, PlanService planService, PlanLocationService planLocationService,
			PlannerLikeService plannerLikeService) {
		this.accountService = accountService;
		this.plannerService = plannerService;
		this.planMemberService = planMemberService;
		this.planMemoService = planMemoService;
		this.planService = planService;
		this.planLocationService = planLocationService;
		this.plannerLikeService = plannerLikeService;
	}

	@PostMapping
	public ResponseEntity<Object> newPlanner(HttpServletRequest req, @RequestBody @Validated(PlannerCreateGroup.class) PlannerDto plannerDto) throws Exception {
		int newPlannerId = plannerService.newPlanner(plannerDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", newPlannerId));
	}

	@GetMapping
	public ResponseEntity<Object> plannerList(HttpServletRequest req, CommonRequestParamDto commonRequestParamDto) throws Exception {
		Integer userId = UserIdUtil.getUserId(req);
	
		Page<PlannerDto> planners = plannerService.findPlannerAll(userId, commonRequestParamDto);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", planners));
	}

	@GetMapping(value="/{plannerId}")
	public ResponseEntity<Object> findPlannerByPlannerId(HttpServletRequest req, @PathVariable int plannerId) throws Exception {
		Integer userId = UserIdUtil.getUserId(req);
		PlannerDto planner = plannerService.findPlannerByPlannerId(userId, plannerId);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",planner));
	}
	
	@PatchMapping(value="/{plannerId}")
	public ResponseEntity<Object> modifyPlanner(HttpServletRequest req, @PathVariable int plannerId, 
			@RequestBody @Validated(PlannerUpdateGroup.class) PlannerDto plannerDto) throws Exception {
		
		plannerService.updatePlanner(plannerId, plannerDto);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value="/{plannerId}")
	public ResponseEntity<Object> deletePlanner(HttpServletRequest req, @PathVariable int plannerId) throws Exception {
		
		plannerService.deletePlanner(plannerId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@PostMapping(value="/{plannerId}/like")
	public ResponseEntity<Object> likePlanner(HttpServletRequest req, @PathVariable int plannerId) throws Exception {
		int id = UserIdUtil.getUserId(req);
		
		plannerLikeService.plannerLikeOrUnLike(id, plannerId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@PostMapping(value="/{plannerId}/memos")
	public ResponseEntity<Object> newMemo(HttpServletRequest req, @PathVariable int plannerId, @RequestBody @Valid PlanMemoDto planMemoDto) throws Exception {
		
		int planMemoId = planMemoService.newMemo(plannerId, planMemoDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", planMemoId));
	}
	
	@PatchMapping(value="/{plannerId}/memos/{memoId}")
	public ResponseEntity<Object> updateMemo(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int memoId, @RequestBody @Valid PlanMemoDto planMemoDto) throws Exception {
		
		planMemoService.updateMemo(memoId, planMemoDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value="/{plannerId}/memos/{memoId}")
	public ResponseEntity<Object> deleteMemo(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int memoId) throws Exception {
		
		planMemoService.deleteMemo(memoId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}

	@PostMapping(value="/{plannerId}/invite-member")
	public ResponseEntity<Object> inviteMember(HttpServletRequest req, @PathVariable int plannerId, @RequestBody @Valid PlanMemberInviteDto members) throws Exception {
		
		planMemberService.inviteMembers(plannerId, members);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value="/{plannerId}/delete-member")
	public ResponseEntity<Object> deleteMember(HttpServletRequest req, @PathVariable int plannerId, @RequestParam(value="nick_name") String nickName) throws Exception {
		
		planMemberService.deleteMember(plannerId, nickName);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@PostMapping(value="/{plannerId}/plans")
	public ResponseEntity<Object> newPlan(HttpServletRequest req, @PathVariable int plannerId, @RequestBody @Validated(PlanCreateGroup.class) PlanDto planDto) throws Exception {
		
		int planId = planService.newPlan(plannerId, planDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", planId));
	}
	
	@PatchMapping(value="/{plannerId}/plans/{planId}")
	public ResponseEntity<Object> updatePlan(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int planId, @RequestBody @Validated(PlanUpdateGroup.class) PlanDto planDto) throws Exception {
		planService.updatePlan(planId, planDto);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value="/{plannerId}/plans/{planId}")
	public ResponseEntity<Object> deletePlan(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int planId) throws Exception {
		planService.deletePlan(planId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@PostMapping(value="/{plannerId}/plans/{planId}/plan-locations")
	public ResponseEntity<Object> newPlanLocation(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int planId, @RequestBody @Validated(PlanLocationCreateGroup.class) PlanLocationDto planLocationDto) throws Exception {
		int planLocationId = planLocationService.newPlanLocation(planId, planLocationDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", planLocationId));
	}
	
	@PatchMapping(value="/{plannerId}/plans/{planId}/plan-locations/{planLocationId}")
	public ResponseEntity<Object> updatePlanLocation(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int planId, @PathVariable int planLocationId, @RequestBody @Validated(PlanLocationUpdateGroup.class) PlanLocationDto planLocationDto) throws Exception {
		planLocationService.updatePlanLocation(planLocationId, planLocationDto);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value="/{plannerId}/plans/{planId}/plan-locations/{planLocationId}")
	public ResponseEntity<Object> deletePlanLocation(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int planId, @PathVariable int planLocationId) throws Exception {
		planLocationService.deletePlanLocation(planLocationId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
 }
