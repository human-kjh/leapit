package com.example.leapit.board.reply;

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
        Board board = boardRepository.findById(reqDTO.getBoardId());

        Reply reply = reqDTO.toEntity(sessionUser, board);
        replyRepository.save(reply);
    }
}
