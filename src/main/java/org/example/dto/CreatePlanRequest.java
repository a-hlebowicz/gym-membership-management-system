package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.PlanType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlanRequest {

    @NotBlank
    private String name;

    @NotNull
    private PlanType type;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    private String currency;

    @Positive
    private int durationMonths;

    @Positive
    private int maxMembers;
}
