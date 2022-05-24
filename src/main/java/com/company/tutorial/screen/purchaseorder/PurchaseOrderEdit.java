package com.company.tutorial.screen.purchaseorder;

import io.jmix.ui.screen.*;
import com.company.tutorial.entity.PurchaseOrder;

@UiController("PurchaseOrder.edit")
@UiDescriptor("purchase-order-edit.xml")
@EditedEntityContainer("purchaseOrderDc")
public class PurchaseOrderEdit extends StandardEditor<PurchaseOrder> {
}