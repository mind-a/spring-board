package com.mediopia.demo.board_project.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateMemberException.class)
    public String handleDuplicateMemberException(DuplicateMemberException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "register"; // 회원가입 페이지로 이동
    }
}
