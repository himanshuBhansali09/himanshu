package com.ttn.ecommerceApplication.ecommerceApplication.dao;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.CategoryMetadataField;

import java.util.List;

public interface CategoryMetadataFieldDao
{
    public List<CategoryMetadataField> viewCategoryMetadataField(Integer pageNo, Integer pageSize, String sortBy);
    public void deleteCategoryMetadataField(Long id);

}
