package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateMemberRequest;
import org.example.exception.ConflictException;
import org.example.exception.NotFoundException;
import org.example.model.Member;
import org.example.model.MemberStatus;
import org.example.model.MembershipPlan;
import org.example.repository.MemberRepository;
import org.example.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;

    public Member createMember(UUID planId, CreateMemberRequest request) {
        MembershipPlan plan = planRepository.findById(planId).orElseThrow(() -> new NotFoundException("Membershiip plan " + planId + " not found"));

        //TODO possible race when more requests
        long activeMembers = memberRepository.countByPlanIdAndStatus(planId, MemberStatus.ACTIVE);
        if (activeMembers >= plan.getMaxMembers()) {
            throw new ConflictException("Plan " + planId + " is full [max " + plan.getMaxMembers() + "]");
        }

        Member member = new Member();
        member.setName(request.getName());
        member.setSurname(request.getSurname());
        member.setEmail(request.getEmail());
        member.setStartDate(LocalDate.now());
        member.setStatus(MemberStatus.ACTIVE);
        member.setPlan(plan);

        return memberRepository.save(member);
    }

    public List<Member> listMembers() { return memberRepository.findAll(); }

    public Member cancelMembership(UUID memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("Member " + memberId + " not found"));

        if (member.getStatus() == MemberStatus.CANCELLED) {
            throw new ConflictException("Member " + memberId + " already cancelled");
        }

        member.setStatus(MemberStatus.CANCELLED);
        return memberRepository.save(member);
    }
}
