package com.example.demotekton.exception;

import com.example.demotekton.entity.CallLog;
import com.example.demotekton.service.CallLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private CallLogService callLogService;

    @Mock
    private HttpServletRequest request;

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler(callLogService);
    }

    @Test
    void handleMissingParams_shouldReturnBadRequestWithErrorMessage() {
        MissingServletRequestParameterException ex =
                new MissingServletRequestParameterException("param", "String");

        when(request.getRequestURI()).thenReturn("/test");
        when(request.getQueryString()).thenReturn(null);

        ResponseEntity<Object> response = exceptionHandler.handleMissingParams(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((Map<?, ?>) response.getBody()).get("error").toString().contains("Missing required parameter: param"));

        verify(callLogService).log(any(CallLog.class));
    }

    @Test
    void handleTypeMismatch_shouldReturnBadRequestWithErrorMessage() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("param");
        when(request.getRequestURI()).thenReturn("/test");
        when(request.getQueryString()).thenReturn("param=abc");

        ResponseEntity<Object> response = exceptionHandler.handleTypeMismatch(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((Map<?, ?>) response.getBody()).get("error").toString().contains("Invalid value for parameter 'param"));

        verify(callLogService).log(any(CallLog.class));
    }

    @Test
    void handleBusinessError_shouldReturnInternalServerErrorAndLog() {
        BusinessCalculationException ex = new BusinessCalculationException("Error occurred", 5.0, 2.0);
        when(request.getRequestURI()).thenReturn("/calculate");

        ResponseEntity<Object> response = exceptionHandler.handleBusinessError(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error occurred", ((Map<?, ?>) response.getBody()).get("error"));

        verify(callLogService).log(any(CallLog.class));
    }

}