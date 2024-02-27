package com.module3.entity;

import com.module3.util.Annotation.Column;
import com.module3.util.Annotation.Id;
import com.module3.util.Annotation.Table;

@Table(name = "bill_details")
public class BillDetail {
    @Id
    @Column(name = "Bill_Detail_Id")
    private Long billDetailId;
    @Column(name = "Bill_Id")
    private Long billId;
    @Column(name = "Product_Id")
    private String productId;
    @Column(name = "Quantity")
    private Integer quantity;
    @Column(name = "Price")
    private Float price;

    public BillDetail() {
    }

    public BillDetail(Long billDetailId, Long billId, String productId, Integer quantity, Float price) {
        this.billDetailId = billDetailId;
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(Long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
