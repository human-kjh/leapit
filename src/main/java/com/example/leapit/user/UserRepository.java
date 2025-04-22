package com.example.leapit.user;

import com.example.leapit.common.enums.Role;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findByUsernameAndRole(String username, Role role) {
        try {
            return em.createQuery("select u from User u where u.username = :username and u.role = :role", User.class)
                    .setParameter("username", username)
                    .setParameter("role", role)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User findByUsername(String username) {
        try {
            return em.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
