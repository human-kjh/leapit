package com.example.leapit.board;

import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
  
    @GetMapping("/community/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 비로그인 시 상세보기
        Integer sessionUserId = (sessionUser == null ? null : sessionUser.getId());

        BoardResponse.DetailDTO detailDTO = boardService.detail(id, sessionUserId);
        request.setAttribute("model", detailDTO);
        return "personal/board/detail";
    }
}
