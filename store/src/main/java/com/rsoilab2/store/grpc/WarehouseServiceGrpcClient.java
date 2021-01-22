package com.rsoilab2.store.grpc;

import com.google.gson.Gson;
import com.rsoilab2.store.exceptions.ItemNotAvailableException;
import com.rsoilab2.store.exceptions.WarehouseServiceGrpcException;
import com.rsoilab2.store.models.ItemInfoResponse;
import com.rsoilab2.store.models.MessageResponse;
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
    public ItemInfoResponse getItem(String itemUid){
        String url ="http://warehouse-service-app-zav.herokuapp.com/api/v1/warehouse/" + itemUid;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result;
        try {
            result = restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException.NotFound e){
            throw new EntityNotFoundException(e.getMessage());
        }
        catch (Exception e){
            throw new WarehouseServiceGrpcException(e.getMessage());
        }
//        if (result.getStatusCode() == HttpStatus.NOT_FOUND) {
//            MessageResponse messageResponse = gson.fromJson(result.getBody(), MessageResponse.class);
//            throw new EntityNotFoundException(messageResponse.getMessage());
//        }
        return gson.fromJson(result.getBody(), ItemInfoResponse.class);
    }
}
