package com.mediopia.demo.board_project.controller;

import com.mediopia.demo.board_project.entity.Member;
import com.mediopia.demo.board_project.exception.DuplicateMemberException;
import com.mediopia.demo.board_project.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 로그인
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 회원가입
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("member", new Member());
        return "register";
    }

    @PostMapping("/register")
    public String registerMember(@Valid Member member, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "register"; // 유효성 검증 실패 시 회원가입 페이지로 이동
        }
        try {
            memberService.registerMember(member.getMemberId(), member.getMemberName(), member.getPassword());
            return "redirect:/posts"; // 성공 시 게시글 페이지로 이동
        } catch (DuplicateMemberException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "register"; // 중복 회원 예외 발생 시 회원가입 페이지로 이동
        }
    }


    //회원 탈퇴 페이지
    @GetMapping("/members/delete")
    public String deleteMemberPage() {
        return "member-delete";
    }

    //회원 탈퇴 처리
    @PostMapping("/members/delete")
    public String deleteMember(Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        String memberId = authentication.getName(); // 현재 로그인된 사용자 ID
        memberService.deleteMemberByUsername(memberId);
        return "redirect:/logout";
    }

    //관리자가 회원 삭제
    @PostMapping("/admin/members/delete/{id}")
    public String deleteMemberByAdmin(@PathVariable Long id, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new IllegalStateException("관리자 권한이 필요합니다.");
        }

        memberService.deleteMember(id);
        return "redirect:/admin/members";
    }
}
