package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Gym;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GymResponse {
    private UUID id;
    private String name;
    private String address;
    private String phoneNumber;

    public static GymResponse from(Gym gym) {
        return new GymResponse(gym.getId(), gym.getName(), gym.getAddress(), gym.getPhoneNumber());
    }
}
