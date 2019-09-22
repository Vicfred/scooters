package net.lagmental.scooters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ScooterController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public Scooter create(@RequestBody Scooter scooter) {
        String scooterid = scooter.getScooterid();
        String email = scooter.getEmail();
        jdbcTemplate.update("INSERT INTO scooters VALUES (?, ?, ?)", scooterid, email, "CREATED");
        return new Scooter(scooterid, email, "CREATED");
    }

    @PostMapping(path = "/setup", consumes = "application/json", produces = "application/json")
    public Scooter setup(@RequestBody Scooter scooter) {
        String scooterid = scooter.getScooterid();
        String email = scooter.getEmail();
        String status = jdbcTemplate.queryForObject("SELECT status FROM scooters WHERE scooterid = ?",
                new Object[]{scooterid}, String.class);

        // TODO think how to handle the error
        if("CREATED" != status) {
            log.info("invalid status");
            return null;
        }

        jdbcTemplate.update("UPDATE scooters SET status=? where scooterid = ?", "MAINTENANCE", scooterid);

        return new Scooter(scooterid, email, "MAINTENANCE");
    }
}
