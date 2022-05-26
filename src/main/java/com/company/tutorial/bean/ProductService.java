package com.company.tutorial.bean;

import com.company.tutorial.entity.Product;
import com.company.tutorial.entity.Status;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductService {
    @Autowired
    private DataManager dataManager;

    public Integer getInStockByProduct(Product p) {
        return dataManager.loadValue(
                        "select sum(s.inStock) from Stock s where s.product = :product",
                        Integer.class
                )
                .parameter("product", p)
                .one();
    }

    public Integer getReservedForSalesByProduct(Product p) {
        return dataManager.loadValue(
                        "select SUM(o.orderDetails.quantity) from Order_ o where o.status = :status and o.orderDetails.product = :product",
                        Integer.class
                )
                .parameter("product", p)
                .parameter("status", Status.OPEN)
                .one();
    }

    public Integer getOnPurchaseOrderByProduct(Product p) {
        return dataManager.loadValue(
                        "select SUM(o.details.quantity) from PurchaseOrder o where o.status = :status and o.details.product = :product",
                        Integer.class
                )
                .parameter("product", p)
                .parameter("status", Status.OPEN)
                .one();
    }
}