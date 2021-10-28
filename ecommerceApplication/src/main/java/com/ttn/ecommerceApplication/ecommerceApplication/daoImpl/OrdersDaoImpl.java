package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.OrdersDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.ProductVariationDao;
import com.ttn.ecommerceApplication.ecommerceApplication.entities.*;
import com.ttn.ecommerceApplication.ecommerceApplication.enums.FromStatus;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.*;
import com.ttn.ecommerceApplication.ecommerceApplication.utilities.GetCurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrdersDaoImpl implements OrdersDao
{
    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    GetCurrentUser getCurrentDetails;

    @Autowired
    ProductVariationDao productVariationDao;

    @Override
    public void placeNewOrder(Long productVariationId,int quantity, String paymentMethod, Long AddressId)
    {
        String username= getCurrentDetails.getUser();
        Customer customer =customerRepository.findByUsername(username);
        productVariationDao.updateQuantity(productVariationId,quantity);
        Optional<ProductVariation> productVariationOptional=  productVariationRepository.findById(productVariationId);
        ProductVariation productVariation =productVariationOptional.get();
        if (productVariation.isActive()==false)
        {
            throw new NullPointerException("this item is not available");
        }
        Optional<Address> addressOptional = addressRepository.findById(AddressId);
        Address address = null;
        if (addressOptional.isPresent()) {
            address = addressOptional.get();
        }
        else
        {
            throw new NullPointerException("add an address to place order");
        }
        Orders orders = new Orders();
        orders.setAmountPaid((productVariationRepository.getPrice(productVariationId))*quantity);
        orders.setCustomer(customer);
        orders.setPaymentMethod(paymentMethod);
        orders.setCustomerAddressCity(address.getCity());
        orders.setCustomerAddressCountry(address.getCountry());
        orders.setCustomerAddressLabel(address.getLabel());
        orders.setCustomerAddressState(address.getState());
        orders.setCustomerAddressZipCode(address.getZipcode());

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setPrice((productVariationRepository.getPrice(productVariationId))*quantity);
        orderProduct.setQuantity(quantity);
        orderProduct.setOrders(orders);
        orderProduct.setProductVariationMetaData(productVariation.getInfoJson());
        orderProduct.setProductVariation(productVariation);

        OrderStatus orderStatus= new OrderStatus();
        orderStatus.setFromStatus(FromStatus.ORDER_PLACED);
        orderStatus.setOrderProduct(orderProduct);

        ordersRepository.save(orders);
        productVariationRepository.save(productVariation);
        orderProductRepository.save(orderProduct);
        addressRepository.save(address);
        orderStatusRepository.save(orderStatus);
    }
}

