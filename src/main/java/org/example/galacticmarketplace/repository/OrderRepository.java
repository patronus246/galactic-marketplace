package org.example.galacticmarketplace.repository;

import org.example.galacticmarketplace.repository.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, UUID> {

}
