package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.CreateGymRequest;
import org.example.dto.GymResponse;
import org.example.model.Gym;
import org.example.service.GymService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gyms")
@RequiredArgsConstructor
public class GymController {

    private final GymService gymService;

    public ResponseEntity<GymResponse> createGym(@RequestBody @Valid CreateGymRequest request){
        Gym gym = gymService.createGym(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(GymResponse.from(gym));
    }
}
