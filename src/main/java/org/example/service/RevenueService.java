package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.RevenueRow;
import org.example.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final MemberRepository memberRepository;

    public List<RevenueRow> getRevenueReport() {
        return memberRepository.revenueReport();
    }
}
