package com.ttn.ecommerceApplication.ecommerceApplication.repository;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.Category;
import org.graalvm.compiler.lir.alloc.lsra.LinearScan;
import org.hibernate.annotations.Parent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long>
{
    @Query(value = "select name,id from category where parent_id is null",nativeQuery = true)
    List<Object[]> getMainCategory();

    @Query(value = "select name from category where parent_id in(select id from category where name=:name)",nativeQuery = true)
    List<Object[]> getSubCategory(@Param("name") String name);

    @Query(value = "select name,id from category where parent_id in(select id from category where id=:id)",nativeQuery = true)
    List<Object[]> getSubCategoryOfCategory(@Param("id") Long id);

    @Query(value = "select id from category where name=:name",nativeQuery = true)
    Long getIdOfParentCategory(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "insert into category(name,parent_id) values(?1,?2)",nativeQuery = true)
    void insertNewSubCategory(String name,Long id);

    @Query(value = "select exists(select * from category where parent_id=:parent_id)",nativeQuery = true)
    int checkIfLeaf(@Param("parent_id") Long parent_id);

    @Query(value = "select parent_id from category where id=:id",nativeQuery = true)
    Long getIdOfParent(@Param("id") Long id);

    @Query(value = "select name from category where id=:id",nativeQuery = true)
    Object[] getNameOfCategory(@Param("id") Long id);

    @Query(value = "select id from category where parent_id=:parent_id",nativeQuery = true)
    List<Long> getIdsOfSubcategories(@Param("parent_id") Long parent_id);

    @Query(value = "select id from category",nativeQuery = true)
    List<Long> getIdsOfCategory();

}
