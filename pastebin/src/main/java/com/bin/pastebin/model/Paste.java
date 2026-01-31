package com.bin.pastebin.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "pastes")
public class Paste {

    @Id
    private String id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private Instant expiresAt;

    private Integer maxViews;

    @Column(nullable = false)
    private Integer views;

    // ---------- Helpers ----------

    public boolean isExpired(Instant now) {
        return expiresAt != null && now.isAfter(expiresAt);
    }

    public boolean isViewLimitExceeded() {
        if (maxViews == null) return false;
        int v = (views == null) ? 0 : views;
        return v >= maxViews;
    }

    // ---------- Getters & Setters ----------
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public Integer getMaxViews() { return maxViews; }
    public void setMaxViews(Integer maxViews) { this.maxViews = maxViews; }

    public Integer getViews() { return views; }
    public void setViews(Integer views) { this.views = views; }
}
