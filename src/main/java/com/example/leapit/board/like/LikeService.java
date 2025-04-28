package com.example.leapit.board.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;

    @Transactional
    public LikeResponse.SaveDTO save(LikeRequest.SaveDTO reqDTO, Integer sessionUserId) {
        Like like = likeRepository.save(reqDTO.toEntity(sessionUserId));
        Long likeCount = likeRepository.findByLikeCount(reqDTO.getBoardId());
        return new LikeResponse.SaveDTO(like.getId(), likeCount.intValue());
    }

    @Transactional
    public LikeResponse.DeleteDTO delete(Integer id) {
        Like like = likeRepository.findById(id);
        if (like == null) throw new RuntimeException("취소할 좋아요가 없습니다.");

        Integer boardId = like.getBoard().getId();

        likeRepository.deleteById(id);

        Long likeCount = likeRepository.findByLikeCount(boardId);

        return new LikeResponse.DeleteDTO(likeCount.intValue());
    }
}
