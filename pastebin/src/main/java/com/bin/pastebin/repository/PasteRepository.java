package com.bin.pastebin.repository;

import com.bin.pastebin.model.Paste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasteRepository extends JpaRepository<Paste, String> {
}
