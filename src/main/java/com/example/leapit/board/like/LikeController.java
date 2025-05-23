package com.example.leapit.board.like;

import com.example.leapit._core.error.ex.ExceptionApi401;
import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;
    private final HttpSession session;

    @PostMapping("/s/api/like")
    public Resp<?> saveLike(@Valid @RequestBody LikeRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new ExceptionApi401("로그인 후 이용");

        LikeResponse.SaveDTO respDTO = likeService.save(reqDTO, sessionUser.getId());

        return Resp.ok(respDTO);
    }

    @DeleteMapping("/s/api/like/{id}")
    public Resp<?> deleteLike(@PathVariable("id") Integer id) {
        // 인증로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new ExceptionApi401("로그인 후 이용");

        LikeResponse.DeleteDTO respDTO = likeService.delete(id, sessionUser.getId());   // likeId

        return Resp.ok(respDTO);
    }
}

