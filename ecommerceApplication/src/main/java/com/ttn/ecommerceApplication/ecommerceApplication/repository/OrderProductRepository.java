package com.ttn.ecommerceApplication.ecommerceApplication.repository;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.OrderProduct;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct,Long> {
}
