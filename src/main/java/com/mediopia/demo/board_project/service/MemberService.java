package com.mediopia.demo.board_project.service;

import com.mediopia.demo.board_project.entity.Member;
import com.mediopia.demo.board_project.exception.DuplicateMemberException;
import com.mediopia.demo.board_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerMember(String memberId, String memberName, String password) {
        // 중복 체크
        if (memberRepository.existsByMemberId(memberId)) {
            throw new DuplicateMemberException("이미 존재하는 회원 ID입니다: " + memberId);
        }
        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member(memberId, memberName, encodedPassword, Member.Role.USER);
        memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        //유저 존재 여부 확인 후 삭제
        if ( !memberRepository.existsById(id)){
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }
        memberRepository.deleteById(id);
    }

    @Transactional
    public void deleteMemberByUsername(String memberId) {
        if (!memberRepository.existsByMemberId(memberId)) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다: " + memberId);
        }
        memberRepository.deleteByMemberId(memberId);
    }
}
