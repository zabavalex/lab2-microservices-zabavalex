package com.rsoilab2.warranty.contollers;

import com.rsoilab2.warranty.models.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@ControllerAdvice
public class WarrantyErrorsController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MessageResponse> notFoundHandler(EntityNotFoundException e){
        String message = String.format("%s", e.getMessage());
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> methodArgumentNotValidHandler(MethodArgumentNotValidException e){
        String message = String.format("%s", e.getMessage());
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageResponse> runtimeHandler(RuntimeException e){
        String message = String.format("%s", e.getMessage());
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
