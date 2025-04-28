package com.example.leapit.board.like;

import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;
    private final HttpSession session;

    @PostMapping("/like")
    public Resp<?> saveLike(@RequestBody LikeRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증이 필요합니다.");

        LikeResponse.SaveDTO respDTO = likeService.save(reqDTO, sessionUser.getId());

        return Resp.ok(respDTO);
    }

    @DeleteMapping("/like/{id}")
    public Resp<?> deleteLike(@PathVariable("id") Integer id) {
        // 인증로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증이 필요합니다.");

        LikeResponse.DeleteDTO respDTO = likeService.delete(id);   // likeId

        return Resp.ok(respDTO);
    }
}

