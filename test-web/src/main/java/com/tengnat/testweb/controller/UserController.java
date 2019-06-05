package com.tengnat.testweb.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("test")
    public void user(){
        int n =3;
    }
}
