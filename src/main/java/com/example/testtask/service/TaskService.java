package com.example.testtask.service;

import com.example.testtask.dto.TaskRequest;
import com.example.testtask.dto.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest taskRequest);

    TaskResponse getTaskById(Long id);

    TaskResponse updateTask(Long id, TaskRequest taskRequest);

    TaskResponse deleteTaskById(Long id);

    List<TaskResponse> getTasksAuthUser(int pageNumber, int pageSize);

    List<TaskResponse> getTasksUser(Long id, int pageNumber, int pageSize);

    List<TaskResponse> getTasksExecutor(Long id, int pageNumber, int pageSize);

    TaskResponse changeStatusTask(Long id, String status);

    TaskResponse changeExecutorTask(Long id, String email);
}
