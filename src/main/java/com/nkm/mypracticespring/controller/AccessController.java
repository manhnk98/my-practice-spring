package com.nkm.mypracticespring.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class AccessController {

    @PostMapping("/shop/signup")
    public Object hihi() {

        return "ok";
    }

}
