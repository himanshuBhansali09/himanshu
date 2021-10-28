package com.ttn.ecommerceApplication.ecommerceApplication.repository;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.ProductVariation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
public interface ProductVariationRepository extends CrudRepository<ProductVariation,Long>
{
    @Query(value = "select * from product_variation where product_id=:product_id",nativeQuery = true)
    List<Object[]> getProductVariation(@Param("product_id") Long product_id);

    @Query(value = "select price,quantity_available,info_json from " +
            "product_variation  where product_id=:product_id ", nativeQuery = true)
    public List<Object[]> getProductVariations(@Param(value = "product_id") Long product_id);

    @Query(value = "select product_id from product_variation where id =:id",nativeQuery = true)
    public Long getProductId(@Param(value = "id") Long id);

    @Query(value = "select price from product_variation where id =:id",nativeQuery = true)
    public double getPrice(@Param(value = "id") Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from product_variation where id=:id",nativeQuery = true)
    public void deleteProductVariation(@Param(value = "id") Long id);

    @Query(value = "select price,quantity_available,product.productname,product.brand,product.description from product_variation join product " +
            "on product_variation.product_id=product.id where product_variation.id=?1 ",nativeQuery = true)
    List<Object[]> getSingleProductVariation(Long id);
}
