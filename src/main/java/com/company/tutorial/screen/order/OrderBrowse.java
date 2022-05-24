package com.company.tutorial.screen.order;

import io.jmix.ui.screen.*;
import com.company.tutorial.entity.Order;

@UiController("Order_.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}