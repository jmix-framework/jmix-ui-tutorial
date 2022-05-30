package com.company.tutorial.screen.productcatalog;

import com.company.tutorial.bean.ProductService;
import com.company.tutorial.entity.Product;
import io.jmix.core.FileRef;
import io.jmix.core.entity.EntityValues;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@UiController("ProductCatalogScreen")
@UiDescriptor("product-catalog-screen.xml")
public class ProductCatalogScreen extends Screen {
    @Autowired
    private InstanceLoader<Product> productDl;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private TextField<Integer> inStockField;
    @Autowired
    private TextField<Integer> reservedSaleField;
    @Autowired
    private TextField<Integer> onPurchaseOrderField;
    @Autowired
    private TextField<Integer> availableField;

    @Autowired
    private ProductService productService;
    @Autowired
    private Button showValuesBtn;

    @Subscribe("productsTable")
    public void onProductsTableItemClick(DataGrid.ItemClickEvent<Product> event) {
        initializeFields();
        loadClickedProduct(event.getItem());

    }
    private void loadClickedProduct(Product product){
        productDl.setEntityId(Objects.requireNonNull(EntityValues.getId(product)));
        productDl.load();
    }
    private void initializeFields()
    {
        inStockField.setValue(0);
        inStockField.setEnabled(false);

        reservedSaleField.setValue(0);
        reservedSaleField.setEnabled(false);

        onPurchaseOrderField.setValue(0);
        onPurchaseOrderField.setEnabled(false);

        availableField.setValue(0);
        availableField.setEnabled(false);

        showValuesBtn.setEnabled(true);
    }

    @Install(to = "productsTable.image", subject = "columnGenerator")
    private Component productsTableImageColumnGenerator(DataGrid.ColumnGeneratorEvent<Product> columnGeneratorEvent) {
        Image<FileRef> image = uiComponents.create(Image.NAME);
        FileRef imageRef = columnGeneratorEvent.getItem().getImage();
        if (imageRef != null) {
            image.setSource(FileStorageResource.class)
                    .setFileReference(columnGeneratorEvent.getItem().getImage());
            image.setScaleMode(Image.ScaleMode.SCALE_DOWN);
        }
        return image;
    }

    @Install(to = "productsTable.pricePerUnit", subject = "columnGenerator")
    private String productsTablePricePerUnitColumnGenerator(DataGrid.ColumnGeneratorEvent<Product> columnGeneratorEvent) {
        return columnGeneratorEvent.getItem().getPricePerUnit() + " " + columnGeneratorEvent.getItem().getCurrency();
    }

    @Install(to = "productsTable.retailPrice", subject = "columnGenerator")
    private String productsTableRetailPriceColumnGenerator(DataGrid.ColumnGeneratorEvent<Product> columnGeneratorEvent) {
        return columnGeneratorEvent.getItem().getRetailPrice() + " " + columnGeneratorEvent.getItem().getCurrency();
    }

    @Subscribe("showValuesBtn")
    public void onShowValuesBtnClick(Button.ClickEvent event) {
        Product product = productDl.getContainer().getItem();
        Integer inStock = productService.getInStockByProduct(product);
        if (inStock != null)
            inStockField.setValue(inStock);
        Integer reservedForSale = productService.getReservedForSalesByProduct(product);
        if (reservedForSale != null)
            reservedSaleField.setValue(reservedForSale);
        Integer onPurchaseOrder = productService.getOnPurchaseOrderByProduct(product);
        if (onPurchaseOrder != null)
            onPurchaseOrderField.setValue(onPurchaseOrder);
        if (inStock != null && reservedForSale != null){
            int available = inStock-reservedForSale;
            if (available >= 0)
                availableField.setValue(available);
        }
        showStockFields();
    }

    private void showStockFields(){
        inStockField.setEnabled(true);
        reservedSaleField.setEnabled(true);
        onPurchaseOrderField.setEnabled(true);
        availableField.setEnabled(true);
        showValuesBtn.setEnabled(false);
    }
}