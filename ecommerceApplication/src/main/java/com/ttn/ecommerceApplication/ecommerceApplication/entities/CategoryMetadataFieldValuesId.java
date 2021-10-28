
package com.ttn.ecommerceApplication.ecommerceApplication.entities;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class CategoryMetadataFieldValuesId implements Serializable
{

    @Column(name = "category_id")

    private Long cid;

    @Column(name = "category_metadata_id")
    private Long mid;

    public CategoryMetadataFieldValuesId()
    {

    }

    public CategoryMetadataFieldValuesId(Long cid, Long mid) {
        this.cid = cid;
        this.mid = mid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }
}

