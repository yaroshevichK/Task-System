package com.example.testtask.controller;

import com.example.testtask.dto.CommentRequest;
import com.example.testtask.dto.CommentResponse;
import com.example.testtask.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.testtask.util.DataOpenApi.CODE_CREATE;
import static com.example.testtask.util.DataOpenApi.CODE_NOT_VALID;
import static com.example.testtask.util.DataOpenApi.CODE_OK;
import static com.example.testtask.util.DataOpenApi.DEFAULT_PAGE_NUMBER;
import static com.example.testtask.util.DataOpenApi.DEFAULT_PAGE_SIZE;
import static com.example.testtask.util.DataOpenApi.MESSAGE_ADD_COMMENT;
import static com.example.testtask.util.DataOpenApi.MESSAGE_COMMENTS;
import static com.example.testtask.util.DataOpenApi.MESSAGE_NOT_VALID;
import static com.example.testtask.util.DataOpenApi.SUMMARY_ADD_COMMENTS;
import static com.example.testtask.util.DataOpenApi.SUMMARY_AUTH_USER_COMMENTS;
import static com.example.testtask.util.DataOpenApi.SUMMARY_COMMENTS;
import static com.example.testtask.util.DataOpenApi.SUMMARY_TASK_COMMENTS;
import static com.example.testtask.util.DataOpenApi.TAG_COMMENTS;
import static com.example.testtask.util.DataOpenApi.TAG_COMMENTS_DESCRIPTION;
import static com.example.testtask.util.DataOpenApi.URL_MAIN_COMMENTS;

@Tag(name = TAG_COMMENTS, description = TAG_COMMENTS_DESCRIPTION)
@RestController
@RequestMapping(URL_MAIN_COMMENTS)
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = SUMMARY_ADD_COMMENTS,
            tags = TAG_COMMENTS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_CREATE,
                    description = MESSAGE_ADD_COMMENT
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @PostMapping(value = "/{task_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long task_id,
            @Valid @RequestBody CommentRequest commentRequest
    ) {
        return new ResponseEntity<>(
                commentService.createComment(
                        task_id,
                        commentRequest.getDescription()),
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = SUMMARY_COMMENTS,
            tags = TAG_COMMENTS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_OK,
                    description = MESSAGE_COMMENTS
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        return new ResponseEntity<>(
                commentService.getAllComments(pageNumber, pageSize),
                HttpStatus.OK
        );
    }


    @Operation(
            summary = SUMMARY_TASK_COMMENTS,
            tags = TAG_COMMENTS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_OK,
                    description = MESSAGE_COMMENTS
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<List<CommentResponse>> getAllTaskComments(
            @PathVariable Long id,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        return new ResponseEntity<>(
                commentService.getAllCommentsTask(id, pageNumber, pageSize),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = SUMMARY_AUTH_USER_COMMENTS,
            tags = TAG_COMMENTS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_OK,
                    description = MESSAGE_COMMENTS
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @GetMapping("/tasks")
    public ResponseEntity<List<CommentResponse>> getAllCommentsAuthUser(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        return new ResponseEntity<>(
                commentService.getAllCommentsAuthUser(pageNumber, pageSize),
                HttpStatus.OK
        );
    }
}
