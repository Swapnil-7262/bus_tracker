package com.swapnil.bustracker.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
}
