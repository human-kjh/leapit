package com.example.leapit.board.like;

import com.example.leapit.board.Board;
import com.example.leapit.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(
        name = "like_tb",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "board_id"})
        }
)
@Entity
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Like(Integer id, User user, Board board, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.board = board;
        this.createdAt = createdAt;
    }
}
