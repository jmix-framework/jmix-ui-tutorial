package com.company.tutorial.screen.order;

import io.jmix.ui.screen.*;
import com.company.tutorial.entity.Order;

@UiController("Order_.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {
}