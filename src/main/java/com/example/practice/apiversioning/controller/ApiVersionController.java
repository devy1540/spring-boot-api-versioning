package com.example.practice.apiversioning.controller;

import com.example.practice.apiversioning.global.ApiVersion;
import com.example.practice.apiversioning.global.ApiVersionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiVersionController {

    private final ApiVersionProperties apiVersionProperties;

    @GetMapping("method/01")
    public ResponseEntity<String> getTest01() {
        return ResponseEntity.ok("hello world");
    }

    @ApiVersion("1")
    @GetMapping("method/02")
    public ResponseEntity<String> getTest02() {
        return ResponseEntity.ok("call v1, method getTest02");
    }

    @ApiVersion("2")
    @GetMapping("method/02")
    public ResponseEntity<String> getTest03() {
        return ResponseEntity.ok("call v2, method getTest03");
    }
}
