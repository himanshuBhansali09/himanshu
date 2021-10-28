package com.ttn.ecommerceApplication.ecommerceApplication.enums;

import java.io.Serializable;

public enum FromStatus implements Serializable
{
    CANCELLED,ORDER_PLACED,ORDER_REJECTED,
    ORDER_CONFIRMED,ORDER_SHIPPED,DELIVERED,
    RETURN_REQUESTED,
    RETURN_REJECTED,RETURN_APPROVED,
    PICK_UP_INITITED,
    PICK_UP_COMPLETED,
    REFUND_INITIATED,REFUND_COMPLETED;

    FromStatus() {
    }
}

