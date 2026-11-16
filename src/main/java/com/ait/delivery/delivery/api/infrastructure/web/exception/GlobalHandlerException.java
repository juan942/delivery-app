package com.ait.delivery.delivery.api.infrastructure.web.exception;

import com.ait.delivery.delivery.api.domain.exception.*;
import com.ait.delivery.delivery.api.infrastructure.web.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler({OrderNotFoundException.class, DriverNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(DomainException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidOrderStateTransitionException.class)
    public ResponseEntity<ApiError> handleTransition(InvalidOrderStateTransitionException ex, HttpServletRequest request) {
        String details = "From: " + ex.getFrom().name() + " To: " + ex.getTo().name();
        return build(HttpStatus.CONFLICT, ex.getMessage(), request, details);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiError> handleBusinessRule(BusinessRuleException ex, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ApiError> handleInvalidArgument(InvalidArgumentException ex, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request, null);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiError> handleDomain(InvalidArgumentException ex, HttpServletRequest request) {
        return build(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage(), request, null);
    }

    private ResponseEntity<ApiError> build (HttpStatusCode status, String message, HttpServletRequest request, String details) {
        HttpStatus resolved = HttpStatus.resolve(status.value());
        String error = resolved != null ? resolved.getReasonPhrase() : "Error";
        ApiError body = new ApiError(Instant.now(), status.value(), error, message, request.getRequestURI(), details);
        return ResponseEntity.status(status).body(body);
    }
}
