package com.ttn.ecommerceApplication.ecommerceApplication.controller;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.CategoryDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.CategoryMetadataFieldDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.ProductDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.FilteringDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.ViewCategoriesDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Category;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.CategoryMetadataField;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.CategoryMetadataFieldValues;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryMetadataFieldDao categoryMetadataFieldDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductVariationRepository productVariationRepository;

    //admin apis

    @ApiOperation("uri for adding metadata fields")
    @PostMapping("/addCategoryMetadataField")
    public String addCategoryMetadataField(@Valid @RequestBody CategoryMetadataField categoryMetadataField) {
        categoryMetadataFieldRepository.save(categoryMetadataField);
        return "CategoryMetadataField is successfully created";
    }

    @ApiOperation("uri for listing all category metadata fields")
    @GetMapping("/viewCategoryMetadataField")
    public ResponseEntity<List<CategoryMetadataField>> viewCategoryMetadataField(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                                                 @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        List<CategoryMetadataField> list = categoryMetadataFieldDao.viewCategoryMetadataField(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<CategoryMetadataField>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @ApiOperation("uri for admin to add a new subcategory")
    @PostMapping("/addNewCategory/{parent_category_id}")
    public String addNewSubCategory(@Valid @PathVariable(name = "parent_category_id") Long parent_category_id, @RequestBody Category category) {
        categoryDao.addNewSubCategory(parent_category_id, category);
        return "subcategory added successfully";
    }

    @ApiOperation("uri for admin to add a main category")
    @PostMapping("/addNewCategory")
    public ResponseEntity addMainCategory(@Valid @RequestBody Category category)
    {
        return categoryDao.addMainCategory(category);
    }

    @ApiOperation("uri for admin to view a single category")
    @GetMapping("/viewACategory/{id}")
    public List<ViewCategoriesDTO> viewCategory(@PathVariable Long id)
    {
        return categoryDao.viewACategory(id);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("uri for admin to view all categories")
    @GetMapping("/viewAllCategories")
    public List<ViewCategoriesDTO> getAllCategories()
    {
        return categoryDao.viewAllCategories();
    }
    @Secured("ROLE_ADMIN")
    @ApiOperation("uri for admin to view all categories")
    @GetMapping("/viewAllCategoriesForAdd")
    public List<ViewCategoriesDTO> getAllCategoriesForAdd(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                    @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                    @RequestParam(name = "sortBy", defaultValue = "id") String sortBy)
    {
        return categoryDao.viewAllCategoriesForAddition(pageNo, pageSize, sortBy);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("uri for admin to update category details")
    @PutMapping("/updateCategory/{categoryId}")
    public String updateCategory(@Valid @RequestBody Category category, @PathVariable(name = "categoryId") Long categoryId) {
        categoryDao.updateCategory(category, categoryId);
        return "Category successfully updated";
    }

    @ApiOperation("uri for seller to add metadata field values for a particular category and metadata field ")
    @PostMapping("/addMetadataValues/{categoryId}/{metadataId}")
    public void addMetadataValues(@Valid @RequestBody CategoryMetadataFieldValues categoryMetadataFieldValues,
                                  @PathVariable(value = "categoryId") Long categoryId,
                                  @PathVariable(value = "metadataId") Long metadataId) {
        categoryDao.addMetadataValues(categoryMetadataFieldValues, categoryId, metadataId);

    }

    @ApiOperation("uri for seller to update metadata field values")
    @PutMapping("/updateMetadataValues/{categoryId}/{metadataId}")
    public void updateMetadataValues(@Valid @RequestBody CategoryMetadataFieldValues categoryMetadataFieldValues,
                                     @PathVariable(value = "categoryId") Long categoryId,
                                     @PathVariable(value = "metadataId") Long metadataId) {

        categoryDao.updateMetadataValues(categoryMetadataFieldValues, categoryId, metadataId);

    }






    //for seller


    @GetMapping("/viewCategoriesForSeller")
    @ApiOperation("uri for seller to view all categories")
    public List<ViewCategoriesDTO> viewCategoriesDTOS()
    {
        return categoryDao.viewAllCategoriesForSeller();
    }




    //customer

    @GetMapping("/viewCategoriesForCustomer")
    @ApiOperation("uri for customer to view all categories")
    public List<Object[]> getMainCategoriesForCustomer()
    {
        return categoryDao.getAllCategory();
    }

    @ApiOperation("uri for customer to view a single category")
    @GetMapping("/viewCategoriesForCustomer/{id}")
    public List<Object[]> getSubCategory(@PathVariable(name = "id") Long id)
    {
        List<Object[]> list = categoryDao.getAllSubCategory(id);
        return list;
    }

    @Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
    @ApiOperation("uri for customer to get filtering details of a category")
    @GetMapping("/filtering/{id}")
    public FilteringDTO getFilteringDetails(@PathVariable Long id)
    {
        return categoryDao.getFilteringDetails(id);
    }































































    @GetMapping("/getCategory")
    public List<Object[]> getCategories()
    {
        List<Object[]> list = new ArrayList<>();
        Iterable<Category> categories = categoryRepository.findAll();
        for (Category category : categories)
        {
            if (categoryRepository.checkIfLeaf(category.getId())==0)
            {
                list.add(categoryRepository.getNameOfCategory(category.getId()));
                List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category.getId());
                for (Long l : longList)
                {
                    List<Object[]> list1 = categoryMetadataFieldRepository.getMetadataField(l);
                    list.addAll(list1);
                }
                List<Object[]> list1 = categoryMetadataFieldValuesRepository.getValues(category.getId());
                list.addAll(list1);
            }
            else
            {
                list.add(0,categoryRepository.getNameOfCategory(category.getId()));

            }
        }
        return list;
    }



    @GetMapping("/getSubcategories")
    public List<Object[]> getSubcategories() {
        List<Object[]> objects = categoryDao.getSubcategory();
        return objects;
    }







    @DeleteMapping("/deleteCategoryMetadataField/{id}")
    public String deleteCategoryMetadataField(@PathVariable(value = "id") Long id) {
        categoryMetadataFieldDao.deleteCategoryMetadataField(id);
        return "CategoryMetadataField is successfully deleted";
    }








}
