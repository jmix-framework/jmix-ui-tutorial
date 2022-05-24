package com.company.tutorial.screen.productcatalog;

import com.company.tutorial.entity.Order;
import com.company.tutorial.entity.OrderDetails;
import com.company.tutorial.entity.Product;
import com.company.tutorial.entity.Status;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.entity.EntityValues;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@UiController("ProductCatalogScreen")
@UiDescriptor("product-catalog-screen.xml")
public class ProductCatalogScreen extends Screen {
    @Autowired
    private InstanceLoader<Product> productDl;
    @Autowired
    private DataGrid<Product> productsTable;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private TextField<Integer> inStockField;
    @Autowired
    private TextField<Integer> reservedSaleField;
    @Autowired
    private TextField<Integer> onPurchaseOrderField;
    @Autowired
    private TextField<Integer> availableField;

    @Subscribe("productsTable")
    public void onProductsTableItemClick(DataGrid.ItemClickEvent<Product> event) {
        initializeFields();
        productDl.setEntityId(Objects.requireNonNull(EntityValues.getId(event.getItem())));
        productDl.load();
        Integer inStock = getInStockByProduct(event.getItem());
        if (inStock != null)
            inStockField.setValue(inStock);
        Integer reservedForSale = getReservedForSalesByProduct(event.getItem());
        if (reservedForSale != null)
            reservedSaleField.setValue(reservedForSale);
        Integer onPurchaseOrder = getOnPurchaseOrderByProduct(event.getItem());
        if (onPurchaseOrder != null)
            onPurchaseOrderField.setValue(onPurchaseOrder);
        if (inStock != null && reservedForSale != null)
            availableField.setValue(inStock-reservedForSale);
    }

    private void initializeFields()
    {
        inStockField.setValue(0);
        reservedSaleField.setValue(0);
        onPurchaseOrderField.setValue(0);
        availableField.setValue(0);
    }
    private Integer getInStockByProduct(Product p) {
        return dataManager.loadValue(
                        "select sum(s.inStock) from Stock s where s.product = :product",
                        Integer.class
                )
                .parameter("product", p)
                .one();
    }

    /*private Optional<Integer> getInStockByProduct(Product p) {
        return Optional.of(dataManager.loadValue(
                        "select sum(s.inStock) from Stock s where s.product = :product",
                        Integer.class
                )
                .parameter("product", p)
                .one());
    }*/

    private Integer getReservedForSalesByProduct(Product p) {
        return dataManager.loadValue(
                        "select SUM(o.orderDetails.quantity) from Order_ o where o.status = :status and o.orderDetails.product = :product",
                        Integer.class
                )
                .parameter("product", p)
                .parameter("status", Status.OPEN)
                .one();
    }

    private Integer getOnPurchaseOrderByProduct(Product p) {
        return dataManager.loadValue(
                        "select SUM(o.details.quantity) from PurchaseOrder o where o.status = :status and o.details.product = :product",
                        Integer.class
                )
                .parameter("product", p)
                .parameter("status", Status.OPEN)
                .one();
    }

    @Install(to = "productsTable.image", subject = "columnGenerator")
    private Component productsTableImageColumnGenerator(DataGrid.ColumnGeneratorEvent<Product> columnGeneratorEvent) {
        Image<FileRef> image = uiComponents.create(Image.NAME);
        image.setSource(FileStorageResource.class)
                .setFileReference(columnGeneratorEvent.getItem().getImage());
        image.setScaleMode(Image.ScaleMode.SCALE_DOWN);
        return image;
    }
}