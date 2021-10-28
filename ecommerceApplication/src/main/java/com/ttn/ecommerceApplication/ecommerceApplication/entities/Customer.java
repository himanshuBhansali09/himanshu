package com.ttn.ecommerceApplication.ecommerceApplication.entities;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@EntityListeners(AuditingEntityListener.class)
public class Customer extends User
{
    @Pattern(regexp = "(\\+91|0)[0-9]{10}")
    String  contactNo;

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

    public String getContactNo() {
        return contactNo;
    }
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private Set<Orders> orders;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private Set<ProductReview> productReviews;

   /* public Set<Orders> getOrders() {
        return orders;
    }
*/

    @Override
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    @Override
    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    @Override
    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getModifiedBy() {
        return modifiedBy;
    }

    @Override
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }
/*
    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }*/

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
