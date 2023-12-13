package com.example.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriorityResponse implements Serializable {
    private Integer id;
    private String name;
}
