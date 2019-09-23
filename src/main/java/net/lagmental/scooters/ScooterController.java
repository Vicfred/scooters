package net.lagmental.scooters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@Slf4j
public class ScooterController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public Result create(@RequestBody Event event) {
        final String scooterId = event.getScooterId();
        final String email = event.getEmail();

        if(scooterId == null || email == null) {
            return new Result(null, "INVALID DATA");
        }

        try {
            jdbcTemplate.update("INSERT INTO scooters VALUES (?, ?)", scooterId, "CREATED");
            jdbcTemplate.update("INSERT INTO transactions VALUES (?, ?, ?, ?, ?, ?)", scooterId, "create", email,
                    "INITIAL", "CREATED", new Timestamp(System.currentTimeMillis()));

            return new Result(scooterId, "CREATED");
        } catch (DuplicateKeyException dupEx) {
            // if the scooter already exists we just return it
            log.info("duplicated key {}", scooterId);

            String status = jdbcTemplate.queryForObject("SELECT status FROM scooters WHERE scooterid = ?",
                new Object[]{scooterId}, String.class);

            return  new Result(scooterId, status);
        }
    }

    private Result processRequest(Event event, String oldState, String newState, String action) {
        final String scooterId = event.getScooterId();
        final String email = event.getEmail();

        if(null == scooterId || null == email)
            return new Result(null, "INVALID DATA");

        String status;

        try {
            status = jdbcTemplate.queryForObject("SELECT status FROM scooters WHERE scooterid = ?",
                    new Object[]{scooterId}, String.class);
        } catch (EmptyResultDataAccessException ex) {
            log.info("scooter {} not found", scooterId);
            return new Result(scooterId, "INVALID ID");
        }

        if(!status.equals(oldState)) {
            log.info("invalid status {}", oldState);
            return new Result(scooterId, "INVALID STATUS");
        }

        jdbcTemplate.update("UPDATE scooters SET status=? where scooterid = ?", newState, scooterId);
        jdbcTemplate.update("INSERT INTO transactions VALUES (?, ?, ?, ?, ?, ?)",
                scooterId, action, email, oldState, newState, new Timestamp(System.currentTimeMillis()));

        return new Result(scooterId, "MAINTENANCE");
    }

    @PostMapping(path = "/setup", consumes = "application/json", produces = "application/json")
    public Result setup(@RequestBody Event event) {
        return processRequest(event, "CREATED", "MAINTENANCE", "setup");
    }

    @PostMapping(path = "/moving", consumes = "application/json", produces = "application/json")
    public Result moving(@RequestBody Event event) {
        return processRequest(event, "MAINTENANCE", "DISTRIBUTING", "setup");
    }

    @PostMapping(path = "/placed", consumes = "application/json", produces = "application/json")
    public Result placed(@RequestBody Event event) {
        return processRequest(event, "DISTRIBUTING", "ONSTREET", "setup");
    }

    @PostMapping(path = "/repair", consumes = "application/json", produces = "application/json")
    public Result repair(@RequestBody Event event) {
        return processRequest(event, "ONSTREET", "MAINTENANCE", "setup");
    }

    @PostMapping(path = "/movefromstreet", consumes = "application/json", produces = "application/json")
    public Result movefromstreet(@RequestBody Event event) {
        return processRequest(event, "ONSTREET", "DISTRIBUTING", "setup");
    }
}
