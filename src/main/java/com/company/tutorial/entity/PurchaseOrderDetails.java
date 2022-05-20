package com.company.tutorial.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "PURCHASE_ORDER_DETAILS", indexes = {
        @Index(name = "IDX_PURCHASEORDERDETAILS", columnList = "PURCHASE_ORDER_ID"),
        @Index(name = "IDX_PURCHASEORDERDETAILS", columnList = "PRODUCT_ID")
})
@Entity
public class PurchaseOrderDetails {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "PRICE_PER_UNIT")
    private Integer pricePerUnit;

    @Column(name = "TOTAL_PRICE", precision = 19, scale = 2)
    private BigDecimal totalPrice;

    @JoinColumn(name = "PURCHASE_ORDER_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private PurchaseOrder purchaseOrder;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Integer pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}