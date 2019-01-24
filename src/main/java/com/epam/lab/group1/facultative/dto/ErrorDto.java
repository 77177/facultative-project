package com.epam.lab.group1.facultative.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class ErrorDto {

    private String type;
    private String message;

    public ErrorDto(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
