package com.ttn.ecommerceApplication.ecommerceApplication.entities;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@EntityListeners(EntityListeners.class)
public class Product
{
     @Id
     @GeneratedValue
     Long ID;

     @ManyToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "SELLER_USER_ID")
     private Seller seller;

     @Column(nullable = false,unique = true)
     private String productname;
     private String description;

    private boolean isCancellable;

    private boolean isReturnable;
    private String brand;

     private boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category1;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<ProductVariation> productVariations;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<ProductReview> productReviews;


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


    public Category getCategory1() {
        return category1;
    }

    public Set<ProductVariation> getProductVariations() {
        return productVariations;
    }

    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

    public void setProductVariations(Set<ProductVariation> productVariations) {
        this.productVariations = productVariations;
     }

     public Product() {
     }





    public Product(String productname, String brand) {
        this.productname = productname;
        this.brand = brand;
    }


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

  /*  public Category getCategory1() {
        return category1;
    }
*/
    public void setCategory1(Category category1) {
        this.category1 = category1;
    }

    public boolean isCancellable() {
        return isCancellable;
    }

    public void setCancellable(boolean cancellable) {
        isCancellable = cancellable;
    }

    public boolean isReturnable() {
        return isReturnable;
    }

    public boolean getisReturnable()
    {
        return  isReturnable;
    }
    public boolean getisCancellable()
    {
        return isCancellable;
    }


    public void setReturnable(boolean returnable) {
        isReturnable = returnable;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean getisActive() { return isActive; }

    public void setActive(boolean active) {
        isActive = active;
    }

}
