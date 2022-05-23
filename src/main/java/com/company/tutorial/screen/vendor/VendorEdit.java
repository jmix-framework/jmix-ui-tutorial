package com.company.tutorial.screen.vendor;

import io.jmix.ui.screen.*;
import com.company.tutorial.entity.Vendor;

@UiController("Vendor.edit")
@UiDescriptor("vendor-edit.xml")
@EditedEntityContainer("vendorDc")
public class VendorEdit extends StandardEditor<Vendor> {
}