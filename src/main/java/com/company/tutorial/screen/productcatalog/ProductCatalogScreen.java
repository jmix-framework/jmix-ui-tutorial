package com.company.tutorial.screen.productcatalog;

import com.company.tutorial.bean.ProductService;
import com.company.tutorial.entity.Product;
import io.jmix.core.FileRef;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

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
        productDl.setEntityId(product.getId());
        productDl.load();
    }

    private void initializeFields() {
        inStockField.setValue(null);
        reservedSaleField.setValue(null);
        onPurchaseOrderField.setValue(null);
        availableField.setValue(null);

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

        int inStock = productService.getInStockByProduct(product);
        inStockField.setValue(inStock);

        int reservedForSale = productService.getReservedForSalesByProduct(product);
        reservedSaleField.setValue(reservedForSale);

        int onPurchaseOrder = productService.getOnPurchaseOrderByProduct(product);
        onPurchaseOrderField.setValue(onPurchaseOrder);

        int available = inStock - reservedForSale;
        availableField.setValue(available);

        showValuesBtn.setEnabled(false);
    }

}