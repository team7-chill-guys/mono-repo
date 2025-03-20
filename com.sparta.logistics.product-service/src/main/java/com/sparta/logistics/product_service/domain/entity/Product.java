package com.sparta.logistics.product_service.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID companyId;
    private UUID hubId;
    private String name;
    private Long stock;
    private Timestamp createdAt;
    private Long createdBy;
    private Timestamp updatedAt;
    private Long updatedBy;
    private Timestamp deletedAt;
    private Long deletedBy;

    public void decreaseStock(long quantity) {
        if (this.stock >= quantity) {
            this.stock -= quantity;
        } else {
            throw new RuntimeException("Not enough stock");
        }
    }
    public void increaseStock(long quantity) {
        this.stock += quantity;
    }
}
