package com.rsoilab2.warehouse.grpc;

import com.google.gson.Gson;
import com.rsoilab2.warehouse.entities.OrderItem;
import com.rsoilab2.warehouse.exceptions.WarrantyServiceGrpcException;
import com.rsoilab2.warehouse.models.MessageResponse;
import com.rsoilab2.warehouse.models.WarrantyRequest;
import com.rsoilab2.warehouse.models.WarrantyResponse;
import com.rsoilab2.warehouse.services.OrderItemService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class WarrantyServiceGrpcClient {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private Gson gson;

    public WarrantyResponse performWarrantyRequest(String orderItemUid, WarrantyRequest warrantyRequest){
        OrderItem orderItem =orderItemService.findByOrderItemUid(UUID.fromString(orderItemUid));
        String url ="http://warranty-service-app-zav.herokuapp.com/api/v1/warranty/" +orderItemUid + "/warranty";
        RestTemplate restTemplate = new RestTemplate();
        warrantyRequest.setAvailableCount(orderItem.getItems().getAvailableCount());
        ResponseEntity<String> result;
        HttpEntity<WarrantyRequest> request = new HttpEntity<>(warrantyRequest);
        try {
            result = restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException.NotFound e){
            throw new EntityNotFoundException("Warranty not found for itemUid '" + orderItemUid + "'");
        } catch (Exception e){
            throw new WarrantyServiceGrpcException(e.getMessage());
        }
//        if (result.getStatusCode() != HttpStatus.OK) {
//            MessageResponse messageResponse = gson.fromJson(result.getBody(), MessageResponse.class);
//            throw new EntityNotFoundException(messageResponse.getMessage());
//        }
        return gson.fromJson(result.getBody(), WarrantyResponse.class);
    }
}
