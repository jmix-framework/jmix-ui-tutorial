package com.company.tutorial.screen.productcatalog;

import com.company.tutorial.entity.Product;
import io.jmix.core.FileRef;
import io.jmix.core.entity.EntityValues;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.FileStorageResource;
import io.jmix.ui.component.Image;
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
    private DataGrid<Product> productsTable;
    @Autowired
    private UiComponents uiComponents;

    @Subscribe("productsTable")
    public void onProductsTableItemClick(DataGrid.ItemClickEvent<Product> event) {
        productDl.setEntityId(Objects.requireNonNull(EntityValues.getId(event.getItem())));
        productDl.load();
    }

    @Install(to = "productsTable.image", subject = "columnGenerator")
    private Component productsTableImageColumnGenerator(DataGrid.ColumnGeneratorEvent<Product> columnGeneratorEvent) {
        Image<FileRef> image = uiComponents.create(Image.NAME);
        image.setSource(FileStorageResource.class)
                .setFileReference(columnGeneratorEvent.getItem().getImage());
        //image.setHeight("100px");
        image.setScaleMode(Image.ScaleMode.SCALE_DOWN);
        return image;
    }
}