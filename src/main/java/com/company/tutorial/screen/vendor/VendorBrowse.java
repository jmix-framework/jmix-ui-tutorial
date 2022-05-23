package com.company.tutorial.screen.vendor;

import io.jmix.ui.screen.*;
import com.company.tutorial.entity.Vendor;

@UiController("Vendor.browse")
@UiDescriptor("vendor-browse.xml")
@LookupComponent("vendorsTable")
public class VendorBrowse extends StandardLookup<Vendor> {
}