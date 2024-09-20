package com.nkm.mypracticespring.aspect;

import com.nkm.mypracticespring.dto.common.DataRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataRequestAspect {

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void validateRequest(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof DataRequest dataReq) {
                dataReq.validate();
            }
        }
    }

}
