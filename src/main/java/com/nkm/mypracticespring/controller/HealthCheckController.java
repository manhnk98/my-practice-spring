package com.nkm.mypracticespring.controller;

import com.nkm.mypracticespring.dto.ResponseDto;
import com.nkm.mypracticespring.enums.MessageEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping
    public ResponseDto<?> healthCheck() {
        return new ResponseDto<>("ok", MessageEnum.SUCCESS);
    }

}
