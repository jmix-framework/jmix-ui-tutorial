package com.company.tutorial.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "VENDOR")
@Entity
public class Vendor {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OneToMany(mappedBy = "vendor")
    private List<Contact> contacts;

    @InstanceName
    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "CONTRACT_TERMS")
    private String contractTerms;

    @OneToMany(mappedBy = "vendor")
    private List<Product> products;

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getContractTerms() {
        return contractTerms;
    }

    public void setContractTerms(String contractTerms) {
        this.contractTerms = contractTerms;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}