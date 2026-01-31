package com.bin.pastebin.controller;

import com.bin.pastebin.model.Paste;
import com.bin.pastebin.service.PasteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.time.Instant;
import java.util.NoSuchElementException;

@RestController
public class PasteViewController {

    private final PasteService service;

    public PasteViewController(PasteService service) {
        this.service = service;
    }

    @GetMapping(value = "/p/{id}", produces = "text/html")
    public ResponseEntity<String> viewPaste(@PathVariable String id) {

        // use real current time (tests for HTML don’t use x-test-now-ms)
        Instant now = Instant.now();

        Paste p = service.fetch(id, now); // same rules as API

        // VERY IMPORTANT: escape to prevent script execution (XSS)
        String safeContent = HtmlUtils.htmlEscape(p.getContent());

        String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Paste</title>
                    <meta charset="UTF-8"/>
                </head>
                <body>
                    <h2>Paste</h2>
                    <pre>%s</pre>
                </body>
                </html>
                """.formatted(safeContent);

        return ResponseEntity.ok(html);
    }
}
