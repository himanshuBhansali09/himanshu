package com.ttn.ecommerceApplication.ecommerceApplication.repository;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Seller;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface SellerRepository extends CrudRepository<Seller,Long>
{
    Seller findByUsername(String username);

    @Query(value =  "select username,email,first_name,last_name," +
            "company_name,gst_no,company_contact_no" +
            " from user join seller on user.id=seller.id where username=:username",nativeQuery = true)
    List<Object[]> getDetails(@Param(value = "username") String username);

    @Modifying
    @Transactional
    @Query(value = "insert into seller values(?1,?2,?3,?4)",nativeQuery = true)
    void insertIntoSeller(String companyContactNo,String companyName,String gstNo,Long id);

    @Query(value = "select name,id from category where parent_id not in (select id from category where parent_id is null)", nativeQuery = true)
    public List<Object[]> getSubcategory();
}
