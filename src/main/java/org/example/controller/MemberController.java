package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.CreateMemberRequest;
import org.example.dto.MemberResponse;
import org.example.model.Member;
import org.example.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
