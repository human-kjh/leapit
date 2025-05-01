package com.example.leapit._core.error;

import com.example.leapit._core.error.ex.Exception400;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

@Aspect
@Component
public class GlobalValidationHandler {


    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void badRequestAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {


            if (arg instanceof Errors) {
                System.out.println("error 400 처리 필요함");
                Errors errors = (Errors) arg;

                if (errors.hasErrors()) {
                    List<FieldError> fErrors = errors.getFieldErrors();

                    for (FieldError fieldError : fErrors) {
                        throw new Exception400(fieldError.getField() + ":" + fieldError.getDefaultMessage());
                    }

                }
            }
        }
    }

}