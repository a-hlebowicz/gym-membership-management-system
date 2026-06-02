package org.example.repository;

import org.example.model.Member;

import org.example.model.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    long countByPlanIdAndStatus(UUID planId, MemberStatus status);
}
