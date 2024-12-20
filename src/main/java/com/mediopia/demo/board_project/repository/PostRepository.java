package com.mediopia.demo.board_project.repository;

import com.mediopia.demo.board_project.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll(Sort sort);
    Page<Post> findAll(Pageable pageable);
}
