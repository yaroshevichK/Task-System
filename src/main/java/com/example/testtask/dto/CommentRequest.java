package com.example.testtask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.example.testtask.util.DataValid.DESCRIPTION_LENGTH;
import static com.example.testtask.util.DataValid.MAX_DESCRIPTION;
import static com.example.testtask.util.DataValid.MESSAGE_NOT_BLANK;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest implements Serializable {


    @NotBlank(message = MESSAGE_NOT_BLANK)
    @Size(max = MAX_DESCRIPTION,
            message = DESCRIPTION_LENGTH)
    private String description;
}
