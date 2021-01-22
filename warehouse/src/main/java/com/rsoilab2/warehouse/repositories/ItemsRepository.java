package com.rsoilab2.warehouse.repositories;

import com.rsoilab2.warehouse.entities.Items;
import com.rsoilab2.warehouse.enumerations.SizeEnumeration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemsRepository extends CrudRepository<Items, Long>{
    Optional<Items> findByModelAndSize(String model, SizeEnumeration size);
}
