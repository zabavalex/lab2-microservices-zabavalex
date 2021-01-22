package com.rsoilab2.order.grpc;

import com.google.gson.Gson;
import com.rsoilab2.order.exceptions.ItemNotAvailableException;
import com.rsoilab2.order.exceptions.WarehouseServiceGrpcException;
import com.rsoilab2.order.exceptions.WarrantyServiceGrpcException;
import com.rsoilab2.order.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.UUID;

@Service
public class WarehouseServiceGrpcClient {
    @Autowired
    private Gson gson;

    public OrderItemResponse takeItem(String orderUid, String model, String size){
        String url ="http://warehouse-service-app-zav.herokuapp.com/api/v1/warehouse";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result;
        OrderItemRequest orderItemRequest = new OrderItemRequest(UUID.fromString(orderUid), model, size);
        HttpEntity<OrderItemRequest> request = new HttpEntity<>(orderItemRequest);
        try {
            result = restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException.NotFound e){
            throw new EntityNotFoundException(e.getMessage());
        } catch (HttpClientErrorException.Conflict e){
            throw new ItemNotAvailableException(e.getMessage());
        }
        catch (Exception e){
            throw new WarehouseServiceGrpcException(e.getMessage());
        }
//        if (result.getStatusCode() == HttpStatus.NOT_FOUND) {
//            MessageResponse messageResponse = gson.fromJson(result.getBody(), MessageResponse.class);
//            throw new EntityNotFoundException(messageResponse.getMessage());
//        }
//        if (result.getStatusCode() == HttpStatus.CONFLICT) {
//            MessageResponse messageResponse = gson.fromJson(result.getBody(), MessageResponse.class);
//            throw new ItemNotAvailableException(messageResponse.getMessage());
//        }
        OrderItemResponse orderItemResponse = gson.fromJson(result.getBody(), OrderItemResponse.class);
        return orderItemResponse;

    }

    public OrderWarrantyResponse useWarranty(String itemUid, OrderWarrantyRequest orderWarrantyRequest){
        String url ="http://warehouse-service-app-zav.herokuapp.com/api/v1/warehouse/" + itemUid + "/warranty";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result;
        HttpEntity<OrderWarrantyRequest> request = new HttpEntity<>(orderWarrantyRequest);
        try {
            result = restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException.NotFound e){
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e){
            throw new WarehouseServiceGrpcException(e.getMessage());
        }
//        if (result.getStatusCode() == HttpStatus.NOT_FOUND) {
//            MessageResponse messageResponse = gson.fromJson(result.getBody(), MessageResponse.class);
//            throw new EntityNotFoundException(messageResponse.getMessage());
//        }
//        if (result.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
//            MessageResponse messageResponse = gson.fromJson(result.getBody(), MessageResponse.class);
//            throw new EntityNotFoundException(messageResponse.getMessage());
//        }
        return gson.fromJson(result.getBody(), OrderWarrantyResponse.class);
    }

    public void returnItem(String itemUid){
        String url ="http://warehouse-service-app-zav.herokuapp.com/api/v1/warehouse/" + itemUid;
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(url);
        } catch (Exception e){
            throw new WarehouseServiceGrpcException(e.getMessage());
        }
    }
}
