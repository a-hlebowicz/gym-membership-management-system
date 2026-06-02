package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.MembershipPlan;
import org.example.model.PlanType;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponse {
    private UUID id;
    private String name;
    private PlanType type;
    private BigDecimal amount;
    private String currency;
    private int durationMonths;
    private int maxMembers;
    private UUID gymId;

    public static PlanResponse from(MembershipPlan plan){
        return new PlanResponse(plan.getId(),plan.getName(),plan.getType(),
                plan.getMonthlyPrice().getAmount(),
                plan.getMonthlyPrice().getCurrency(),
                plan.getDurationMonths(),
                plan.getMaxMembers(),
                plan.getGym().getId());
    }
}
