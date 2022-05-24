package com.company.tutorial.screen.orderdetails;

import io.jmix.ui.screen.*;
import com.company.tutorial.entity.OrderDetails;

@UiController("OrderDetails.edit")
@UiDescriptor("order-details-edit.xml")
@EditedEntityContainer("orderDetailsDc")
public class OrderDetailsEdit extends StandardEditor<OrderDetails> {
}