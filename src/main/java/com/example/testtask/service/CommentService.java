package com.example.testtask.service;

import com.example.testtask.dto.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(Long task_id, String description);

    List<CommentResponse> getAllComments(int pageNumber, int pageSize);

    List<CommentResponse> getAllCommentsTask(Long id, int pageNumber, int pageSize);

    List<CommentResponse> getAllCommentsAuthUser(int pageNumber, int pageSize);
}
