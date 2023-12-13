package com.example.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse implements Serializable {

    private Long id;
    private String description;
    private UserResponse author;
    private TaskComment task;
}
