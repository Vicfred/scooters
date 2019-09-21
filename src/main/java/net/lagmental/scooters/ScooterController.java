package net.lagmental.scooters;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScooterController {

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public Scooter create(@RequestBody Scooter scooter) {
        return new Scooter(scooter.getScooterid(), scooter.getEmail(), "CREATED");
    }
}
