package com.ttn.ecommerceApplication.ecommerceApplication.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Category
{
        @Id
        @GeneratedValue
        private Long Id;

        @Column(nullable = false,unique = true)
        private String name;


    @Column(name = "createdDate")
    @CreatedDate
    private LocalDateTime createdOn;

    @Column(name = "modifiedDate")
    @LastModifiedDate
    private LocalDateTime modifiedOn;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;


        @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
        @JoinColumn(name="parent_id")
        private Category category;

        @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
        private Set<Category> categories;


        @OneToMany(targetEntity = CategoryMetadataFieldValues.class,mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
        private Set<CategoryMetadataFieldValues> categoryMetadataFieldValues;

        @OneToMany(mappedBy = "category1",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
        private Set<Product> products;


    public Category() {
    }

    public Set<CategoryMetadataFieldValues> getCategoryMetadataFieldValues() {
        return categoryMetadataFieldValues;
    }

    public void setCategoryMetadataFieldValues(Set<CategoryMetadataFieldValues> categoryMetadataFieldValues) {
        this.categoryMetadataFieldValues = categoryMetadataFieldValues;
    }

    public Category(String name) {
        this.name = name;
    }

    /*  public Category getCategory() {
            return category;
        }
    */
    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return Id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    public Category getCategory() {
        return category;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

 /*   public Set<Category> getCategories() {
        return categories;
    }
*/
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

   /* public Set<Product> getProducts() {
        return products;
    }
*/
    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
