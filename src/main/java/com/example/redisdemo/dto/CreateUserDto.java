package com.example.redisdemo.dto;

import com.example.redisdemo.model.User;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private String username;
    private String password;

    public User toEntity(CreateUserDto createUserDto){
        return User.builder()
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .build();

    }
}
