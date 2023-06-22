package com.planner.planner.Controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.planner.planner.Common.ValidationGroups.PlannerCreateGroup;
import com.planner.planner.Common.ValidationGroups.PlannerUpdateGroup;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.ForbiddenException;
import com.planner.planner.Service.PlannerService;
import com.planner.planner.util.ResponseMessage;
import com.planner.planner.util.UserIdUtil;

@RestController
@RequestMapping(value="/api/planners")
public class PlannerContorller {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerContorller.class);

	private PlannerService plannerService;
	
	public PlannerContorller(PlannerService plannerService) {
		this.plannerService = plannerService;
	}

	@PostMapping
	public ResponseEntity<Object> newPlanner(HttpServletRequest req, @RequestBody @Validated(PlannerCreateGroup.class) PlannerDto plannerDto) throws Exception {
		int newPlannerId = plannerService.newPlanner(plannerDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", newPlannerId));
	}

	@GetMapping
	public ResponseEntity<Object> plannerList(HttpServletRequest req, @RequestBody CommonRequestParamDto commonRequestParamDto) throws Exception {
		int userId = UserIdUtil.getUserId(req);
	
		Page<PlannerDto> planners = plannerService.findPlannerAll(userId, commonRequestParamDto);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", planners));
	}
	
//	@GetMapping(value="/likes")
//	public ResponseEntity<Object> likePlannerList(HttpServletRequest req) throws Exception {
//		int userId = UserIdUtil.getUserId(req);
//		List<PlannerDto> planners = plannerService.getLikePlannerList(userId);
//		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", planners));
//	}

	@GetMapping(value="/{plannerId}")
	public ResponseEntity<Object> findPlannerByPlannerId(HttpServletRequest req, @PathVariable int plannerId) throws Exception {
		PlannerDto planner = plannerService.findPlannerByPlannerId(plannerId);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",planner));
	}
	
	@PatchMapping(value="/{plannerId}")
	public ResponseEntity<Object> modifyPlanner(HttpServletRequest req, @PathVariable int plannerId, 
			@RequestBody @Validated(PlannerUpdateGroup.class) PlannerDto plannerDto) throws Exception {
		checkAuth(req, plannerId);
		
		plannerService.updatePlanner(plannerDto);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value="/{plannerId}")
	public ResponseEntity<Object> deletePlanner(HttpServletRequest req, @PathVariable int plannerId) throws Exception {
		checkAuth(req, plannerId);
		
		plannerService.deletePlanner(plannerId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@PostMapping(value="/{plannerId}/like")
	public ResponseEntity<Object> likePlanner(HttpServletRequest req, @PathVariable int plannerId) throws Exception {
		int id = UserIdUtil.getUserId(req);
		
		plannerService.plannerLikeOrUnLike(id, plannerId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@PostMapping(value="/{plannerId}/memos")
	public ResponseEntity<Object> newMemo(HttpServletRequest req, @PathVariable int plannerId, @RequestBody @Valid PlanMemoDto planMemoDto) throws Exception {
		checkAuth(req, plannerId);
		
		int planMemoId = plannerService.newMemo(plannerId, planMemoDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", planMemoId));
	}
	
	@PatchMapping(value="/{plannerId}/memos/{memoId}")
	public ResponseEntity<Object> updateMemo(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int memoId, @RequestBody @Valid PlanMemoDto planMemoDto) throws Exception {
		checkAuth(req, plannerId);
		
		plannerService.updateMemo(memoId, planMemoDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value="/{plannerId}/memos/{memoId}")
	public ResponseEntity<Object> deleteMemo(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int memoId) throws Exception {
		checkAuth(req, plannerId);
		
		plannerService.deleteMemo(memoId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@PostMapping(value="/{plannerId}/invite-member")
	public ResponseEntity<Object> inviteMember(HttpServletRequest req, @PathVariable int plannerId, @RequestBody HashMap<String, List<String>> members) throws Exception {
		checkAuth(req, plannerId);
		
		plannerService.inviteMembers(plannerId, members.get("members"));
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value="/{plannerId}/delete-member")
	public ResponseEntity<Object> deleteMember(HttpServletRequest req, @PathVariable int plannerId, @RequestParam(value="nick_name") String nickName) throws Exception {
		checkAuth(req, plannerId);
		
		plannerService.deleteMember(plannerId, nickName);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@PostMapping(value="/{plannerId}/plans")
	public ResponseEntity<Object> newPlan(HttpServletRequest req, @PathVariable int plannerId, @RequestBody @Valid PlanDto planDto) throws Exception {
		checkAuth(req, plannerId);
		
		int planId = plannerService.newPlan(planDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", planId));
	}
	
	@PatchMapping(value="/{plannerId}/plans/{planId}")
	public ResponseEntity<Object> updatePlan(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int planId, @RequestBody @Valid PlanDto planDto) throws Exception {
		checkAuth(req, plannerId);
		
		plannerService.updatePlan(planId, planDto);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value="/{plannerId}/plans/{planId}")
	public ResponseEntity<Object> deletePlan(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int planId) throws Exception {
		checkAuth(req, plannerId);
		
		plannerService.deletePlan(planId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@PostMapping(value="/{plannerId}/plans/{planId}/plan-locations")
	public ResponseEntity<Object> newPlanLocation(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int planId, @RequestBody @Valid PlanLocationDto planLocationDto) throws Exception {
		checkAuth(req, plannerId);
		
		int planLocationId = plannerService.newPlanLocation(planLocationDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", planLocationId));
	}
	
	@PatchMapping(value="/{plannerId}/plans/{planId}/plan-locations/{planLocationId}")
	public ResponseEntity<Object> updatePlanLocation(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int planId, @PathVariable int planLocationId, @RequestBody @Valid PlanLocationDto planLocationDto) throws Exception {
		checkAuth(req, plannerId);
		
		plannerService.updatePlanLocation(planLocationId, planLocationDto);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value="/{plannerId}/plans/{planId}/plan-locations/{planLocationId}")
	public ResponseEntity<Object> deletePlanLocation(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int planId, @PathVariable int planLocationId) throws Exception {
		checkAuth(req, plannerId);
		
		plannerService.deletePlanLocation(planLocationId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	private void checkAuth(HttpServletRequest req, int plannerId) throws Exception {
		int id = UserIdUtil.getUserId(req);
		PlannerDto planner = plannerService.findPlannerByPlannerId(plannerId);
		
		if(planner.getAccountId() != id) {
			throw new ForbiddenException("접근 권한이 없습니다.");
		}
	}
 }
