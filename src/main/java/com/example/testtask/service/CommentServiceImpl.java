package com.example.testtask.service;

import com.example.testtask.auth.CustomUserDetails;
import com.example.testtask.dto.CommentResponse;
import com.example.testtask.exception.NotCorrectPageException;
import com.example.testtask.exception.NotExistsInDBException;
import com.example.testtask.model.Comment;
import com.example.testtask.model.Task;
import com.example.testtask.model.User;
import com.example.testtask.repository.CommentRepository;
import com.example.testtask.repository.TaskRepository;
import com.example.testtask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.testtask.util.DataValid.NO_COMMENTS;
import static com.example.testtask.util.DataValid.PAGE_NOT_CORRECT;
import static com.example.testtask.util.DataValid.TASK_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public CommentResponse createComment(Long task_id, String description) {
        Task foundedTask = taskRepository.findById(task_id).orElse(null);
        if (foundedTask == null) {
            throw new NotExistsInDBException(
                    String.format(TASK_NOT_FOUND, task_id)
            );
        }

        Comment comment = Comment.builder()
                .task(foundedTask)
                .author(getAuthUser())
                .description(description)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentResponse.class);
    }


    @Override
    public List<CommentResponse> getAllComments(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Comment> comments = commentRepository.findAll(pageable);

        validPage(pageNumber, comments);

        return comments.get()
                .map(task -> modelMapper.map(task, CommentResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getAllCommentsTask(Long id, int pageNumber, int pageSize) {
        Task foundedTask = taskRepository.findById(id).orElse(null);
        if (foundedTask == null) {
            throw new NotExistsInDBException(
                    String.format(TASK_NOT_FOUND, id)
            );
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Comment> comments = commentRepository.findCommentByTask_id(id, pageable);

        validPage(pageNumber, comments);

        return comments.get()
                .map(task -> modelMapper.map(task, CommentResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getAllCommentsAuthUser(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Comment> comments = commentRepository
                .findCommentByAuthor_id(
                        getAuthUser().getId(),
                        pageable
                );

        validPage(pageNumber, comments);

        return comments.get()
                .map(task -> modelMapper.map(task, CommentResponse.class))
                .collect(Collectors.toList());
    }


    private User getAuthUser() {
        CustomUserDetails userDetails = (CustomUserDetails) (SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
        return userRepository.findByEmail(userDetails.getEmail()).orElse(null);
    }

    private void validPage(int pageNumber, Page<Comment> comments) {
        if (pageNumber > comments.getTotalPages()) {
            throw new NotCorrectPageException(
                    String.format(PAGE_NOT_CORRECT,
                            comments.getTotalPages()));
        }

        if (comments.getContent().isEmpty()) {
            throw new NotExistsInDBException(NO_COMMENTS);
        }
    }
}
