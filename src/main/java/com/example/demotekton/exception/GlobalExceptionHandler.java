package com.example.demotekton.exception;

import com.example.demotekton.entity.CallLog;
import com.example.demotekton.service.CallLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final CallLogService callLogService;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingParams(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String paramName = ex.getParameterName();
        String message = "Missing required parameter: " + paramName;

        return getObjectResponseEntity(request, message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String paramName = ex.getName();
        String message = "Invalid value for parameter '" + paramName;

        return getObjectResponseEntity(request, message);
    }

    @ExceptionHandler(BusinessCalculationException.class)
    public ResponseEntity<Object> handleBusinessError(BusinessCalculationException ex, HttpServletRequest request) {
        callLogService.log(CallLog.builder()
                .timestamp(LocalDateTime.now())
                .endpoint(request.getRequestURI())
                .parameters("num1=" + ex.getNum1() + ", num2=" + ex.getNum2())
                .response(ex.getMessage())
                .error(true)
                .build());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", ex.getMessage()));
    }

    private ResponseEntity<Object> getObjectResponseEntity(HttpServletRequest request, String message) {
        callLogService.log(CallLog.builder()
                .timestamp(LocalDateTime.now())
                .endpoint(request.getRequestURI())
                .parameters(request.getQueryString() != null ? request.getQueryString() : "")
                .response(message)
                .error(true)
                .build());

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
