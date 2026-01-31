package com.bin.pastebin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

@RestController
public class HealthController {

    private final DataSource dataSource;

    public HealthController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/api/healthz")
    public Map<String, Boolean> health() throws Exception {
        try (Connection c = dataSource.getConnection()) {
            // if we get here, DB is reachable
        }
        return Map.of("ok", true);
    }
}
