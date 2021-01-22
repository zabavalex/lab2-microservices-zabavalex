package com.rsoilab2.store.exceptions;

public class OrderServiceGrpcException extends RuntimeException {
    public OrderServiceGrpcException(String message) {
        super(message);
    }
}
