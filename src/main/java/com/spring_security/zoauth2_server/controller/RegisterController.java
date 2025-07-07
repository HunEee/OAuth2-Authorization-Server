package com.spring_security.zoauth2_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring_security.zoauth2_server.dto.RegisterDTO;
import com.spring_security.zoauth2_server.entity.RegisterEntity;
import com.spring_security.zoauth2_server.service.RegisterService;

@Controller
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public RegisterEntity register(RegisterDTO dto) {
        return registerService.register(dto);
    }
    
}
