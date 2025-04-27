package com.example.leapit.board.like;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LikeRepository {
    private final EntityManager em;

    public Like findByUserIdAndBoardId(Integer userId, Integer boardId) {
        Query query = em.createQuery("select lo from Like lo where lo.user.id = :userId and lo.board.id = :boardId", Like.class);
        query.setParameter("userId", userId);
        query.setParameter("boardId", boardId);

        try {
            return (Like) query.getSingleResult();  // unique 제약조건이기 때문에 SingleResult
        } catch (Exception e) {
            return null;
        }
    }

    public Long findByLikeCount(int boardId) {
        Query query = em.createQuery("select count(lo) from Like lo where lo.board.id = :boardId");
        query.setParameter("boardId", boardId);
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public Like findById(Integer id) {
        return em.find(Like.class, id);
    }

    public void deleteByBoardId(Integer id) {
        em.createQuery("delete from Like lo where lo.board.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}
