package net.lagmental.scooters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@Slf4j
public class ScootersApplication implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ScootersApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE scooters IF EXISTS");
        jdbcTemplate.execute("DROP TABLE transactions IF EXISTS");
        // FIXME scooterid should be unique
        jdbcTemplate.execute("CREATE TABLE scooters(" +
                "scooterid VARCHAR(255) NOT NULL PRIMARY KEY, status VARCHAR(255))");
        jdbcTemplate.execute("CREATE TABLE transactions(" +
                "event VARCHAR(255), email VARCHAR(255), old_status VARCHAR(255), new_status VARCHAR(255), transaction_date TIMESTAMP)");
    }
}
