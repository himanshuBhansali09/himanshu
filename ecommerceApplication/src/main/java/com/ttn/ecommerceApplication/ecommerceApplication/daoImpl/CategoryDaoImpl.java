package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.CategoryDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.FilteringDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.ViewCategoriesDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.*;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.NotFoundException;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.NullException;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public void addNewSubCategory(Long parentCategory_id, Category category)
    {
        Long[] l = {};
        int result = categoryRepository.checkIfLeaf(parentCategory_id);
        if (result == 1) {
            Optional<Category> category1 = categoryRepository.findById(parentCategory_id);
            if (category1.isPresent()) {
                category.setCategory(category1.get());
                categoryRepository.save(category);
            } else {
                throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NullPointerException("parent category you selected is already a leaf node");
        }
    }

    @Override
    public ResponseEntity addMainCategory(Category category)
    {
        Long[] l = {};
        categoryRepository.save(category);
        return ResponseEntity.ok().body(messageSource.getMessage("success.txt",l, LocaleContextHolder.getLocale()));
    }

    @Override
    public List<Object[]> getSubcategory() {
        List<Object[]> objects = sellerRepository.getSubcategory();
        return objects;
    }

    public FilteringDTO getFilteringDetails(Long category_id) {
        Optional<Category> category = categoryRepository.findById(category_id);
        FilteringDTO filteringDTO = new FilteringDTO();
        if (category.isPresent() && categoryRepository.checkIfLeaf(category_id) == 0) {
            List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category_id);
            filteringDTO.setCategoryName(category.get().getName());
            List<String> fields = new ArrayList<>();
            List<String> values = new ArrayList<>();
            for (Long l : longList) {
                Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l);
                fields.add(categoryMetadataField.get().getName());
                values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category_id, l));
            }
            filteringDTO.setFields(fields);
            filteringDTO.setValues(values);
            Set<Product> set = category.get().getProducts();
            Double minPrice = 0.0;
            Double maxPrice = 0.0;
            TreeSet<Double> doubles = new TreeSet<>();
            List<String> brands = new ArrayList<>();
            for (Product product : set) {
                brands.add(product.getBrand());
                Set<ProductVariation> set1 = product.getProductVariations();
                for (ProductVariation productVariation : set1) {
                    doubles.add(productVariation.getPrice());
                }

            }
            filteringDTO.setBrands(brands);
            Double[] d = doubles.toArray(new Double[doubles.size()]);
            filteringDTO.setMaximuPrice(d[d.length - 1]);
            filteringDTO.setMinimumPrice(d[0]);


        } else {
            Long[] l =  {};
            throw new NotFoundException(messageSource.getMessage("message15.txt",l,LocaleContextHolder.getLocale()));
        }
        return filteringDTO;

    }


    public List<ViewCategoriesDTO> viewACategory(Long category_id) {
        Optional<Category> category = categoryRepository.findById(category_id);
        List<ViewCategoriesDTO> list = new ArrayList<>();
        if (category.isPresent()) {
            if (categoryRepository.checkIfLeaf(category_id) == 0) {
                List<String> fields = new ArrayList<>();
                List<String> values = new ArrayList<>();

                ViewCategoriesDTO viewCategoriesDTO = new ViewCategoriesDTO();
                viewCategoriesDTO.setName(category.get().getName());
                viewCategoriesDTO.setId(category.get().getId().toString());

                List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category_id);
                for (Long l : longList) {
                    Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l);
                    fields.add(categoryMetadataField.get().getName());
                    values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category_id, l));
                }
                viewCategoriesDTO.setFieldName(fields);
                viewCategoriesDTO.setValues(values);
                list.add(viewCategoriesDTO);
            } else {
                ViewCategoriesDTO viewCategoriesDTO = new ViewCategoriesDTO();
                viewCategoriesDTO.setName(category.get().getName());
                viewCategoriesDTO.setId(category.get().getId().toString());
                list.add(viewCategoriesDTO);
                List<Long> longList = categoryRepository.getIdsOfSubcategories(category_id);

                if (!longList.isEmpty()) {

                    for (Long l : longList) {
                        Optional<Category> category1 = categoryRepository.findById(l);
                        if (categoryRepository.checkIfLeaf(category1.get().getId()) == 0) {
                            List<String> fields = new ArrayList<>();
                            List<String> values = new ArrayList<>();

                            ViewCategoriesDTO viewCategoriesDTO1 = new ViewCategoriesDTO();
                            viewCategoriesDTO1.setName(category1.get().getName());
                            viewCategoriesDTO1.setId(category1.get().getId().toString());

                            List<Long> longList1 = categoryMetadataFieldValuesRepository.getMetadataId(category1.get().getId());
                            for (Long l1 : longList1) {
                                Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l1);
                                fields.add(categoryMetadataField.get().getName());
                                values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category1.get().getId(), l1));
                            }
                            viewCategoriesDTO1.setFieldName(fields);
                            viewCategoriesDTO1.setValues(values);
                            list.add(viewCategoriesDTO1);
                        } else {
                            ViewCategoriesDTO viewCategoriesDTO1 = new ViewCategoriesDTO();
                            viewCategoriesDTO1.setName(category1.get().getName());
                            viewCategoriesDTO1.setId(category1.get().getId().toString());
                            list.add(viewCategoriesDTO1);
                        }
                    }

                }



            }
            return list;
        } else {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void updateCategory(Category category, Long categoryId) {

        if (categoryRepository.findById(categoryId).isPresent()) {
            Category category1 = categoryRepository.findById(categoryId).get();
            category1.setName(category.getName());
            categoryRepository.save(category1);

        } else {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));

        }
    }


    @Override
    public void addMetadataValues(CategoryMetadataFieldValues categoryMetadataFieldValues, Long categoryId, Long metadataId) {

        Long[] lo = {};
        if (categoryRepository.findById(categoryId).isPresent() && categoryRepository.checkIfLeaf(categoryId) == 0) {
            if (categoryMetadataFieldRepository.findById(metadataId).isPresent())
            {
                String checkIfAlreadyPresent = categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(categoryId,metadataId);
                if (checkIfAlreadyPresent==null) {
                    CategoryMetadataFieldValuesId categoryMetadataFieldValuesId = new CategoryMetadataFieldValuesId();
                    categoryMetadataFieldValuesId.setCid(categoryRepository.findById(categoryId).get().getId());
                    categoryMetadataFieldValuesId.setMid(categoryMetadataFieldRepository.findById(metadataId).get().getId());

                    categoryMetadataFieldValues.setCategoryMetadataFieldValuesId(categoryMetadataFieldValuesId);
                    categoryMetadataFieldValues.setCategory(categoryRepository.findById(categoryId).get());
                    String[] valuesArray = categoryMetadataFieldValues.getFieldValues().split(",");
                    Set<String> s = new HashSet<>(Arrays.asList(valuesArray));
                    if (s.size() == valuesArray.length && s.size() >= 1 && valuesArray[0] != "")
                        categoryMetadataFieldValues.setFieldValues(categoryMetadataFieldValues.getFieldValues());
                    else
                        throw new NullException(messageSource.getMessage("unique.txt", lo, LocaleContextHolder.getLocale()));
                    categoryMetadataFieldValues.setCategoryMetadataField(categoryMetadataFieldRepository.findById(metadataId).get());
                    categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);
                }
                else
                {
                    throw new NullException("values are already present for this category id and metadata id");
                }
            } else {
                throw new NotFoundException(messageSource.getMessage("metadata.txt",lo,LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NotFoundException(messageSource.getMessage("message14.txt",lo,LocaleContextHolder.getLocale()));
        }


    }

    @Override
    public void updateMetadataValues(CategoryMetadataFieldValues categoryMetadataFieldValues, Long categoryId, Long metadataId) {

        Long[] l = {};

        if (categoryRepository.findById(categoryId).isPresent()) {
            if (categoryMetadataFieldRepository.findById(metadataId).isPresent()) {
                if (categoryMetadataFieldValuesRepository.getFieldValues(categoryId, metadataId) != null) {
                    CategoryMetadataFieldValues categoryMetadataFieldValues1 = categoryMetadataFieldValuesRepository.getFieldValues(categoryId, metadataId);
                    categoryMetadataFieldValues1.setFieldValues(categoryMetadataFieldValues.getFieldValues());
                    categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues1);
                } else {
                    throw new NotFoundException(messageSource.getMessage("combinationError.txt",l,LocaleContextHolder.getLocale()));
                }
            } else {
                throw new NotFoundException(messageSource.getMessage("wrongMetadata.txt",l,LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }


    }

    public List<ViewCategoriesDTO> viewAllCategoriesForSeller() {
        List<Object[]> list = sellerRepository.getSubcategory();
        List<ViewCategoriesDTO> list1 = new ArrayList<>();
        for (Object[] objects : list) {
            ViewCategoriesDTO viewCategoriesDTO = new ViewCategoriesDTO();
            viewCategoriesDTO.setName(objects[0].toString());
            viewCategoriesDTO.setId(objects[1].toString());
            Long categoryId = categoryRepository.getIdOfParentCategory(objects[0].toString());
            Optional<Category> category = categoryRepository.findById(categoryId);
            Set<CategoryMetadataFieldValues> set = category.get().getCategoryMetadataFieldValues();
            List<String> fields = new ArrayList<>();
            for (CategoryMetadataFieldValues categoryMetadataFieldValues : set) {
                fields.add(categoryMetadataFieldValues.getFieldValues());
                viewCategoriesDTO.setValues(fields);
            }
            List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category.get().getId());
            List<String> fields1 = new ArrayList<>();
            for (Long l : longList) {
                Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l);
                fields1.add(categoryMetadataField.get().getName());
                viewCategoriesDTO.setFieldName(fields1);
            }
            list1.add(viewCategoriesDTO);

        }
        return list1;
    }


    public List<ViewCategoriesDTO> viewAllCategories()
    {
        List<Long> longList = categoryRepository.getIdsOfCategory();
        List<ViewCategoriesDTO> list = new ArrayList<>();
        for (Long l : longList) {
            Optional<Category> category = categoryRepository.findById(l);
            if (category.isPresent()) {
                if (categoryRepository.checkIfLeaf(category.get().getId()) == 0) {
                    List<String> fields = new ArrayList<>();
                    List<String> values = new ArrayList<>();

                    ViewCategoriesDTO viewCategoriesDTO1 = new ViewCategoriesDTO();
                    viewCategoriesDTO1.setName(category.get().getName());
                    viewCategoriesDTO1.setId(category.get().getId().toString());

                    List<Long> longList1 = categoryMetadataFieldValuesRepository.getMetadataId(category.get().getId());
                    for (Long l1 : longList1) {
                        Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l1);
                        fields.add(categoryMetadataField.get().getName());
                        values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category.get().getId(), l1));
                    }
                    viewCategoriesDTO1.setFieldName(fields);
                    viewCategoriesDTO1.setValues(values);
                    list.add(viewCategoriesDTO1);

                } else {
                    ViewCategoriesDTO viewCategoriesDTO = new ViewCategoriesDTO();
                    viewCategoriesDTO.setName(category.get().getName());
                    viewCategoriesDTO.setId(category.get().getId().toString());
                    list.add(viewCategoriesDTO);
                }

            } else {
                Long[] l1 = {};
                throw new NotFoundException(messageSource.getMessage("notfound.txt",l1,LocaleContextHolder.getLocale()));
            }
        }
        return list;
    }


    @Override
    public List<Object[]> getAllCategory() {

        List<Object[]> list = categoryRepository.getMainCategory();
        if (list.isEmpty()) {
            throw new NullException("no categories available");
        }
        return list;
    }

    @Override
    public List<Object[]> getAllSubCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            if (categoryRepository.checkIfLeaf(id) == 1)
                return categoryRepository.getSubCategoryOfCategory(id);
            else {
                throw new NullException("category is a leaf node");
            }
        } else {
            throw new NotFoundException("category with this id is not present");
        }
    }

    @Override
    public void save(Category category) {

    }

    public List<ViewCategoriesDTO> viewAllCategoriesForAddition(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));

        List<Long> longList = categoryRepository.getIdsOfCategory();
        System.out.println(longList);
        List<ViewCategoriesDTO> list = new ArrayList<>();
        for (Long l : longList) {
            Optional<Category> category = categoryRepository.findById(l);
            if (category.isPresent()) {
                if (categoryRepository.checkIfLeaf(category.get().getId()) == 0&&categoryRepository.getIdOfParent(category.get().getId())!=null) {
                    System.out.println(category.get().getId()+"is leaf");
                } else {
                    ViewCategoriesDTO viewCategoriesDTO = new ViewCategoriesDTO();
                    viewCategoriesDTO.setName(category.get().getName());
                    viewCategoriesDTO.setId(category.get().getId().toString());
                    list.add(viewCategoriesDTO);
                }

            } else {
                Long[] l1 = {};
                throw new NotFoundException(messageSource.getMessage("notfound.txt",l1,LocaleContextHolder.getLocale()));
            }
        }
        return list;
    }



}






















