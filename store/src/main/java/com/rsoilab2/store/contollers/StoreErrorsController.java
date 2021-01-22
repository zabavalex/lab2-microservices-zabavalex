package com.rsoilab2.store.contollers;
import com.rsoilab2.store.exceptions.ItemNotAvailableException;
import com.rsoilab2.store.exceptions.OrderServiceGrpcException;
import com.rsoilab2.store.exceptions.WarehouseServiceGrpcException;
import com.rsoilab2.store.exceptions.WarrantyServiceGrpcException;
import com.rsoilab2.store.models.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@ControllerAdvice
public class StoreErrorsController {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<MessageResponse> notFoundHandler(EntityNotFoundException e){
        String message = String.format("%s", e.getMessage());
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<MessageResponse> methodArgumentNotValidHandler(MethodArgumentNotValidException e){
        String message = String.format("%s", e.getMessage());
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ItemNotAvailableException.class)
    private ResponseEntity<MessageResponse> itemNotAvailableHandler(ItemNotAvailableException e){
        String message = String.format("%s", e.getMessage());
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({WarrantyServiceGrpcException.class, WarehouseServiceGrpcException.class, OrderServiceGrpcException.class})
    private ResponseEntity<MessageResponse> ServiceGrpcHandler(RuntimeException e){
        String message = String.format("%s", e.getMessage());
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<MessageResponse> runtimeHandler(RuntimeException e){
        String message = String.format("%s", e.getMessage());
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
