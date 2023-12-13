package com.example.testtask.controller;

import com.example.testtask.dto.TaskRequest;
import com.example.testtask.dto.TaskResponse;
import com.example.testtask.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import static com.example.testtask.util.DataOpenApi.MESSAGE_CREATE_TASK;
import static com.example.testtask.util.DataOpenApi.MESSAGE_DELETE_TASK;
import static com.example.testtask.util.DataOpenApi.MESSAGE_EDIT_TASK;
import static com.example.testtask.util.DataOpenApi.MESSAGE_FIND_TASK;
import static com.example.testtask.util.DataOpenApi.MESSAGE_NOT_VALID;
import static com.example.testtask.util.DataOpenApi.MESSAGE_TASKS;
import static com.example.testtask.util.DataOpenApi.MESSAGE_TASK_EXECUTOR;
import static com.example.testtask.util.DataOpenApi.MESSAGE_TASK_STATUS;
import static com.example.testtask.util.DataOpenApi.SUMMARY_ADD_TASKS;
import static com.example.testtask.util.DataOpenApi.SUMMARY_DELETE_TASK;
import static com.example.testtask.util.DataOpenApi.SUMMARY_EDIT_TASKS;
import static com.example.testtask.util.DataOpenApi.SUMMARY_FIND_TASK;
import static com.example.testtask.util.DataOpenApi.SUMMARY_TASKS_AUTH_USER;
import static com.example.testtask.util.DataOpenApi.SUMMARY_TASKS_EXECUTOR;
import static com.example.testtask.util.DataOpenApi.SUMMARY_TASKS_USER;
import static com.example.testtask.util.DataOpenApi.SUMMARY_TASK_EXECUTOR;
import static com.example.testtask.util.DataOpenApi.SUMMARY_TASK_STATUS;
import static com.example.testtask.util.DataOpenApi.TAG_TASKS;
import static com.example.testtask.util.DataOpenApi.TAG_TASKS_DESCRIPTION;
import static com.example.testtask.util.DataOpenApi.URL_MAIN_TASKS;

@Tag(name = TAG_TASKS, description = TAG_TASKS_DESCRIPTION)
@RestController
@RequestMapping(URL_MAIN_TASKS)
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(
            summary = SUMMARY_ADD_TASKS,
            tags = TAG_TASKS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_CREATE,
                    description = MESSAGE_CREATE_TASK
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest taskRequest
    ) {
        return new ResponseEntity<>(
                taskService.createTask(taskRequest),
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = SUMMARY_EDIT_TASKS,
            tags = TAG_TASKS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_OK,
                    description = MESSAGE_EDIT_TASK
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequest taskRequest
    ) {
        return new ResponseEntity<>(
                taskService.updateTask(id, taskRequest),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = SUMMARY_FIND_TASK,
            tags = TAG_TASKS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_OK,
                    description = MESSAGE_FIND_TASK
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @PostMapping(value = "/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(
                taskService.getTaskById(id),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = SUMMARY_DELETE_TASK,
            tags = TAG_TASKS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_OK,
                    description = MESSAGE_DELETE_TASK
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TaskResponse> deleteTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(
                taskService.deleteTaskById(id),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = SUMMARY_TASKS_AUTH_USER,
            tags = TAG_TASKS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_OK,
                    description = MESSAGE_TASKS
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasksAuthUser(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        return new ResponseEntity<>(
                taskService.getTasksAuthUser(pageNumber, pageSize),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = SUMMARY_TASKS_USER,
            tags = TAG_TASKS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_OK,
                    description = MESSAGE_TASKS
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<List<TaskResponse>> getTasksUser(
            @PathVariable Long id,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        return new ResponseEntity<>(
                taskService.getTasksUser(id, pageNumber, pageSize),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = SUMMARY_TASKS_EXECUTOR,
            tags = TAG_TASKS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_OK,
                    description = MESSAGE_TASKS
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_TASKS
            )
    })
    @GetMapping(value = "executor/{id}")
    public ResponseEntity<List<TaskResponse>> getTasksExecutor(
            @PathVariable Long id,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        return new ResponseEntity<>(
                taskService.getTasksExecutor(id, pageNumber, pageSize),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = SUMMARY_TASK_STATUS,
            tags = TAG_TASKS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_OK,
                    description = MESSAGE_TASK_STATUS
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @PutMapping(value = "/{id}/status/{status}")
    public ResponseEntity<TaskResponse> changeStatusTask(
            @PathVariable Long id,
            @PathVariable String status
    ) {
        return new ResponseEntity<>(
                taskService.changeStatusTask(id, status),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = SUMMARY_TASK_EXECUTOR,
            tags = TAG_TASKS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CODE_OK,
                    description = MESSAGE_TASK_EXECUTOR
            ),
            @ApiResponse(
                    responseCode = CODE_NOT_VALID,
                    description = MESSAGE_NOT_VALID
            )
    })
    @PutMapping(value = "/{id}/executor/{email}")
    public ResponseEntity<TaskResponse> changeExecutorTask(
            @PathVariable Long id,
            @PathVariable String email
    ) {
        return new ResponseEntity<>(
                taskService.changeExecutorTask(id, email),
                HttpStatus.OK
        );
    }
}
