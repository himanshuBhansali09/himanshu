package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.CartDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.OrdersDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.CartDTO;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Cart;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.Customer;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.ProductVariation;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.CartRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.CustomerRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.ProductVariationRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartDaoImpl implements CartDao
{
    @Autowired
    GetCurrentUser getCurrentUser;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrdersDao ordersDao;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void addToCart(Long product_variation_id,int quantity) {
        String username = getCurrentUser.getUser();
        Customer user = customerRepository.findByUsername(username);
        Cart cart = new Cart();
        Optional<ProductVariation> productVariation = productVariationRepository.findById(product_variation_id);
        cart.setProductVariation(productVariation.get());
        cart.setQuantity(quantity);
        cart.setCustomer(user);
        productVariation.get().addCarts(cart);
        cartRepository.save(cart);
    }
    @Override
    public void deleteFromCart(Long product_variation_id)
    {
        cartRepository.deleteByProductVariationId(product_variation_id);
    }
    @Override
    public void emptyCart()
    {
        String username = getCurrentUser.getUser();
        Customer customer = customerRepository.findByUsername(username);
        cartRepository.emptyUserCart(customer.getId());
    }
    /*@Override
    public List<Object[]> viewCart() {
        String username = getCurrentUser.getUser();
        Customer customer = customerRepository.findByUsername(username);
        Cart cart = customer.getCart();
        List<Long> getIds = cartRepository.getProductVariationsFromCart(customer.getId());
        System.out.println(cart.getProductVariation());
        CartDTO cartDTO = new CartDTO();
        for (Long list : getIds)
        {
            Optional<ProductVariation> productVariation =  productVariationRepository.findById(list);
            cartDTO.setPrice(productVariation.get().getPrice());
        }
        List<Object[]> list = cartRepository.viewCart(customer.getId());
        if (list.isEmpty())
        {
            System.out.println("cart is empty");
        }
        return list;

    }*/

    @Override
    public List<CartDTO> viewCart() throws JsonProcessingException {
        String username = getCurrentUser.getUser();
        Customer customer = customerRepository.findByUsername(username);
        List<Cart> getCartsOfCustomer = cartRepository.getCarts(customer.getId());
        List<CartDTO> list = new ArrayList<>();
        for (Cart cart:getCartsOfCustomer)
        {
            CartDTO cartDTO = new CartDTO();
            cartDTO.setPrice(cart.getProductVariation().getPrice());
            cartDTO.setPoductName(cart.getProductVariation().getProduct().getProductname());
            cartDTO.setQuantity(cart.getQuantity());
            Map<String ,Object> map = objectMapper.readValue(cart.getProductVariation().getInfoJson(), HashMap.class);
            List<String > field = new ArrayList<>();
            List<String > values = new ArrayList<>();
            for (Map.Entry m : map.entrySet())
            {
                field.add(m.getKey().toString());
                values.add(m.getValue().toString());
            }
            cartDTO.setFields(field);
            cartDTO.setValues(values);
            list.add(cartDTO);
        }

        return list;
    }

    @Override
    public void placeOrderFromCart(Long cartId,String paymentMethod,Long AddressId)
    {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        Cart cart= cartOptional.get();
        ProductVariation productVariation = cart.getProductVariation();
        int quantity= cart.getQuantity();
        ordersDao.placeNewOrder(productVariation.getId(),quantity,paymentMethod,AddressId);
        deleteFromCart(productVariation.getId());
    }

    @Override
    public void orderWholeCart( Long AddressId)
    {
        for(Cart cart : cartRepository.findAll())
        {
            int quantity= cart.getQuantity();
            ProductVariation productVariation =cart.getProductVariation();
            ordersDao.placeNewOrder(productVariation.getId(),quantity,"COD",AddressId);
            emptyCart();
        }
    }
}