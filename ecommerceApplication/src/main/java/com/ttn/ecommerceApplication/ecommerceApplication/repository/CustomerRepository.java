package com.ttn.ecommerceApplication.ecommerceApplication.repository;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.Customer;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,Long> {


    Customer findByUsername(String username);

    @Query(value = "select username,first_name,last_name,contact_no from user join customer on user.id=customer.id where username=:username",nativeQuery = true)
    List<Object[]> getDetails(@Param(value = "username") String username);

    @Modifying
    @Transactional
    @Query(value = "insert into customer values(?1,?2)",nativeQuery = true)
    void insertIntoCustomer(String contactNo,Long id);

    @Query("select firstName,middleName,lastName,isActive,contactNo from Customer where id=:id")
    List<Object[]> viewProfile(@Param(value = "id") Long id);
}
