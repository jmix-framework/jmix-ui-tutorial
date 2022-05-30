package com.company.tutorial.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;

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

    @Column(name = "DISCOUNT")
    private Integer discount;

    @JoinColumn(name = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @DependsOnProperties({"discount", "product"})
    @JmixProperty
    public BigDecimal getPricePerUnit() {
        return getProduct().getPricePerUnit().multiply(new BigDecimal(100 - getDiscount()).divide(BigDecimal.valueOf(100)));
    }

    @DependsOnProperties({"quantity", "pricePerUnit"})
    @JmixProperty
    public BigDecimal getTotalPrice() {
        return getPricePerUnit().multiply(new BigDecimal(getQuantity()));
    }
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}