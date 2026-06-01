package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateGymRequest;
import org.example.exception.ConflictException;
import org.example.model.Gym;
import org.example.repository.GymRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    public Gym createGym(CreateGymRequest request){

        if(gymRepository.existsByName(request.getName())){
            throw new ConflictException("Gym " + request.getName() + "already exists");
        }

        Gym gym = new Gym();
        gym.setName(request.getName());
        gym.setAddress(request.getAddress());
        gym.setPhoneNumber(request.getPhoneNumber());

        return gymRepository.save(gym);
    }

    public List<Gym> listGyms(){
        return gymRepository.findAll();
    }
}
