package com.example.leapit.resume;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ResumeRepository {
    private final EntityManager em;

    public List<Resume> findAllByUserId(int userId) {
        Query query = em.createQuery("SELECT r FROM Resume r WHERE r.user.id = :userId", Resume.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public Resume findByIdJoinUser(Integer id) {
        Query query = em.createQuery("SELECT r FROM Resume r join fetch r.user WHERE r.id = :id order by :id desc", Resume.class);
        query.setParameter("id", id);
        return (Resume) query.getSingleResult();
    }

    public void deleteById(Integer resumeId) {
        em.remove(em.find(Resume.class, resumeId));
    }

    public void save(Resume resume) {
        em.persist(resume);
    }

    public Resume findById(Integer id) {
        return em.find(Resume.class, id);
    }

}
