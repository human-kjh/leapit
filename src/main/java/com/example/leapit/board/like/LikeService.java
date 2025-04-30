package com.example.leapit.board.like;

import com.example.leapit._core.error.ex.Exception403;
import com.example.leapit._core.error.ex.Exception404;
import com.example.leapit._core.error.ex.ExceptionApi403;
import com.example.leapit._core.error.ex.ExceptionApi404;
import com.example.leapit.board.Board;
import com.example.leapit.board.BoardRepository;
import com.example.leapit.user.User;
import com.example.leapit.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public LikeResponse.SaveDTO save(LikeRequest.SaveDTO reqDTO, Integer sessionUserId) {
        if (sessionUserId == null) throw new ExceptionApi404("회원정보가 존재하지 않습니다.");


        Board board = boardRepository.findById(reqDTO.getBoardId());
        if (board == null) throw new ExceptionApi404("게시글을 찾을 수 없습니다.");


        Like like = likeRepository.save(reqDTO.toEntity(sessionUserId));
        Long likeCount = likeRepository.findByLikeCount(reqDTO.getBoardId());
        return new LikeResponse.SaveDTO(like.getId(), likeCount.intValue());
    }

    @Transactional
    public LikeResponse.DeleteDTO delete(Integer id, Integer sessionUserId) {
        if (sessionUserId == null) throw new ExceptionApi404("회원정보가 존재하지 않습니다.");

        Like like = likeRepository.findById(id);
        if (like == null) throw new ExceptionApi404("취소할 좋아요가 없습니다.");

        if (!like.getUser().getId().equals(sessionUserId)) throw new ExceptionApi403("권한이 없습니다.");

        Integer boardId = like.getBoard().getId();

        likeRepository.deleteById(id);

        Long likeCount = likeRepository.findByLikeCount(boardId);

        return new LikeResponse.DeleteDTO(likeCount.intValue());
    }
}
