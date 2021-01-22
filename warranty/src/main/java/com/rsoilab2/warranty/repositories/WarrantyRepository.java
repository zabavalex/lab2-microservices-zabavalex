package com.rsoilab2.warranty.repositories;

import com.rsoilab2.warranty.entities.Warranty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarrantyRepository extends CrudRepository<Warranty, Long> {
    Optional<Warranty> findByItemUid(UUID itemUid);
    void deleteAllByItemUid(UUID itemUid);
}
