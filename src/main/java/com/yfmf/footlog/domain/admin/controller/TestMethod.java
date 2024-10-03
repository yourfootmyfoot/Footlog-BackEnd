package com.yfmf.footlog.domain.admin.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/admin")
@RestController
public class TestMethod {

    @GetMapping("/test")
    public String testMethod() {
        return "test success";
    }
}

