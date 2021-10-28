
package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.CustomerDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.UploadDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.AddressDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.ProfileDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.*;
import com.ttn.ecommerceApplication.ecommerceApplication.enums.FromStatus;
import com.ttn.ecommerceApplication.ecommerceApplication.enums.ToStatus;
import com.ttn.ecommerceApplication.ecommerceApplication.exceptionHandling.*;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.*;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.GetCurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Component
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    GetCurrentUser getCurrentUser;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UploadDao uploadDao;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductVariationRepository  productVariationRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    MessageSource messageSource;



    public String editContact(Customer customer)
    {
        if (customer.getContactNo().matches("(\\+91|0)[0-9]{10}"))
        {
            String user = getCurrentUser.getUser();
            Customer user1 = customerRepository.findByUsername(user);
            user1.setContactNo(customer.getContactNo());
            user1.setModifiedBy(user);
            customerRepository.save(user1);
            return "success";
        }
        else
            {
                Long[] l = {};
             throw new PatternMismatchException(messageSource.getMessage("message5.txt",l, LocaleContextHolder.getLocale()));
            }
    }


    @Transactional
    @Override
    public String getAnSellerAccount(Seller seller) {
        Long[] l = {};

        if (seller.getCompanyName() != null && seller.getCompanyContactNo() != null && seller.getGstNo() != null) {
            String username = getCurrentUser.getUser();
            User customer = userRepository.findByUsername(username);
            if (seller.getCompanyContactNo().matches("(\\+91|0)[0-9]{10}")) {
                if (seller.getGstNo().matches("\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}")) {
                    seller.setId(customer.getId());
                    sellerRepository.insertIntoSeller(seller.getCompanyContactNo(), seller.getCompanyName(), seller.getGstNo(), seller.getId());
                    Set<Role> roles = customer.getRoles();
                    Role role = new Role();
                    role.setRole("ROLE_SELLER");
                    roles.add(role);
                    customer.setRoles(roles);
                    Set<User> users = new HashSet<>();
                    role.setUsers(users);
                    userRepository.save(customer);
                    return "success";
                } else {
                    throw new PatternMismatchException(messageSource.getMessage("message6.txt",l,LocaleContextHolder.getLocale()));
                }
            } else {
                throw new PatternMismatchException(messageSource.getMessage("message5.txt",l,LocaleContextHolder.getLocale()));

            }
        } else {
            throw new NullException(messageSource.getMessage("message7.txt",l,LocaleContextHolder.getLocale()));
        }
    }





    public List<AddressDTO> getAddresses()
    {
        Long[] l = {};
        String username = getCurrentUser.getUser();
        Customer customer = customerRepository.findByUsername(username);
        Set<Address> addresses = customer.getAddresses();
        List<AddressDTO> list = new ArrayList<>();
        if (addresses.isEmpty())
        {
            throw new NotFoundException(messageSource.getMessage("message8.txt",l,LocaleContextHolder.getLocale()));
        }
        else
        {
            for (Address address : addresses)
            {
                AddressDTO addressDTO = modelMapper.map(address,AddressDTO.class);
                list.add(addressDTO);

            }

        }
      return list;
    }

    public List<AddressDTO> getAddressesofSeller()
    {
        Long[] l = {};
        String username = getCurrentUser.getUser();
        Seller customer = sellerRepository.findByUsername(username);
        Set<Address> addresses = customer.getAddresses();
        List<AddressDTO> list = new ArrayList<>();
        if (addresses.isEmpty())
        {
            throw new NotFoundException(messageSource.getMessage("message8.txt",l,LocaleContextHolder.getLocale()));
        }
        else
        {
            for (Address address : addresses)
            {
                AddressDTO addressDTO = modelMapper.map(address,AddressDTO.class);
                list.add(addressDTO);

            }

        }
        return list;
    }


    @Override
    public ProfileDTO viewProfile(HttpServletRequest request) throws IOException {
        String username = getCurrentUser.getUser();
        Customer customer = customerRepository.findByUsername(username);
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName(customer.getFirstName());
        profileDTO.setLastName(customer.getLastName());
        profileDTO.setContactNo(customer.getContactNo());
        profileDTO.setMiddleName(customer.getMiddleName());
        profileDTO.setActive(customer.getisActive());
        String filename = customer.getId().toString()+"_";
        String imageName = uploadDao.downloadImage(filename,request);
        profileDTO.setFileName(imageName);
        return profileDTO;
    }

    public String updateProfile(ProfileDTO customer)
    {
        String username = getCurrentUser.getUser();
        Customer customer1 = customerRepository.findByUsername(username);
        if (customer.getFirstName()!=null)
            customer1.setFirstName(customer.getFirstName());
        if (customer.getMiddleName()!=null)
            customer1.setMiddleName(customer.getMiddleName());
        if (customer.getLastName()!=null)
            customer1.setLastName(customer.getLastName());
        if (customer.getContactNo()!=null)
        {
            if (customer.getContactNo().matches("(\\+91|0)[0-9]{10}"))
            {
                customer1.setContactNo(customer.getContactNo());
            }
            else
            {
                throw new PatternMismatchException("error");
            }
        }
        customer1.setEnabled(true);
        customerRepository.save(customer1);
        return "success";
    }


















    @Override
    public List<Object[]> viewProduct(Long id) {
        Optional<Product> product1 = productRepository.findById(id);
        List<Object[]> list1 = new ArrayList<>();
        if (product1.isPresent())
        {
            if (product1.get().isActive()==true)
            {
                List<Object[]> list = productVariationRepository.getProductVariations(id);
                if (list.isEmpty())
                {
                    throw new NotFoundException("product variations not available for this product");
                }
                else
                    {
                     list1.addAll(productRepository.getSingleProduct(id));
                     list1.addAll(list);
                     return list1;
                    }
             }
            else
            {
                throw new NotFoundException("product is not present or is currently not active");
            }
        }
        else
        {
            throw new NotFoundException("prooduct with this id is not present");
        }
    }

    @Override
    public List<Object[]> viewProducts(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        List<Object[]> list1 = new ArrayList<>();
        if (category.isPresent())
        {
            int result = categoryRepository.checkIfLeaf(categoryId);
            if (result==1)
            {
                  List<Product> list = productRepository.getProducts(categoryId);
                  for (Product product : list)
                  {
                      if (!productVariationRepository.getProductVariations(product.getID()).isEmpty()&&product.isActive()==true)
                      {
                          list1.addAll(productRepository.getSingleProduct(product.getID()));
                      }
                  }
                  return list1;
            }
            else
            {
                throw new NotFoundException("Category is not a leaf category");
            }
        }
        else
        {
            throw new NotFoundException("category with this id is not present");

        }


    }

    public List<Object[]> viewSimilarProducts(Long productId)
    {
        Optional<Product> product = productRepository.findById(productId);
        List<Object[]> list1 = new ArrayList<>();
        if (product.isPresent()&&product.get().isActive()==true)
        {
            Long category_id = productRepository.getCategoryId(productId);
            List<Product> products = productRepository.getProducts(category_id);
            for (Product product1 : products)
            {
                if (product1.getBrand().equals(product.get().getBrand())&&product1.isActive()==true)
                {
                    list1.addAll(productRepository.getSingleProduct(product1.getID()));
                }
            }
            return list1;
        }
        else
        {
            throw new NotFoundException("product with this is not present");
        }

    }

    @Override
    public String cancelOrder(Long orderStatusId) {
        Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(orderStatusId);
        OrderStatus orderStatus = orderStatusOptional.get();

        if (orderStatus.getToStatus() == ToStatus.DELIVERED) {
            throw new NullPointerException("You cant cancel the order.");
        } else {
            orderStatus.setFromStatus(FromStatus.CANCELLED);
            orderStatusRepository.save(orderStatus);
        }
        return "request for cancellation is approved";
    }

    public List<Object[]> getCustomerDetails() {
        String username = getCurrentUser.getUser();
        Customer customer = customerRepository.findByUsername(username);
        List<Object[]> objects = customerRepository.getDetails(username);
        return objects;
    }

    @Override
    public Customer getCustomer() {
        String username = getCurrentUser.getUser();
        Customer customer = customerRepository.findByUsername(username);
        return customer;
    }



    @Override
    public String  returnRequested(Long orderStatusId) {
        Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(orderStatusId);
        OrderStatus orderStatus = orderStatusOptional.get();
        if (orderStatus.getToStatus() == ToStatus.CLOSED) {
            throw new NullPointerException("You can not request for return ");
        } else if (orderStatus.getToStatus() == ToStatus.DELIVERED) {
            orderStatus.setFromStatus(FromStatus.RETURN_REQUESTED);
            orderStatusRepository.save(orderStatus);
            return "return request approved";
        }
        else
        {
            throw new NullPointerException("You can not request for return ");
        }
    }


}
