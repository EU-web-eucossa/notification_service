package com.eucossa.notification_service.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 24/06/2022
 */

@RestControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorDetails>> ConstraintViolationErrors(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<ErrorDetails> errors = new ArrayList<>(constraintViolations.size());
        constraintViolations.forEach(constraintViolation -> errors.add(ErrorDetails.builder()
                .timestamp(Date.valueOf(LocalDate.now()))
                .details(constraintViolation.getInvalidValue().toString())
                .message(constraintViolation.getMessage())
                .build()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MailParseException.class)
    public ResponseEntity<ErrorDetails> mailParseExceptionHandler(MailParseException mailParseException) {
        return ResponseEntity.badRequest().body(generateErrorDetails(mailParseException));
    }

    @ExceptionHandler(MailAuthenticationException.class)
    public ResponseEntity<ErrorDetails> mailAuthenticationException(MailAuthenticationException mailAuthenticationException) {
        return ResponseEntity.badRequest().body(generateErrorDetails(mailAuthenticationException));
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<ErrorDetails> mailMailSendException(MailSendException mailSendException) {
        return ResponseEntity.badRequest().body(generateErrorDetails(mailSendException));
    }

    @ExceptionHandler(EmptyEmailRecipientAddressException.class)
    public ResponseEntity<ErrorDetails> exception(EmptyEmailRecipientAddressException exception) {
        return ResponseEntity.badRequest().body(generateErrorDetails(exception));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> exception(Exception exception) {
        return ResponseEntity.internalServerError().body(generateErrorDetails(exception));
    }

    private ErrorDetails generateErrorDetails(Exception exception) {
        return ErrorDetails.builder()
                .message(exception.getMessage())
                .details(Arrays.toString(exception.getStackTrace()))
                .timestamp(Date.valueOf(LocalDate.now()))
                .build();
    }
}
