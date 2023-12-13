package com.example.testtask.service;

import com.example.testtask.auth.CustomUserDetails;
import com.example.testtask.dto.TaskRequest;
import com.example.testtask.dto.TaskResponse;
import com.example.testtask.exception.NotCorrectPageException;
import com.example.testtask.exception.NotExistsInDBException;
import com.example.testtask.model.Priority;
import com.example.testtask.model.Status;
import com.example.testtask.model.Task;
import com.example.testtask.model.User;
import com.example.testtask.repository.PriorityRepository;
import com.example.testtask.repository.StatusRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final PriorityRepository priorityRepository;

    private final ModelMapper modelMapper;


    @Override
    public TaskResponse createTask(TaskRequest taskRequest) {
        Status status = statusRepository.findByName(taskRequest.getStatus());
        Priority priority = priorityRepository.findByName(taskRequest.getPriority());

        validTask(taskRequest, status, priority);

        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(status)
                .priority(priority)
                .author(getAuthUser())
                .executor(getUser(taskRequest.getExecutor()))
                .build();

        Task savedTask = taskRepository.save(task);
        return modelMapper.map(savedTask, TaskResponse.class);
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task foundedTask = taskRepository.findById(id).orElse(null);
        if (foundedTask == null) {
            throw new NotExistsInDBException(
                    String.format("Задача с id %s отсутствует в базе данных", id)
            );
        }

        if (!getAuthUser().equals(foundedTask.getAuthor())) {
            throw new NotExistsInDBException(
                    String
                            .format(
                                    "Редактирование задачи разрешено только автору: %s",
                                    foundedTask
                                            .getAuthor()
                                            .getEmail()
                            )
            );
        }

        Status status = statusRepository.findByName(taskRequest.getStatus());
        Priority priority = priorityRepository.findByName(taskRequest.getPriority());
        validTask(taskRequest, status, priority);

        Task task = Task.builder()
                .id(id)
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(status)
                .priority(priority)
                .author(getAuthUser())
                .executor(getUser(taskRequest.getExecutor()))
                .build();

        Task updatedTask = taskRepository.save(task);
        return modelMapper.map(updatedTask, TaskResponse.class);
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        Task foundedTask = taskRepository.findById(id).orElse(null);
        if (foundedTask == null) {
            throw new NotExistsInDBException(
                    String.format("Задача с id %s отсутствует в базе данных", id)
            );
        }

        return modelMapper.map(foundedTask, TaskResponse.class);
    }

    @Override
    public TaskResponse deleteTaskById(Long id) {
        Task foundedTask = taskRepository.findById(id).orElse(null);

        if (foundedTask == null) {
            throw new NotExistsInDBException(
                    String.format("Задача с id %s отсутствует в базе данных", id)
            );
        }

        if (!getAuthUser().equals(foundedTask.getAuthor())) {
            throw new NotExistsInDBException(
                    String
                            .format(
                                    "Удаление задачи разрешено только автору: %s",
                                    foundedTask
                                            .getAuthor()
                                            .getEmail()
                            )
            );
        }

        taskRepository.deleteById(id);
        return modelMapper.map(foundedTask, TaskResponse.class);
    }

    @Override
    public List<TaskResponse> getTasksAuthUser(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Task> tasks = taskRepository.findTasksByAuthor_Id(
                getAuthUser().getId(),
                pageable
        );

        validPage(pageNumber, tasks);

        return tasks.get()
                .map(task -> modelMapper.map(task, TaskResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getTasksUser(Long id, int pageNumber, int pageSize) {
        Optional<User> author = userRepository.findById(id);

        if (author.isEmpty()) {
            throw new NotExistsInDBException(
                    String.format("Автор с указанным id ( %s ) отсутствует в базе данных", id)
            );
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Task> tasks = taskRepository.findTasksByAuthor_Id(
                id,
                pageable
        );

        validPage(pageNumber, tasks);

        return tasks.get()
                .map(task -> modelMapper.map(task, TaskResponse.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<TaskResponse> getTasksExecutor(Long id, int pageNumber, int pageSize) {
        Optional<User> executor = userRepository.findById(id);
        if (executor.isEmpty()) {
            throw new NotExistsInDBException(
                    String.format("Исполнитель с указанным id ( %s ) отсутствует в базе данных", id)
            );
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Task> tasks = taskRepository.findTasksByExecutor_Id(
                id,
                pageable
        );

        validPage(pageNumber, tasks);

        return tasks.get()
                .map(task -> modelMapper.map(task, TaskResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse changeStatusTask(Long id, String status) {
        Task foundedTask = taskRepository.findById(id).orElse(null);
        if (foundedTask == null) {
            throw new NotExistsInDBException(
                    String.format("Задача с id %s отсутствует в базе данных", id)
            );
        }

        User authUser = getAuthUser();
        if (
                !(authUser.equals(foundedTask.getAuthor()) ||
                        authUser.equals(foundedTask.getExecutor()))
        ) {
            throw new NotExistsInDBException(
                    String
                            .format(
                                    "Изменение статуса задачи разрешено автору или исполнителю: %s, %s",
                                    foundedTask
                                            .getAuthor()
                                            .getEmail(),
                                    authUser.getEmail()
                            )
            );
        }


        Status newStatus = statusRepository.findByName(status);
        if (newStatus == null) {
            throw new NotExistsInDBException(
                    String.format(
                            "{ Указанный статус %s отсутствует в базе данных, ",
                            status
                    )
            );
        }

        foundedTask.setStatus(newStatus);

        Task updatedTask = taskRepository.save(foundedTask);
        return modelMapper.map(updatedTask, TaskResponse.class);
    }

    @Override
    public TaskResponse changeExecutorTask(Long id, String email) {
        Task foundedTask = taskRepository.findById(id).orElse(null);
        if (foundedTask == null) {
            throw new NotExistsInDBException(
                    String.format("Задача с id %s отсутствует в базе данных", id)
            );
        }

        User executor = getUser(email);
        if (executor == null) {
            throw new NotExistsInDBException(
                    String.format("Исполнитель с указанным id ( %s ) отсутствует в базе данных", id)
            );
        }

        User authUser = getAuthUser();
        if (!authUser.equals(foundedTask.getAuthor())) {
            throw new NotExistsInDBException(
                    String.format(
                            "Назначение исполнителя задачи разрешено только автору: %s",
                            foundedTask
                                    .getAuthor()
                                    .getEmail()
                    )
            );
        }

        foundedTask.setExecutor(executor);
        Task updatedTask = taskRepository.save(foundedTask);
        return modelMapper.map(updatedTask, TaskResponse.class);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    private User getAuthUser() {
        CustomUserDetails userDetails = (CustomUserDetails) (SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
        return getUser(userDetails.getEmail());
    }

    private void validTask(TaskRequest taskRequest, Status status, Priority priority) {
        if (status == null && priority == null) {
            throw new NotExistsInDBException(
                    String.format(
                            "{ Указанный статус %s отсутствует в базе данных, ",
                            taskRequest.getStatus()
                    ) +
                            String.format(
                                    "Указанный приоритет %s отсутствует в базе данных }",
                                    taskRequest.getPriority()
                            )
            );
        } else if (status != null && priority == null) {
            throw new NotExistsInDBException(
                    String.format(
                            "Указанный приоритет %s отсутствует в базе данных",
                            taskRequest.getPriority()
                    )
            );
        } else if (status == null) {
            throw new NotExistsInDBException(
                    String.format(
                            "Указанный приоритет %s отсутствует в базе данных",
                            taskRequest.getPriority()
                    )
            );
        }
    }

    private void validPage(int pageNumber, Page<Task> tasks) {
        if (pageNumber > tasks.getTotalPages()) {
            throw new NotCorrectPageException(
                    String.format("Номер страницы больше общего количества страниц %d",
                            tasks.getTotalPages()));
        }

        if (tasks.getContent().isEmpty()) {
            throw new NotExistsInDBException("Список задач в базе данных пуст");
        }
    }
}
