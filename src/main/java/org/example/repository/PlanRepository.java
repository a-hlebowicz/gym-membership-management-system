package org.example.repository;

import jakarta.persistence.LockModeType;
import org.example.model.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanRepository extends JpaRepository<MembershipPlan, UUID> {
    List<MembershipPlan> findByGymId(UUID gymId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from MembershipPlan p where p.id = :id")
    Optional<MembershipPlan> findByIdLocked(@Param("id") UUID Id);
}
