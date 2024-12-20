package com.mediopia.demo.board_project.service;

import com.mediopia.demo.board_project.entity.Post;
import com.mediopia.demo.board_project.entity.Member;
import com.mediopia.demo.board_project.repository.PostRepository;
import com.mediopia.demo.board_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //글 작성자인지 확인
    public boolean isAuthor(Post post, String loggedInUserId) {
        return post.getMember().getMemberId().equals(loggedInUserId);
    }

    // 어드민인지 확인
    public boolean isAdmin(Authentication authentication) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    // 게시물 조회
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시물을 찾을 수 없습니다. ID: " + id));
    }

    // 게시물 작성
    public Post createPost(Post post, String memberId) {
        Member author = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        post.setMember(author);
        return postRepository.save(post);
    }

    // 게시물 수정
    public void updatePost(Long id, String title, String content) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시물을 찾을 수 없습니다."));
        post.setTitle(title);
        post.setContent(content);

        postRepository.save(post);
    }

    // 게시물 삭제
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // 페이징
    public Page<Post> getPosts(int page){
        int pageSize = 10; // 한 페이지당 글 개수
        return postRepository.findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

}
