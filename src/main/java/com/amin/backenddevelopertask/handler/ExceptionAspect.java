package com.amin.backenddevelopertask.handler;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

@Aspect
@Component
public class ExceptionAspect {

    @Pointcut("execution(* com.amin.backenddevelopertask.controller..*(..))")  // Kontrollerin paketi
    public void controllerMethods() {}

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public ResponseEntity<Object> handleException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validationException = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = validationException.getBindingResult();

            StringBuilder errors = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
            });

            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }

        if (ex.getClass().isAnnotationPresent(ResponseStatus.class)) {
            ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
            status = responseStatus.value();
        }

        return new ResponseEntity<>(ex.getMessage(), status);
    }
}
