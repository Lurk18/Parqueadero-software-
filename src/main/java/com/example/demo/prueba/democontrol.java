package com.example.demo.prueba;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class democontrol {
    @PostMapping(value = "prueba")
    public String welcome()
    {
        return "welcome from secure endpoint";
    }

}
