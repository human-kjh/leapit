package com.example.leapit.board.reply;

import com.example.leapit._core.error.ex.Exception401;
import com.example.leapit._core.error.ex.Exception403;
import com.example.leapit._core.error.ex.Exception404;
import com.example.leapit.board.Board;
import com.example.leapit.board.BoardRepository;
import com.example.leapit.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void save(ReplyRequest.SaveDTO reqDTO, User sessionUser) {
        if (sessionUser == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        Board board = boardRepository.findById(reqDTO.getBoardId());
        if (board == null) throw new Exception404("게시글을 찾을 수 없습니다.");


        Reply reply = reqDTO.toEntity(sessionUser, board);
        replyRepository.save(reply);
    }

    @Transactional
    public Integer delete(Integer id, Integer sessionUserId) {
        if (sessionUserId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        Reply reply = replyRepository.findById(id);
        if (reply == null) throw new Exception404("삭제할 댓글이 없습니다.");
        if (!(reply.getUser().getId().equals(sessionUserId))) throw new Exception403("삭제할 권한이 없습니다.");

        Integer boardId = reply.getBoard().getId();

        replyRepository.deleteById(id);

        return boardId;
    }
}
