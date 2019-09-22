package net.lagmental.scooters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event {
    private final String scooterId;
    private final String email;
}
