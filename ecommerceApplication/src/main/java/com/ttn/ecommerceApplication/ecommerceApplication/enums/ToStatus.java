package com.ttn.ecommerceApplication.ecommerceApplication.enums;

import java.io.Serializable;

public enum ToStatus implements Serializable
{
    CANCELLED,ORDER_CONFIRMED,ORDER_REJECTED,REFUND_INITIATED,
    CLOSED,ORDER_SHIPPED,DELIVERED,RETURN_REQUESTED,
    RETUN_REJECTED,RETURN_APPROVED,PICK_UP_INITIATED,
    PICK_UP_COMPLETED,REFUND_COMPLETED;

    ToStatus()
    {

    }
}