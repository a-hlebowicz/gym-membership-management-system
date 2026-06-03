package org.example.repository;

import org.example.dto.RevenueRow;
import org.example.model.Member;

import org.example.model.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    long countByPlanIdAndStatus(UUID planId, MemberStatus status);

    @Query("""
            select new org.example.dto.RevenueRow(g.name, sum(p.monthlyPrice.amount), p.monthlyPrice.currency)
            from Member m
            join m.plan p
            join p.gym g
            where m.status = org.example.model.MemberStatus.ACTIVE
            group by g.name, p.monthlyPrice.currency
            """)
    List<RevenueRow> revenueReport();
}
