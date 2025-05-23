package com.example.leapit.board;

import com.example.leapit._core.error.ex.Exception401;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
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

    @GetMapping("/s/community/save-form")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        return "personal/board/save-form";
    }

    @PostMapping("/s/community/save")
    public String save(@Valid BoardRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        boardService.save(reqDTO, sessionUser);

        return "redirect:/community/list";
    }

    @GetMapping("/s/community/{id}/update-form")
    public String updateForm(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        Board board = boardService.updateCheck(id, sessionUser.getId());
        request.setAttribute("model", board);
        return "personal/board/update-form";
    }


    @PostMapping("/s/community/{id}/update")
    public String update(@PathVariable("id") Integer id, @Valid BoardRequest.UpdateDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        boardService.update(reqDTO, id, sessionUser.getId());

        return "redirect:/community/" + id;
    }

    @PostMapping("/s/community/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        boardService.delete(id, sessionUser.getId());

        return "redirect:/community/list";
    }
}
