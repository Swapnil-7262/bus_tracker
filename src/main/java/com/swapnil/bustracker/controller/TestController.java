package com.swapnil.bustracker.controller;

import com.swapnil.bustracker.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping

    public ApiResponse<String> testApi() {
        return new ApiResponse<>(true, "API is working", "Hello World");
    }

    @GetMapping("/error")
    public String error() {
        throw new RuntimeException("Something went wrong!");
    }

}
