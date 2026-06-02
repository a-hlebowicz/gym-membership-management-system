package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.CreatePlanRequest;
import org.example.dto.PlanResponse;
import org.example.model.MembershipPlan;
import org.example.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gyms/{gymId}/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<PlanResponse>  createPlan(@PathVariable UUID gymId, @RequestBody @Valid CreatePlanRequest request){
        MembershipPlan plan = planService.createPlan(gymId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlanResponse.from(plan));
    }

    @GetMapping
    public ResponseEntity<List<PlanResponse>> listPlans(@PathVariable UUID gymId){
        List<PlanResponse> plans = planService.listPlansForGym(gymId).stream().map(PlanResponse::from).toList();
        return ResponseEntity.ok(plans);
    }

}
