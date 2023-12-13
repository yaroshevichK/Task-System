package com.example.testtask.repository;

import com.example.testtask.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findCommentByTask_id(Long id, Pageable pageable);

    Page<Comment> findCommentByAuthor_id(Long id, Pageable pageable);
}
