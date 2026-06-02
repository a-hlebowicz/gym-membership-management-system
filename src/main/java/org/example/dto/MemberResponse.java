package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Member;
import org.example.model.MemberStatus;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private UUID id;
    private String name;
    private String surname;
    private String email;
    private LocalDate startDate;
    private MemberStatus status;
    private String planName;
    private String gymName;

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getSurname(),
                member.getEmail(), member.getStartDate(), member.getStatus(),
                member.getPlan().getName(), member.getPlan().getGym().getName());
    }
}
