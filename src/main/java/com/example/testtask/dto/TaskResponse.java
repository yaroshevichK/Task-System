package com.example.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse implements Serializable {
    private Long id;
    private String title;
    private String description;
    private StatusResponse status;
    private PriorityResponse priority;
    private UserResponse author;
    private UserResponse executor;

}
