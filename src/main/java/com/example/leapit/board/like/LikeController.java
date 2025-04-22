package com.example.leapit.board.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class LikeController {
    private final LikeService likeService;
}
