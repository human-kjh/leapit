package com.example.leapit.board;

import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    private final HttpSession session;


    @GetMapping("/community/list")
    public String list(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            List<BoardResponse.ListDTO> respDTO = boardService.list(null);
            request.setAttribute("models", respDTO);
        } else {
            List<BoardResponse.ListDTO> respDTO = boardService.list(sessionUser.getId());
            request.setAttribute("models", respDTO);
        }

        return "personal/board/list";
    }
}
