package com.ttn.ecommerceApplication.ecommerceApplication.dto;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilteringDTO
{
    String categoryName;
    List<String> fields;
    List<String> values;
    List<String> brands;
    Double maximuPrice;
    Double minimumPrice;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    public Double getMaximuPrice() {
        return maximuPrice;
    }

    public void setMaximuPrice(Double maximuPrice) {
        this.maximuPrice = maximuPrice;
    }

    public Double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(Double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }
}
