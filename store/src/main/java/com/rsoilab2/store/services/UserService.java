package com.rsoilab2.store.services;

import com.rsoilab2.store.entities.User;
import com.rsoilab2.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUserByUserUid(UUID userUid){
        return userRepository.findByUserUid(userUid)
                .orElseThrow(() -> new EntityNotFoundException("User " + userUid.toString() + " not found"));
    }
}
