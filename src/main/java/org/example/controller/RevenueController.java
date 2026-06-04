package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.RevenueRow;
import org.example.service.RevenueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class RevenueController {

    private final RevenueService revenueService;

    @GetMapping("/revenue")
    public List<RevenueRow> getRevenueReport(){
        return revenueService.getRevenueReport();
    }
}
