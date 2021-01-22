package com.rsoilab2.order.exceptions;

public class WarehouseServiceGrpcException extends RuntimeException {
    public WarehouseServiceGrpcException(String message) {
        super(message);
    }
}
