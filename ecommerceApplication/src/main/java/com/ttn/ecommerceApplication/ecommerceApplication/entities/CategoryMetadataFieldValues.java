package com.ttn.ecommerceApplication.ecommerceApplication.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity

public class CategoryMetadataFieldValues implements Serializable
{

    @EmbeddedId
    private CategoryMetadataFieldValuesId categoryMetadataFieldValuesId;


    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_metadata_id",insertable = false,updatable = false,nullable = false)

    private CategoryMetadataField categoryMetadataField;
    private CategoryMetadataField getCategoryMetadataField()
    {

        return categoryMetadataField;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id",insertable = false,updatable = false,nullable = false)
    private Category category;
    private Category getCategory()
    {
        return category;
    }    


    @Column(nullable = false)
    String fieldValues;

    public CategoryMetadataFieldValuesId getCategoryMetadataFieldValuesId() {
        return categoryMetadataFieldValuesId;
    }

    public void setCategoryMetadataFieldValuesId(CategoryMetadataFieldValuesId categoryMetadataFieldValuesId) {
        this.categoryMetadataFieldValuesId = categoryMetadataFieldValuesId;
    }

    public void setCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        this.categoryMetadataField = categoryMetadataField;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(String fieldValues) {
        this.fieldValues = fieldValues;
    }
}
