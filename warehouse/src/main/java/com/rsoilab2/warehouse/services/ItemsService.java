package com.rsoilab2.warehouse.services;

import com.rsoilab2.warehouse.entities.Items;
import com.rsoilab2.warehouse.enumerations.SizeEnumeration;
import com.rsoilab2.warehouse.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ItemsService {
    @Autowired
    private ItemsRepository itemsRepository;

    @Transactional
    public Items findByModelAndSize(String model, SizeEnumeration size){
        return itemsRepository.findByModelAndSize(model, size).
                orElseThrow(() -> new EntityNotFoundException("Item with model " + model +
                                " and size " + size.name() + " not found"));
    }

    @Transactional
    public void save(Items items){
        itemsRepository.save(items);
    }


}
