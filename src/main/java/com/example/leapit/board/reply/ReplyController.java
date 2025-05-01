package com.example.leapit.board.reply;

import com.example.leapit._core.error.ex.Exception401;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;

    @PostMapping("/s/reply/save")
    public String save(@Valid ReplyRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        replyService.save(reqDTO, sessionUser);

        return "redirect:/community/" + reqDTO.getBoardId();
    }

    @PostMapping("/s/reply/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        Integer boardId = replyService.delete(id, sessionUser.getId());

        replyService.delete(id, sessionUser.getId());
        return "redirect:/community/" + boardId;
    }
}
