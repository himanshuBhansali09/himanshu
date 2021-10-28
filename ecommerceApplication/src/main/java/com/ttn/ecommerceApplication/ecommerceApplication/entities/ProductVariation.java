package com.ttn.ecommerceApplication.ecommerceApplication.entities;

import com.ttn.ecommerceApplication.ecommerceApplication.utilities.HashMapConverter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProductVariation
{
    @Id
    @GeneratedValue
    Long Id;

    Boolean isActive;

    @Positive
    @Column(nullable = false)
    Integer quantity_available;
    @Positive
    @Column(nullable = false)
    Double price;

    private String infoJson;

    @Convert(converter = HashMapConverter.class)
    private Map<String,Object> infoAttributes;


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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @OneToMany(mappedBy = "productVariation",cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "productVariation",cascade = CascadeType.ALL)
    private Set<Cart> carts;

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }
    public void addCarts(Cart cart)
    {
        if (carts==null)
        {
            carts = new HashSet<>();
        }
        carts.add(cart);
    }



    public String getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(String infoJson) {
        this.infoJson = infoJson;
    }

    public Map<String, Object> getInfoAttributes() {
        return infoAttributes;
    }

    public void setInfoAttributes(Map<String, Object> infoAttributes) {
        this.infoAttributes = infoAttributes;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Boolean isActive() {
        return isActive;
    }

    public Boolean getisActive(){return isActive;}

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(Integer quantity_available) {
        this.quantity_available = quantity_available;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
