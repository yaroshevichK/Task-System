package com.example.testtask.util;

public final class DataOpenApi {

    public static final String DEV_NAME = "Katerina";
    public static final String DEV_EMAIL = "yaroshevich.katerina.v@gmail.com";
    public static final String URL_UI = "http://localhost:8080";
    public static final String TITLE_UI = "Task Management System API";
    public static final String DESCRIPTION_UI = """
            Проект демонстрирует систему управления задачами.
             Проект реализует авторизацию с использованием JWT token. Для выполнения запроса требуется добавить полученный токен.
             Система обеспечивает создание, редактирование, удаление и просмотр задач, изменение статуса и назначение исполнителей задачи.
             API позволяет добавлять и просматривать комментарии к задачам.""";

    public static final String KEY_SECRET = "${application.jwt.secret}";
    public static final int TIME_TOKEN = 1000 * 60 * 24;
    public static final String HEADER_AUTH = "Authorization";
    public static final String BEARER_AUTH = "Bearer ";
    public static final String JWT_SCHEMA = "JWT authorization";
    public static final String BEARER = "bearer";
    public static final String JWT_FORMAT = "JWT";
    public static final String JWT_REQ = "JWT authorization";
    public static final String READ = "read";
    public static final String WRITE = "write";

    public static final String TAG_MAIN_AUTH = "Authorization and authentication";
    public static final String TAG_AUTH_DESCRIPTION = "Authorization and authentication API";
    public static final String TAG_TASKS = "Tasks";
    public static final String TAG_TASKS_DESCRIPTION = "The tasks API";
    public static final String TAG_COMMENTS = "Comments";
    public static final String TAG_COMMENTS_DESCRIPTION = "The comments API";
    public static final String SUMMARY_REG = "Registration";
    public static final String SUMMARY_AUTH = "Authentication";
    public static final String SUMMARY_ADD_TASKS = "Add a new task";
    public static final String SUMMARY_EDIT_TASKS = "Edit a task";
    public static final String SUMMARY_FIND_TASK = "Find task by id";
    public static final String SUMMARY_DELETE_TASK = "Delete task by id";
    public static final String SUMMARY_TASKS_AUTH_USER = "Get all tasks authored user";
    public static final String SUMMARY_TASKS_USER = "Get all tasks choose user";
    public static final String SUMMARY_TASKS_EXECUTOR = "Get all tasks executor";
    public static final String SUMMARY_TASK_STATUS = "Change a status task";
    public static final String SUMMARY_TASK_EXECUTOR = "Appoint an executor";
    public static final String SUMMARY_ADD_COMMENTS = "Add a new comment in a task";
    public static final String SUMMARY_COMMENTS = "Get list all comments";
    public static final String SUMMARY_TASK_COMMENTS = "Get list all comments task";
    public static final String SUMMARY_AUTH_USER_COMMENTS = "Get list all comments authorized user";
    public static final String URL_MAIN_AUTH = "/api/v1/auth";
    public static final String URL_REG = "/register";
    public static final String URL_AUTH = "/authenticate";
    public static final String URL_MAIN_TASKS = "/api/v1/tasks";
    public static final String URL_MAIN_COMMENTS = "/api/v1/comments";
    public static final String CODE_CREATE = "201";
    public static final String CODE_OK = "200";
    public static final String CODE_UNIQ = "409";
    public static final String CODE_NOT_VALID = "400";
    public static final String CODE_NOT_AUTH = "401";
    public static final String MESSAGE_CREATE_USER = "User added";
    public static final String MESSAGE_CREATE_TASK = "The task added";
    public static final String MESSAGE_EMAIL_NOT_UNIQ = "Database has user with same email";
    public static final String MESSAGE_NOT_VALID = "Not valid request data";
    public static final String MESSAGE_USER_AUTH = "User authorized";
    public static final String MESSAGE_USER_NOT_AUTH = "Authorization error (not correct login or password)";
    public static final String MESSAGE_EDIT_TASK = "The task edited";
    public static final String MESSAGE_FIND_TASK = "The task got";
    public static final String MESSAGE_DELETE_TASK = "Task deleted";
    public static final String MESSAGE_TASKS = "The tasks got";
    public static final String MESSAGE_TASK_STATUS = "Status in task changed";
    public static final String MESSAGE_TASK_EXECUTOR = "Executor in task changed";
    public static final String MESSAGE_ADD_COMMENT = "The comment added";
    public static final String MESSAGE_COMMENTS = "The list comments got";

    public static final String DEFAULT_PAGE_NUMBER = "1";
    public static final String DEFAULT_PAGE_SIZE = "10";
}
