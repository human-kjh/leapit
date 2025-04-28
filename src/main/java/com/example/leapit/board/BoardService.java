package com.example.leapit.board;

import com.example.leapit.board.like.Like;
import com.example.leapit.board.like.LikeRepository;
import com.example.leapit.board.reply.Reply;
import com.example.leapit.board.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final LikeRepository likeRepository;

    public List<BoardResponse.ListDTO> list(Integer sessionUserId) {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(board -> new BoardResponse.ListDTO(board, sessionUserId))
                .toList();
    }
  
    public BoardResponse.DetailDTO detail(Integer id, Integer userId) {
        Board board = boardRepository.findByIdJoinUser(id);

        // 비로그인 상태 대비 기본값
        Boolean isLike = false;
        Integer likeId = null;

        // 로그인 상태면 좋아요 정보 조회
        if (userId != null) {
            Like like = likeRepository.findByUserIdAndBoardId(userId, id);
            if (like != null) {
                isLike = true;
                likeId = like.getId();
            }
        }

        // 좋아요 개수는 항상 조회 (로그인 여부 상관없이)
        Long likeCount = likeRepository.findByLikeCount(board.getId());

        List<Reply> replies = replyRepository.findAllByBoardId(id);

        BoardResponse.DetailDTO detailDTO = new BoardResponse.DetailDTO(board, userId, isLike, likeCount.intValue(), likeId, replies);

        return detailDTO;
    }
}
