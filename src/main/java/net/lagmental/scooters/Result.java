package net.lagmental.scooters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private final String scooterId;
    private final String result;
}
