package com.mediopia.demo.board_project.repository;


import com.mediopia.demo.board_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(String memberId);
    void deleteByMemberId(String memberId);
    boolean existsByMemberId(String memberId);
}
