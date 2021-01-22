package com.rsoilab2.store.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "users", indexes = {
        @Index(name = "idx_user_name", columnList = "name", unique = true),
        @Index(name = "idx_user_user_uid", columnList = "userUid", unique = true)
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private UUID userUid;


}
