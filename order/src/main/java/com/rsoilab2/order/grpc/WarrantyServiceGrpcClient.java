package com.rsoilab2.order.grpc;

import com.google.gson.Gson;
import com.rsoilab2.order.exceptions.WarrantyServiceGrpcException;
import com.rsoilab2.order.models.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Service
public class WarrantyServiceGrpcClient {
    @Autowired
    private Gson gson;
    public void startWarranty(String itemUid){
        String url ="http://warranty-service-app-zav.herokuapp.com/api/v1/warranty/" +itemUid;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result;
        try {
             result = restTemplate.postForEntity(url, new HttpEntity<>(null), String.class);
        } catch (Exception e){
            throw new WarrantyServiceGrpcException(e.getMessage());
        }
        if (result.getStatusCode() != HttpStatus.NO_CONTENT) {
            MessageResponse messageResponse = gson.fromJson(result.getBody(), MessageResponse.class);
            throw new WarrantyServiceGrpcException(messageResponse.getMessage());
        }
    }

    public void stopWarranty(String itemUid){
        String url ="http://warranty-service-app-zav.herokuapp.com/api/v1/warranty/" +itemUid;
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(url);
        } catch (Exception e){
            throw new WarrantyServiceGrpcException(e.getMessage());
        }
    }


}
