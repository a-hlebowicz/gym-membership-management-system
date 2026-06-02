package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreatePlanRequest;
import org.example.exception.NotFoundException;
import org.example.model.Gym;
import org.example.model.MembershipPlan;
import org.example.model.Money;
import org.example.repository.GymRepository;
import org.example.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final GymRepository gymRepository;

    public MembershipPlan createPlan(UUID gymId, CreatePlanRequest request){
        Gym gym = gymRepository.findById(gymId).orElseThrow(() -> new NotFoundException("Gym " + gymId + " not found"));

        MembershipPlan plan = new MembershipPlan();
        plan.setName(request.getName());
        plan.setType(request.getType());
        plan.setMonthlyPrice(new Money(request.getAmount(), request.getCurrency()));
        plan.setDurationMonths(request.getDurationMonths());
        plan.setMaxMembers(request.getMaxMembers());
        plan.setGym(gym);

        return planRepository.save(plan);
    }

}
