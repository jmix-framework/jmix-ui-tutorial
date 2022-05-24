package com.company.tutorial.screen.purchaseorderdetails;

import io.jmix.ui.screen.*;
import com.company.tutorial.entity.PurchaseOrderDetails;

@UiController("PurchaseOrderDetails.edit")
@UiDescriptor("purchase-order-details-edit.xml")
@EditedEntityContainer("purchaseOrderDetailsDc")
public class PurchaseOrderDetailsEdit extends StandardEditor<PurchaseOrderDetails> {
}