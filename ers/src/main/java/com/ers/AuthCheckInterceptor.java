package com.ers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = req.getSession();
        if(session != null) {
            Object authInfo = session.getAttribute("authInfo");
            if(authInfo != null) {
                return true;
            }
        }
        response.sendRedirect(req.getContextPath() + "/signin");
        return false;
    }
}

