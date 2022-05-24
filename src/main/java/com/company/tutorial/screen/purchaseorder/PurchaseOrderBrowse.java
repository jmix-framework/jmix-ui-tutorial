package com.company.tutorial.screen.purchaseorder;

import io.jmix.ui.screen.*;
import com.company.tutorial.entity.PurchaseOrder;

@UiController("PurchaseOrder.browse")
@UiDescriptor("purchase-order-browse.xml")
@LookupComponent("purchaseOrdersTable")
public class PurchaseOrderBrowse extends StandardLookup<PurchaseOrder> {
}