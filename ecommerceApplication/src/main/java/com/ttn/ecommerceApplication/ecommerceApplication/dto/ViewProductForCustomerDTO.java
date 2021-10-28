package com.ttn.ecommerceApplication.ecommerceApplication.dto;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ViewProductForCustomerDTO extends ViewProductDTO
{
    List<String > links;

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
