package com.ttn.ecommerceApplication.ecommerceApplication.dto;

import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
public class ProductDTO
{
    @Column(nullable = false,unique = true)
    private String productname;

    @Column(nullable = false,unique = true)
    private String brand;


    private Boolean isCancellable;

    private Boolean isReturnable;

    private String description;

    private boolean isActive;

    public Boolean isActive() {
        return isActive;
    }

    public Boolean getisActive()
    {
        return isActive;
    }


    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean isCancellable() {
        return isCancellable;
    }

    public Boolean getisCancellable()
    {
        return isCancellable;
    }

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }

    public Boolean isReturnable() {
        return isReturnable;
    }

    public Boolean getisReturnable()
    {
        return isReturnable;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
