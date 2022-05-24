package com.company.tutorial.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "ORDER_DETAILS", indexes = {
        @Index(name = "IDX_ORDERDETAILS_PRODUCT_ID", columnList = "PRODUCT_ID")
})
@Entity
public class OrderDetails {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @InstanceName
    @Column(name = "SKU")
    private String sku;

    @Column(name = "PRICE_PER_UNIT")
    private BigDecimal pricePerUnit;

    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    @JoinColumn(name = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}