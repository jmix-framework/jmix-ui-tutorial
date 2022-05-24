package com.company.tutorial.screen.stock;

import io.jmix.ui.screen.*;
import com.company.tutorial.entity.Stock;

@UiController("Stock.browse")
@UiDescriptor("stock-browse.xml")
@LookupComponent("stocksTable")
public class StockBrowse extends StandardLookup<Stock> {
}