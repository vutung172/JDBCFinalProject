package com.module3.entity;

import com.module3.util.Annotation.Column;
import com.module3.util.Annotation.Id;
import com.module3.util.Annotation.Index;
import com.module3.util.Annotation.Table;

import java.util.Date;

@Table(name = "products")
public class Product {
    @Id
    @Column(name = "Product_Id")
    private String productId;
    @Index(name = "Product_Name")
    @Column(name = "Product_Name")
    private String productName;
    @Column(name = "Manufacturer")
    private String manufacturer;
    @Column(name = "Created")
    private Date created;
    @Column(name = "Batch")
    private Integer batch;
    @Column(name = "Quantity")
    private Integer quantity;
    @Column(name = "Product_Status")
    private Boolean productStatus;

    public Product() {
    }

    public Product(String productId, String productName, String manufacturer, Date created, Integer batch, Integer quantity, Boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.created = created;
        this.batch = batch;
        this.quantity = quantity;
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Boolean productStatus) {
        this.productStatus = productStatus;
    }
}
