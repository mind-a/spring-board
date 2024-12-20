package com.mediopia.demo.board_project.controller;

import com.mediopia.demo.board_project.entity.Post;
import com.mediopia.demo.board_project.repository.PostRepository;
import com.mediopia.demo.board_project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping("/")
    public String index() {
        return "redirect:/posts";
    }

    // 페이징 처리
    @GetMapping("/posts")
    public String index(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Post> postPage = postService.getPosts(page);
        model.addAttribute("postPage", postPage);
        model.addAttribute("currentPage", page);
        return "posts";
    }

    // 글 상세
    @GetMapping("/posts/{id}")
    public String getPostDetail(@PathVariable Long id, Model model, Authentication authentication) {
        Post post = postService.getPostById(id);

        String loggedInUserId = (authentication != null && authentication.isAuthenticated())
                ? authentication.getName() : null;

        boolean isAuthor = postService.isAuthor(post, loggedInUserId);
        boolean isAdmin = postService.isAdmin(authentication);

        model.addAttribute("post", post);
        model.addAttribute("isAuthor", isAuthor);
        model.addAttribute("isAdmin", isAdmin);

        return "post-detail";
    }

    // 새 글 작성
    @GetMapping("/posts/new")
    public String newPostPage(Model model, Authentication authentication) throws AccessDeniedException {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("로그인 후 이용 가능합니다.");
        }
        model.addAttribute("post", new Post());
        return "post-new";
    }

    @PostMapping("/posts/new")
    public String createPost(@ModelAttribute Post post, Authentication authentication) throws AccessDeniedException {

        if(authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("로그인 후 이용 가능합니다.");
        }
        String loggedInUserId = authentication.getName();
        postService.createPost(post, loggedInUserId);

        return "redirect:/";
    }

    // 글 수정
    @GetMapping("/posts/edit/{id}")
    public String editPost(@PathVariable Long id, Model model, Authentication authentication) throws AccessDeniedException {
        Post post = postService.getPostById(id);

        // 권한 체크
        boolean isAuthor = authentication.getName().equals(post.getMember().getMemberId());
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        model.addAttribute("post", post);
        return "post-edit";

    }

    @PostMapping("/posts/edit/{id}")
    public String editPost(@PathVariable Long id, String title, String content, Authentication authentication) throws AccessDeniedException {
        Post post = postService.getPostById(id);

        // 권한 체크
        boolean isAuthor = authentication.getName().equals(post.getMember().getMemberId());
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        postService.updatePost(id, title, content);
        return "redirect:/posts/" + id;

    }

    //글 삭제
    @PostMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id,Authentication authentication) throws AccessDeniedException {
        Post post = postService.getPostById(id);

        // 권한 체크
        boolean isAuthor = authentication != null && authentication.getName().equals(post.getMember().getMemberId());
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        postService.deletePost(id);
        return "redirect:/"; // 삭제 후 메인 페이지로 리다이렉트
    }
}
