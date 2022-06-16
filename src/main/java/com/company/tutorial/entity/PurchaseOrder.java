package com.company.tutorial.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "PURCHASE_ORDER", indexes = {
        @Index(name = "IDX_PURCHASEORDER_VENDOR_ID", columnList = "VENDOR_ID"),
        @Index(name = "IDX_PURCHASEORDER_DETAILS_ID", columnList = "DETAILS_ID")
})
@Entity
public class PurchaseOrder {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "VENDOR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vendor vendor;

    @Column(name = "STATUS")
    private String status;

    @InstanceName
    @Column(name = "ORDER_NUMBER")
    private String orderNumber;

    @JoinColumn(name = "DETAILS_ID")
    @Composition
    @OneToOne(fetch = FetchType.LAZY)
    private PurchaseOrderDetails details;

    public PurchaseOrderDetails getDetails() {
        return details;
    }

    public void setDetails(PurchaseOrderDetails details) {
        this.details = details;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Status getStatus() {
        return status == null ? null : Status.fromId(status);
    }

    public void setStatus(Status status) {
        this.status = status == null ? null : status.getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}