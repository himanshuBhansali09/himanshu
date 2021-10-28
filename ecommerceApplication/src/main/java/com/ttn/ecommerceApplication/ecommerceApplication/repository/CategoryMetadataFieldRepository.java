package com.ttn.ecommerceApplication.ecommerceApplication.repository;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.CategoryMetadataField;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CategoryMetadataFieldRepository extends PagingAndSortingRepository<CategoryMetadataField,Long>
{

    @Query(value = "select name from category_metadata_field where id = :id",nativeQuery = true)
    List<Object[]> getMetadataField(@Param("id") Long id);

    @Query(value = "select name from category_metadata_field where id = :id",nativeQuery = true)
    public String getNameOfMetadata(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from category_metadata_field where id=:id",nativeQuery = true)
    public void deleteMetadatField(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from category_metadata_field_values where category_metadata_id=:category_metadata_id",nativeQuery = true)
    public void dele(@Param("category_metadata_id") Long category_metadata_id);


}
