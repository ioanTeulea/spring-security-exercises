package com.example.project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

    @GetMapping("/ciao")
    public String ciao() { return "Ciao!"; }

    @PostMapping("/endpoint-a")
    public String postEndpointA() {
        return "Works!";
    }
    @GetMapping("/endpoint-a")
    public String getEndpointA() {
        return "Works!";
    }
    @GetMapping("/common/a")
    public String commonA() {
        return "Common A!";
    }
    @GetMapping("/common/b")
    public String commonB() {
        return "Common B!";
    }
    @GetMapping("/product/{code}")
    public String productCode(@PathVariable String code) {
        return code;
    }
}
