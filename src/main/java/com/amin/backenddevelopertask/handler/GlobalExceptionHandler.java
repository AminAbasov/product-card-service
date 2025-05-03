//package com.amin.backenddevelopertask.handler;
//
//import com.fasterxml.jackson.annotation.JsonView;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.ConstraintViolationException;
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    // Validation xətaları üçün
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            String fieldName = error.getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return ResponseEntity.badRequest().body(errors);
//    }
//
//    // Constraint violation xətaları üçün
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getConstraintViolations().forEach(violation -> {
//            String fieldName = violation.getPropertyPath().toString();
//            String errorMessage = violation.getMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return ResponseEntity.badRequest().body(errors);
//    }
//
//    // 404 xətaları üçün
//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleNotFound(NoHandlerFoundException ex, HttpServletRequest request) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body("Endpoint tapılmadı: " + request.getRequestURI());
//    }
//
//    // Ümumi xətalar üçün
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<String> handleAllExceptions(Exception ex, HttpServletRequest request) {
//        // Swagger endpointlərini ignore et
//        if (request.getRequestURI().contains("/swagger") ||
//                request.getRequestURI().contains("/v2/api-docs") ||
//                request.getRequestURI().contains("/webjars")) {
//            return null;
//        }
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Xəta baş verdi: " + ex.getMessage());
//    }
//}