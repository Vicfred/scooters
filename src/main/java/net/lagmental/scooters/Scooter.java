package net.lagmental.scooters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Scooter {
    private final String scooterid;
    private final String email;
    private String status;
}
