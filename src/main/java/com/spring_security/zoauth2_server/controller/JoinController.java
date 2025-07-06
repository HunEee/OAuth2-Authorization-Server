package com.spring_security.zoauth2_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring_security.zoauth2_server.dto.UserDTO;
import com.spring_security.zoauth2_server.service.UserService;

@Controller
public class JoinController {

    private final UserService userService;

    public JoinController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/join")
    public String joinPage() {
        return "joinPage";
    }

    @PostMapping("/join")
    @ResponseBody
    public String join(UserDTO dto) {
        userService.join(dto);
        return "ok";
    }
}
