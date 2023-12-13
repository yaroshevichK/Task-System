package com.example.testtask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.example.testtask.util.DataValid.DESCRIPTION_LENGTH;
import static com.example.testtask.util.DataValid.MAX_DESCRIPTION;
import static com.example.testtask.util.DataValid.MAX_PRIORITY;
import static com.example.testtask.util.DataValid.MAX_STATUS;
import static com.example.testtask.util.DataValid.MESSAGE_EMAIL;
import static com.example.testtask.util.DataValid.MESSAGE_NOT_BLANK;
import static com.example.testtask.util.DataValid.MIN_PRIORITY;
import static com.example.testtask.util.DataValid.MIN_STATUS;
import static com.example.testtask.util.DataValid.PRIORITY_LENGTH;
import static com.example.testtask.util.DataValid.STATUS_LENGTH;
import static com.example.testtask.util.DataValid.TASK_TITLE_LENGTH;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest implements Serializable {

    @NotBlank(message = MESSAGE_NOT_BLANK)
    @Size(min = MIN_PRIORITY, max = MAX_PRIORITY,
            message = TASK_TITLE_LENGTH)
    private String title;

    @Size(max = MAX_DESCRIPTION,
            message = DESCRIPTION_LENGTH)
    private String description;

    @NotBlank(message = MESSAGE_NOT_BLANK)
    @Size(min = MIN_STATUS, max = MAX_STATUS,
            message = STATUS_LENGTH)
    private String status;

    @NotBlank(message = MESSAGE_NOT_BLANK)
    @Size(min = MIN_PRIORITY, max = MAX_PRIORITY,
            message = PRIORITY_LENGTH)
    private String priority;

    @NotBlank(message = MESSAGE_NOT_BLANK)
    @Email(message = MESSAGE_EMAIL)
    private String executor;

}
