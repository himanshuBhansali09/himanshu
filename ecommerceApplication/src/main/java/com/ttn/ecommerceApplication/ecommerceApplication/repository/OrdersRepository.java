package com.ttn.ecommerceApplication.ecommerceApplication.repository;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends CrudRepository<Orders,Long>
{
    @Query(value = "select amount_paid,payment_method from orders join order_product " +
            "on orders.id= order_product.order_id where orders.customer_user_id =:customer_user_id",nativeQuery = true)
    public List<Object[]> getAllOrders(@Param(value = "customer_user_id") Long customer_user_id);
}
