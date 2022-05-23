package com.company.tutorial.entity.product;

import io.jmix.ui.screen.*;
import com.company.tutorial.entity.Product;

@UiController("Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("table")
public class ProductBrowse extends MasterDetailScreen<Product> {
}