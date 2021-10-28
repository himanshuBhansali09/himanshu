package com.ttn.ecommerceApplication.ecommerceApplication.entities;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@EntityListeners(AuditingEntityListener.class)
public class Seller extends User
{
    @Column(unique = true,nullable = false)
    String companyName;
    @Column(unique = true,nullable = false)
    @Pattern(regexp = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}")
    String gstNo;
    @Pattern(regexp = "(\\+91|0)[0-9]{10}")
    @Column(unique = true,nullable = false)
    String   companyContactNo;
    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL)
    private Set<Product> products;

  /*  public Set<Product> getProducts() {
        return products;
    }
*/
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

    public Set<Product> getProducts() {
        return products;
    }

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

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getCompanyContactNo() {
        return companyContactNo;
    }

    public void setCompanyContactNo(String companyContactNo) {
        this.companyContactNo = companyContactNo;
    }
}
