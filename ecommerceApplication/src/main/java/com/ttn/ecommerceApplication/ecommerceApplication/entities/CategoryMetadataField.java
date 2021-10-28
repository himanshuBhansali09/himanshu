package com.ttn.ecommerceApplication.ecommerceApplication.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class CategoryMetadataField
{
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false,unique = true)
    String name;

    @OneToMany(mappedBy = "categoryMetadataField",cascade = CascadeType.ALL)
    private Set<CategoryMetadataFieldValues> categoryMetadataFieldValues;

    public Long getId() {
        return id;
    }

    public Set<CategoryMetadataFieldValues> getCategoryMetadataFieldValues() {
        return categoryMetadataFieldValues;
    }

    public void setCategoryMetadataFieldValues(Set<CategoryMetadataFieldValues> categoryMetadataFieldValues) {
        this.categoryMetadataFieldValues = categoryMetadataFieldValues;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
