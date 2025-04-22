package com.example.leapit.resume.link;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LinkRepository {
    private final EntityManager em;
}
