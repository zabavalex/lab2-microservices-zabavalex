package com.rsoilab2.store.grpc;
import com.google.gson.Gson;
import com.rsoilab2.store.exceptions.ItemNotAvailableException;
import com.rsoilab2.store.exceptions.OrderServiceGrpcException;
import com.rsoilab2.store.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Access;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrderServiceGrpcClient {
    @Autowired
    private Gson gson;
    public OrdersInfoResponse getOrdersByUserUid(String userUid){
        String url ="http://order-service-app-zav.herokuapp.com/api/v1/orders/" + userUid;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OrdersInfoResponse> result;
        try {
            result = restTemplate.getForEntity(url, OrdersInfoResponse.class);
        } catch (Exception e) {
            throw new OrderServiceGrpcException(e.getMessage());
        }
        return result.getBody();
    }

    public OrderWarrantyResponse getWarrantyByOrderUid(String orderUid, OrderWarrantyRequest orderWarrantyRequest){
        String url ="http://order-service-app-zav.herokuapp.com/api/v1/orders/" + orderUid + "/warranty";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result;
        HttpEntity<OrderWarrantyRequest> request = new HttpEntity<>(orderWarrantyRequest);
        try {
            result = restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException.NotFound e){
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e){
            throw new OrderServiceGrpcException(e.getMessage());
        }
//        if(result.getStatusCode() == HttpStatus.NOT_FOUND){
//            MessageResponse messageResponse = gson.fromJson(result.getBody(), MessageResponse.class);
//            throw new EntityNotFoundException(messageResponse.getMessage());
//        }
//        if(result.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY){
//            MessageResponse messageResponse = gson.fromJson(result.getBody(), MessageResponse.class);
//            throw new EntityNotFoundException(messageResponse.getMessage());
//        }
        return gson.fromJson(result.getBody(), OrderWarrantyResponse.class);
    }

    public OrderResponse performOrder(PurchaseRequest purchaseRequest, String userUid){
        String url ="http://order-service-app-zav.herokuapp.com/api/v1/orders/" + userUid;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result;
        HttpEntity<PurchaseRequest> request = new HttpEntity<>(purchaseRequest);
        try {
            result = restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException.Conflict e){
            throw new ItemNotAvailableException(e.getMessage());
        }
        catch (Exception e) {
            throw new OrderServiceGrpcException(e.getMessage());
        }
        if(result.getStatusCode() == HttpStatus.CONFLICT){
            MessageResponse messageResponse = gson.fromJson(result.getBody(), MessageResponse.class);
            throw new ItemNotAvailableException(messageResponse.getMessage());
        }
        if(result.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY){
            MessageResponse messageResponse = gson.fromJson(result.getBody(), MessageResponse.class);
            throw new OrderServiceGrpcException(messageResponse.getMessage());
        }
        return gson.fromJson(result.getBody(), OrderResponse.class);
    }

    public void refundOrder(String orderUid){
        String url ="http://order-service-app-zav.herokuapp.com/api/v1/orders/" + orderUid;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result;
        try {
            result = restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(null), String.class);
        } catch (HttpClientErrorException.NotFound e){
            throw new EntityNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new OrderServiceGrpcException(e.getMessage());
        }
        if(result.getStatusCode() == HttpStatus.NOT_FOUND){
            MessageResponse messageResponse = gson.fromJson(result.getBody(), MessageResponse.class);
            throw new EntityNotFoundException((messageResponse.getMessage()));
        }
    }
}
