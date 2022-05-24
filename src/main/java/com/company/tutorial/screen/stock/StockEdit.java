package com.company.tutorial.screen.stock;

import io.jmix.ui.screen.*;
import com.company.tutorial.entity.Stock;

@UiController("Stock.edit")
@UiDescriptor("stock-edit.xml")
@EditedEntityContainer("stockDc")
public class StockEdit extends StandardEditor<Stock> {
}