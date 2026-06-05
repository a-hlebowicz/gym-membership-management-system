package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.CreateGymRequest;
import org.example.dto.GymResponse;
import org.example.model.Gym;
import org.example.service.GymService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gyms")
@RequiredArgsConstructor
public class GymController {

    private final GymService gymService;

    @PostMapping
    public ResponseEntity<GymResponse> createGym(@RequestBody @Valid CreateGymRequest request){
        Gym gym = gymService.createGym(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(GymResponse.from(gym));
    }

    @GetMapping
    public ResponseEntity<List<GymResponse>> listGyms(){
        List<GymResponse> gyms =gymService.listGyms().stream().map(GymResponse::from).toList();
        return ResponseEntity.ok(gyms);
    }
}
