package com.example.leapit._core.interceptor;

import com.example.leapit._core.error.ex.Exception403;
import com.example.leapit.common.enums.Role;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User sessionUser = (User) request.getSession().getAttribute("sessionUser");

        String uri = request.getRequestURI();

        if (uri.startsWith("/s/personal") || uri.startsWith("/s/api/personal")) {
            if (sessionUser.getRole() != Role.personal) {
                throw new Exception403("개인 유저만 접근할 수 있습니다.");
            }
        }

        if (uri.startsWith("/s/company") || uri.startsWith("/s/api/company")) {
            if (sessionUser.getRole() != Role.company) {
                throw new Exception403("기업 유저만 접근할 수 있습니다.");
            }
        }

        return true;
    }
}