package com.example.leapit.board;

import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
  
    @GetMapping("/community/save-form")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증이 필요합니다.");

        return "personal/board/save-form";
    }

    @PostMapping("/community/save")
    public String save(BoardRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증이 필요합니다.");

        boardService.save(reqDTO, sessionUser);

        return "redirect:/community/list";
    }

    @GetMapping("/community/{id}/update-form")
    public String updateForm(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증이 필요합니다.");

        Board board = boardService.updateCheck(id, sessionUser.getId());
        request.setAttribute("model", board);
        return "personal/board/update-form";
    }


    @PostMapping("/community/{id}/update")
    public String update(@PathVariable("id") Integer id, BoardRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증이 필요합니다.");

        boardService.update(reqDTO, id, sessionUser.getId());

        return "redirect:/community/" + id;
    }
  
    @PostMapping("/community/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증이 필요합니다.");

        boardService.delete(id, sessionUser.getId());

        return "redirect:/community/list";
    }
}
