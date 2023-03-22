package com.project.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostUpdateDto {

    private String username;
    private String oldPassword;
    private String newPassword;
}
