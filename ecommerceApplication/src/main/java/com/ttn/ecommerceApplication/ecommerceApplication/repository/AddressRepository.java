package com.ttn.ecommerceApplication.ecommerceApplication.repository;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address,Long>
{
    @Query(value = "select id,city,country,label,state,zip_code from address where user_id=:user_id",nativeQuery = true)
    public List<Object[]> findAllByUser(@Param(value = "user_id")Long user_id);
}
