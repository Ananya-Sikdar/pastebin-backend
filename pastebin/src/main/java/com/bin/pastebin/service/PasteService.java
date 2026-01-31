package com.bin.pastebin.service;

import com.bin.pastebin.dto.CreatePasteRequest;
import com.bin.pastebin.model.Paste;
import com.bin.pastebin.repository.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PasteService {

    @Autowired
    private final PasteRepository repo;

    public PasteService(PasteRepository repo) {
        this.repo = repo;
    }

    public Paste create(CreatePasteRequest req) {
        if (req.content == null || req.content.isBlank()) {
            throw new IllegalArgumentException("content is required");
        }

        Paste p = new Paste();
        p.setId(UUID.randomUUID().toString());
        p.setContent(req.content);
        p.setViews(0);
        p.setMaxViews(req.max_views);

        if (req.ttl_seconds != null) {
            p.setExpiresAt(Instant.now().plusSeconds(req.ttl_seconds));
        }

        return repo.save(p);
    }

    public Paste fetch(String id, Instant now) {
        Paste p = repo.findById(id).orElseThrow(NoSuchElementException::new);

        if (p.isExpired(now) || p.isViewLimitExceeded()) {
            throw new NoSuchElementException();
        }

        Integer currentViews = p.getViews();
        if (currentViews == null) {
            currentViews = 0;
        }

        p.setViews(currentViews + 1);
        return repo.save(p);
    }



}
