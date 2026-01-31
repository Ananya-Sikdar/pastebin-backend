package com.bin.pastebin.dto;

import java.time.Instant;

public class PasteResponse {
    public String content;
    public Integer remaining_views;
    public Instant expires_at;
}
