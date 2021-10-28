package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.CategoryDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.ProductDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.ProductVariationDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.SellerDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.*;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.*;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.NotFoundException;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.NullException;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.*;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.GetCurrentUser;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class ProductDaoImpl implements ProductDao
{
    Long[] l={};

    @Autowired
    ProductRepository productRepository;

    @Lazy
    @Autowired
    NotificationService notificationService;

    @Autowired
    SellerDao sellerDao;

    @Autowired
    ProductVariationDao productVariationDao;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GetCurrentUser getCurrentUser;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void save(Product product) {
    }








    //adding a new product
    @Override
    public void addNewProduct(ProductDTO product, Long categoryId)
    {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()&&categoryRepository.checkIfLeaf(categoryId)==0)
        {
            if (product.getProductname().equals(categoryRepository.findById(categoryId).get().getName())||product.getProductname().equals(product.getBrand()))
            {
                throw new NullException(messageSource.getMessage("message16.txt",l,LocaleContextHolder.getLocale()));
            }
            Product product1 = modelMapper.map(product,Product.class);
            addProduct(product1,categoryId);
        }
        else
        {
            throw new NotFoundException(messageSource.getMessage("message15.txt",l,LocaleContextHolder.getLocale()));
        }
        Long productId =productRepository.findProduct(product.getProductname());
        Long id = userRepository.findAdmin();
        User user= userRepository.findById(id).get();
        String text= "Product Brand: "+product.getBrand()+"\n"+"Product Description: "+
                product.getDescription()+"\n"+"Product ID: "+productId;
        notificationService.sendToAdmin(user,text);
    }








    //for seller single product
    public ViewProductDTO viewSingleProduct(Long productId)
    {
        String sellername= getCurrentUser.getUser();
        Seller seller= sellerRepository.findByUsername(sellername);
        Optional<Product> product=  productRepository.findById(productId);
        Long[] l = {};
        if (product.isPresent())
        {
            if ((product.get().getSeller().getUsername()).equals(seller.getUsername()))
            {
                ViewProductDTO viewProductDTO = new ViewProductDTO();
                viewProductDTO.setBrand(product.get().getBrand());
                viewProductDTO.setActive(product.get().getisActive());
                viewProductDTO.setCancellable(product.get().isCancellable());
                viewProductDTO.setDescription(product.get().getDescription());
                viewProductDTO.setProductname(product.get().getProductname());
                viewProductDTO.setId(product.get().getID());
                Optional<Category> category = categoryRepository.findById(productRepository.getCategoryId(productId));
                viewProductDTO.setName(category.get().getName());
                List<String > fields = new ArrayList<>();
                List<String > values = new ArrayList<>();
                List<Long> longList1 = categoryMetadataFieldValuesRepository.getMetadataId(category.get().getId());
                for (Long l1 : longList1) {
                    Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l1);
                    fields.add(categoryMetadataField.get().getName());
                    values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category.get().getId(), l1));
                }
                viewProductDTO.setFieldName(fields);
                viewProductDTO.setValues(values);


                return viewProductDTO;
            } else {
                throw new NotFoundException(messageSource.getMessage("message9.txt",l,LocaleContextHolder.getLocale()));
            }

        }
        else
        {
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }
    }









    //for seller all products
    @Override
    public List<ViewProductDTO> getProductDetails(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        String username = getCurrentUser.getUser();
        Seller seller = sellerRepository.findByUsername(username);
        List<Long> longList = productRepository.getProductIdOfSeller(seller.getId(),paging);
        List<ViewProductDTO> list = new ArrayList<>();
        for (Long l : longList)
        {
                list.add(viewSingleProduct(productRepository.findById(l).get().getID()));
        }
      return list;
    }

    @Override
    public void deleteProduct(Long productId)
    {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            System.out.println("1");
            Product product1 = product.get();
            String seller = getCurrentUser.getUser();
            Seller seller1 = sellerRepository.findByUsername(seller);
            if ((product1.getSeller().getUsername()).equals(seller1.getUsername())) {
                productRepository.deleteProductVariation(product1.getID());
                productRepository.deleteProduct(product1.getID(), product1.getProductname());

            } else {
                Long[] l = {product.get().getID()};
                throw new NullException(messageSource.getMessage("message9.txt",l, LocaleContextHolder.getLocale()));
            }
        }
        else
        {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void editProduct(ProductDTO product, Long id) throws IllegalAccessException {

            String seller= getCurrentUser.getUser();
            Seller seller1= sellerRepository.findByUsername(seller);
            Optional<Product> productOptional = productRepository.findById(id);
            Long[] l = {};
          if (productOptional.isPresent()) {
              Product product1 = productOptional.get();

              if ((product1.getSeller().getUsername()).equals(seller1.getUsername()))
              {
                  if (product.getProductname().equals(product1.getBrand())||product.getProductname().equals(product1.getCategory1().getName())||product.getProductname().equals(seller1.getFirstName())||product.getProductname().equals(product.getBrand()))
                  {
                      throw new NullException(messageSource.getMessage("message10.txt",l,LocaleContextHolder.getLocale()));
                  }
                  if (product.getProductname()!=null)
                  {
                      product1.setProductname(product.getProductname());
                  }
                  if (product.getBrand()!=null)
                  {
                      product1.setBrand(product.getBrand());
                  }
                  if (product.getisCancellable()!=null)
                  {
                      product1.setCancellable(product.getisCancellable());
                  }
                  if (product.getisReturnable()!=null)
                  {
                      product1.setReturnable(product.getisReturnable());
                  }
                  if (product.getDescription()!=null)
                  {
                      product1.setDescription(product.getDescription());
                  }
                  if (product.getisActive()!=null)
                  {
                      product1.setActive(product.getisActive());
                  }
                  productRepository.save(product1);
              }
              else {
                  throw new NullException(messageSource.getMessage("message11.txt",l,LocaleContextHolder.getLocale()));
              }
          }
          else
          {
              throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
          }
    }


    public Long getid(String productName) {
        Long id = productRepository.getProductVariationid(productName);
        return id;
    }



    @Override
    public List<Object[]> viewProduct(Long product_id) {
        return null;
    }




    @Override
    public void deactivate(Long productId) {

        Optional<Product> productOptional = productRepository.findById(productId);
        Long[] l = {};
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getisActive() == true) {
                String seller = productRepository.getThatSeller(productId);
                System.out.println(seller);
                Seller seller1 = sellerRepository.findByUsername(seller);
                productRepository.setActiveStatusOfProduct(productId);
                productRepository.setActiveStatusOfProductAndProductVariation(product.getID());
                System.out.println(seller1.getUsername());
                String subject = "Regarding deactivation";
                String text = product.getProductname() + "  got deactivated by admin";
                notificationService.sendToSeller(seller1, subject, text);
            } else {
                throw new NotFoundException(messageSource.getMessage("message12.txt",l,LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }

    }

    @Override
    public void activateProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Long[] l = {};

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            if (product.getisActive() == false) {
                String seller = productRepository.getThatSeller(productId);
                System.out.println(seller);
                Seller seller1 = sellerRepository.findByUsername(seller);
                System.out.println(product.getBrand());
                productRepository.activateTheProduct(productId);
                System.out.println(seller1.getUsername());
                String subject = "Regarding Activation";
                String text = product.getProductname() + "  got activated by admin";
                notificationService.sendToSeller(seller1, subject, text);
            } else {
                throw new NotFoundException(messageSource.getMessage("message13.txt",l,LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }

    }

    @Override
    public List<ViewProductForCustomerDTO> getSimilarProducts(Long productId, Integer pageNo, Integer pageSize, String sortBy) {
        Optional<Product> product = productRepository.findById(productId);
        Long[] l1 = {};
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<ViewProductForCustomerDTO> list = new ArrayList<>();

        if (product.isPresent())
        {
            List<Long> longList = productRepository.getIdOfSimilarProduct(product.get().getCategory1().getId(),product.get().getBrand(),paging);
            System.out.println(longList);
            for (Long l : longList)
            {
                list.add(viewSingleProductForCustomer(productRepository.findById(l).get().getID()));
            }
            return list;

        }
        else
        {
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l1,LocaleContextHolder.getLocale()));
        }
    }


    public ViewProductForCustomerDTO viewSingleProductForCustomer(Long productId)
    {
        Optional<Product> product=  productRepository.findById(productId);
        if (product.isPresent())
        {
                ViewProductForCustomerDTO viewProductDTO = new ViewProductForCustomerDTO();
                viewProductDTO.setBrand(product.get().getBrand());
                viewProductDTO.setActive(product.get().getisActive());
                viewProductDTO.setCancellable(product.get().isCancellable());
                viewProductDTO.setDescription(product.get().getDescription());
                viewProductDTO.setProductname(product.get().getProductname());
                viewProductDTO.setId(product.get().getID());
                Optional<Category> category = categoryRepository.findById(productRepository.getCategoryId(productId));
                viewProductDTO.setName(category.get().getName());
                List<String > fields = new ArrayList<>();
                List<String > values = new ArrayList<>();
                List<Long> longList1 = categoryMetadataFieldValuesRepository.getMetadataId(category.get().getId());
                for (Long l1 : longList1) {
                    Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l1);
                    fields.add(categoryMetadataField.get().getName());
                    values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category.get().getId(), l1));
                }
                viewProductDTO.setFieldName(fields);
                viewProductDTO.setValues(values);
                List<String > list = new ArrayList<>();
                Set<ProductVariation> productVariations = product.get().getProductVariations();
                String firstPath = System.getProperty("user.dir");
                String fileBasePath = firstPath+"/src/main/resources/productVariation/";
                for (ProductVariation productVariation : productVariations)
                {
                    File dir = new File(fileBasePath);
                    if (dir.isDirectory())
                    {
                        File[] files = dir.listFiles();
                        for (File file1 : files) {
                            String value = productVariation.getId().toString()+"_0";
                            if (file1.getName().startsWith(value)) {
                                list.add("http://localhost:8080/viewProductVariationImage/"+file1.getName());
                            }
                        }
                    }
                }
                viewProductDTO.setLinks(list);
                return viewProductDTO;
            }
        else {
            Long[] l = {};
                throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
            }

        }



    public ViewProductForCustomerDTO viewSingleProductForAdmin(Long productId)
    {
        Optional<Product> product=  productRepository.findById(productId);
        if (product.isPresent())
        {
            ViewProductForCustomerDTO viewProductDTO = new ViewProductForCustomerDTO();
            viewProductDTO.setBrand(product.get().getBrand());
            viewProductDTO.setActive(product.get().getisActive());
            viewProductDTO.setCancellable(product.get().isCancellable());
            viewProductDTO.setDescription(product.get().getDescription());
            viewProductDTO.setProductname(product.get().getProductname());
            viewProductDTO.setId(product.get().getID());
            Optional<Category> category = categoryRepository.findById(productRepository.getCategoryId(productId));
            viewProductDTO.setName(category.get().getName());
            List<String > fields = new ArrayList<>();
            List<String > values = new ArrayList<>();
            List<Long> longList1 = categoryMetadataFieldValuesRepository.getMetadataId(category.get().getId());
            for (Long l1 : longList1) {
                Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l1);
                fields.add(categoryMetadataField.get().getName());
                values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category.get().getId(), l1));
            }
            viewProductDTO.setFieldName(fields);
            viewProductDTO.setValues(values);
            List<String > list = new ArrayList<>();
            Set<ProductVariation> productVariations = product.get().getProductVariations();
            String firstPath = System.getProperty("user.dir");
            String fileBasePath = firstPath+"/src/main/resources/productVariation/";
            for (ProductVariation productVariation : productVariations)
            {
                File dir = new File(fileBasePath);
                if (dir.isDirectory())
                {
                    File[] files = dir.listFiles();
                    for (File file1 : files) {
                        String value = productVariation.getId().toString()+"_0";
                        if (file1.getName().startsWith(value)) {
                            list.add("http://localhost:8080/viewProductVariationImage/"+file1.getName());
                        }
                    }
                }
            }
            viewProductDTO.setLinks(list);

            return viewProductDTO;
        }

        else {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }

    }


    public List<ViewProductForCustomerDTO> getProductDetailsForCustomer(Long categoryId,Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        Optional<Category> category = categoryRepository.findById(categoryId);
        List<ViewProductForCustomerDTO> list = new ArrayList<>();
        if (category.isPresent()&&categoryRepository.checkIfLeaf(categoryId)==0)
        {
            List<Long> longList = productRepository.getIdsOdProducts(categoryId,paging);
            System.out.println(longList);
            for (Long l : longList)
            {
                    list.add(viewSingleProductForCustomer(productRepository.findById(l).get().getID()));

            }
            return list;
        }
        else
        {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }

    }



    public List<ViewProductForCustomerDTO> getProductDetailsForAdmin(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<Long> longList = productRepository.getAllId(paging);
        System.out.println(longList);
        List<ViewProductForCustomerDTO> list = new ArrayList<>();
        for (Long l : longList)
        {
                System.out.println(productRepository.findById(l).get().getProductname());
                list.add(viewSingleProductForAdmin(productRepository.findById(l).get().getID()));

        }
      return list;
    }


    @Override
    public void addProduct(Product product, Long categoryid) {
        String sellername = getCurrentUser.getUser();
        Seller seller = sellerRepository.findByUsername(sellername);
        Set<Product> productSet = new HashSet<>();
        Product product1 = new Product();
        product1.setBrand(product.getBrand());
        product1.setActive(false);
        product1.setCancellable(product.getisCancellable());
        product1.setDescription(product.getDescription());
        product1.setProductname(product.getProductname());
        product1.setSeller(seller);
        product1.setCategory1(categoryRepository.findById(categoryid).get());
        productSet.add(product1);
        productRepository.save(product1);
    }

    public FullDetailDTO getAllDetailsOfProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
                FullDetailDTO fullDetailDTO = new FullDetailDTO();
                /*fullDetailDTO.setProductDTO(viewSingleProduct(productId));
               fullDetailDTO.setProductVariationDTO(productVariationDao.getAllProductVariations(productId));
                fullDetailDTO.setSellerName(product.get().getSeller().getCompanyName());
               Set<Address> address = product.get().getSeller().getAddresses();
                List<AddressDTO> addressDTO = new ArrayList<>();
                for (Address address1 : address) {
                    AddressDTO addressDTO1 = modelMapper.map(address1, AddressDTO.class);
                    addressDTO.add(addressDTO1);
                }
                fullDetailDTO.setAddressDTO(addressDTO);
                */return fullDetailDTO;

        } else {
            throw new NotFoundException("not found");
        }
    }
    }




