package com.example.leapit.jobposting.bookmark;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JobPostingBookmarkRepository {
    private final EntityManager em;
}
