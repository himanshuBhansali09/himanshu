package com.ttn.ecommerceApplication.ecommerceApplication.repository;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product,Long>
{
    @Query(value = "select brand,description,productname from product where category_id in (select id from category where name=:name)",nativeQuery = true)
    List<Object[]> getProducts(@Param("name") String name );

    @Query(value =  "select productname,description,brand,name" +
            " from product join category on product.category_id=category.id where seller_user_id=:seller_user_id and is_active=true",nativeQuery = true)
    List<Object[]> getProductss(@Param(value = "seller_user_id") Long id);

    List<Object[]> findByProductname(String productName);

    @Query(value = "select category_id from product where id=:id",nativeQuery = true)
    Long getCategoryId(@Param("id") Long id);

    @Query(value = "select brand,description,is_cancellable,productname,name from product join category on product.category_id=category.id where product.is_active=true",nativeQuery = true)
    public List<Object[]> getAllProducts();

    @Query("select id from Product where productname =:productname")
    public Long findProduct(@Param(value = "productname") String productname);

    @Transactional
    @Modifying
    @Query(value = "delete from product where id=:id and productname=:productname",nativeQuery = true)
    public void deleteProduct(@Param(value = "id") Long id, @Param(value = "productname") String productname);

    @Transactional
    @Modifying
    @Query(value = "delete product_variation from product_variation inner join" +
            " product on product.id= product_variation.product_id where product_id =:id  ",nativeQuery = true)
    public void deleteProductVariation(@Param(value = "id") Long id);

    @Query(value = "select id from product where productname=:productname",nativeQuery = true)
    Long getProductVariationid(@Param("productname") String productname);

    @Query(value = "select price from product_variation where id =:id",nativeQuery = true)
    public double getPrice(@Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(value = "update Product set isActive= false where id =:id")
    public void setActiveStatusOfProduct(@Param(value = "id") Long id);

    @Query(value = "select username from user join  product on user.id=product.seller_user_id where product.id= ?1",nativeQuery = true)
    public String getThatSeller(Long productId);

    @Transactional
    @Modifying
    @Query(value = "update Product set isActive= true where id =:id")
    public void activateTheProduct(@Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(value = "update product_variation set is_active = false where product_id =:product_id",nativeQuery = true)
    public void setActiveStatusOfProductAndProductVariation(@Param(value = "product_id") Long product_id);

    @Query(value = "select brand from product where category_id=:category_id",nativeQuery = true)
    List<Object[]> getBrands(@Param("category_id") Long category_id);

    @Query(value = "select productname,brand,name,description from category  join product  on" +
            " product.category_id = category.id where product.id = ?1",nativeQuery = true)
    List<Object[]> getSingleProduct(Long id);

    @Query(value = "select productname,brand,description from product where is_active=true",nativeQuery = true)
    List<Object[]> getAllNonDeletedproducts();

    @Query(value = "select * from product where category_id=:category_id",nativeQuery = true)
    List<Product> getProducts(@Param("category_id") Long category_id);

    @Query(value = "select id from product where category_id=:category_id",nativeQuery = true)
    List<Long> getIdsOdProducts(@Param("category_id") Long category_id, Pageable pageable);

    @Query(value = "select id from product",nativeQuery = true)
    List<Long> getAllId(Pageable pageable);

    @Query(value = "select id from product where seller_user_id=:seller_user_id",nativeQuery = true)
    List<Long> getProductIdOfSeller(@Param("seller_user_id") Long seller_user_id,Pageable pageable);

    @Query(value = "select id from product where category_id=:category_id and brand=:brand and is_active=true",nativeQuery = true)
    List<Long> getIdOfSimilarProduct(@Param("category_id") Long category_id,@Param("brand") String brand,Pageable pageable);

}
