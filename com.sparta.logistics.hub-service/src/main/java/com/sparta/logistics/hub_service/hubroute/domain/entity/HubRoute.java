package com.sparta.logistics.hub_service.hubroute.domain.entity;

import com.sparta.logistics.hub_service.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "p_hub_route")
public class HubRoute extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "start_hub_id", nullable = false)
  private UUID startHubId;

  @Column(name = "end_hub_id", nullable = false)
  private UUID endHubId;

  @Column(name = "start_hub_name", nullable = false)
  private String startHubName;
  
  @Column(name = "end_hub_name", nullable = false)
  private String endHubName;

  @Column(name = "delivery_time", nullable = false)
  private Long deliveryTime;

  @Column(name = "delivery_distance", nullable = false)
  private Long deliveryDistance;
}
