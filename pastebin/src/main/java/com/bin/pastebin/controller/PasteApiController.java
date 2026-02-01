package com.bin.pastebin.controller;

import com.bin.pastebin.dto.CreatePasteRequest;
import com.bin.pastebin.dto.PasteResponse;
import com.bin.pastebin.model.Paste;
import com.bin.pastebin.service.PasteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/pastes")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "https://pastebin-ui-eta.vercel.app"})
public class PasteApiController {

    private final PasteService service;

    public PasteApiController(PasteService service) {
        this.service = service;
    }

    @PostMapping
    public Map<String, String> create(
            @RequestBody CreatePasteRequest req,
            HttpServletRequest request) {

        Paste p = service.create(req);

        String baseUrl = request.getRequestURL()
                .toString()
                .replace("/api/pastes", "");

        return Map.of(
                "id", p.getId(),
                "url", baseUrl + "/p/" + p.getId()
        );
    }

    @GetMapping("/{id}")
    public PasteResponse fetch(
            @PathVariable String id,
            @RequestHeader(value = "x-test-now-ms", required = false) Long testNow) {

        Instant now = testNow != null
                ? Instant.ofEpochMilli(testNow)
                : Instant.now();

        Paste p = service.fetch(id, now);

        PasteResponse r = new PasteResponse();
        r.content = p.getContent();
        r.expires_at = p.getExpiresAt();
        r.remaining_views = p.getMaxViews() == null
                ? null
                : p.getMaxViews() - p.getViews();

        return r;
    }
}
