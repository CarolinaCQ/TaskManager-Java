package com.project.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskGetDto {

    private String title;
    private String description;
    private LocalDate finishDate;
    private Integer duration;

}
