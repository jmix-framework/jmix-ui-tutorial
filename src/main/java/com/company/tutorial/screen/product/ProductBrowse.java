package com.company.tutorial.screen.product;

import io.jmix.ui.screen.*;
import com.company.tutorial.entity.Product;

@UiController("Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
public class ProductBrowse extends StandardLookup<Product> {
}