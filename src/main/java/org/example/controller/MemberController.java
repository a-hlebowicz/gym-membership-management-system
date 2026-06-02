package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.CreateMemberRequest;
import org.example.dto.MemberResponse;
import org.example.dto.PlanResponse;
import org.example.model.Member;
import org.example.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gyms/{gymId}/plans")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/plans/{planId}/members")
    public ResponseEntity<MemberResponse> createMember(@PathVariable UUID planId, @RequestBody @Valid CreateMemberRequest request){
        Member member = memberService.createMember(planId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponse.from(member));
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> listMembers(){
        List<MemberResponse> members = memberService.listMembers().stream().map(MemberResponse::from).toList();
        return ResponseEntity.ok(members);
    }

    @PostMapping("/members/{memberId}/cancel")
    public ResponseEntity<MemberResponse> cancelMembership(@PathVariable UUID memberId) {
        Member member = memberService.cancelMembership(memberId);
        return ResponseEntity.ok(MemberResponse.from(member));
    }
}
