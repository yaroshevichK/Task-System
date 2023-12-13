package com.example.testtask.util;

public final class DataValid {
    public static final int MIN_PASSWORD = 3;
    public static final int MAX_PASSWORD = 30;
    public static final int MIN_NAME = 3;
    public static final int MAX_NAME = 30;
    public static final int MAX_DESCRIPTION = 100_000;
    public static final int MIN_PRIORITY = 3;
    public static final int MAX_PRIORITY = 30;
    public static final int MIN_STATUS = 3;
    public static final int MAX_STATUS = 30;
    public static final String MESSAGE_NOT_BLANK = "{not-blank}";
    public static final String PASSWORD_LENGTH = "{password.length}";
    public static final String NAME_LENGTH = "{name.length}";
    public static final String DESCRIPTION_LENGTH = "{description}";
    public static final String STATUS_LENGTH = "{status.length}";
    public static final String PRIORITY_LENGTH = "{priority.length}";
    public static final String TASK_TITLE_LENGTH = "{title.length}";
    public static final String ERROR_AUTH = "Authorization error (not correct login or password)";
    public static final String ERROR_FORBIDDEN = "You are not authorization.";
    public static final String EMAIL_NOT_UNIQ = "Email пользователя не уникальный: %s";
    public static final String MESSAGE_EMAIL = "{email}";
    public static final String USER_NOT_FOUND = "User not found";

    public static final String TASK_NOT_FOUND = "Task with id %s not found in database";
    public static final String PAGE_NOT_CORRECT = "Not correct page, which you choose. Total count pages are %d";
    public static final String NO_COMMENTS = "Database has not comments in database";

}
