package com.rsoilab2.warranty.services;

import com.rsoilab2.warranty.entities.Warranty;
import com.rsoilab2.warranty.repositories.WarrantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class WarrantyService {
    @Autowired
    private WarrantyRepository warrantyRepository;

    @Transactional
    public Warranty getWarrantyByItemUid(UUID itemUid){
        return warrantyRepository.findByItemUid(itemUid).
                orElseThrow(() -> new EntityNotFoundException("Warranty not found for itemUid: " + itemUid.toString()));
    }

    @Transactional
    public void save(Warranty warranty){
        warrantyRepository.save(warranty);
    }

    @Transactional
    public void deleteAll(UUID itemUid){
        warrantyRepository.deleteAllByItemUid(itemUid);
    }
}
