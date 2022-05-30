package com.company.tutorial.app;

import com.company.tutorial.entity.*;
import io.jmix.core.*;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Component("ims_TestDataCreation")
public class TestDataCreation {

    private static final Logger log = LoggerFactory.getLogger(TestDataCreation.class);

    @Autowired
    private DataManager dataManager;

    @Autowired
    private FileStorageLocator fileStorageLocator;
    @Autowired
    private Resources resources;

    @Autowired
    private Sequences sequences;

    public void createData()
    {
        if (productsExists()) {
            log.info("Products found in DB. Test data generation is skipped...");
            return;
        }

        log.info("No Products found in the DB. Test data will be created...");

        createVendorsWithProducts();
        createStocks();
        createCustomers();
        createPurchaseOrders();
        createOrders();

        log.info("Test data created");
    }

    private void createVendorsWithProducts(){
        createVendorWithProduct("SOS-4561326", "Soft Soda", "Net Monthly Account", "USD", getImage("images/Vanilla_Coke.jpg", "vanilla_coca"), 100, "Item",
                "SSA-43284", new BigDecimal("2"), "Vanilla-flavored version of Coca-Cola", "Coca-Cola Vanilla", new BigDecimal("1.5"));
        createVendorWithProduct("LEM-654567", "Lemonades LLS", "Payment in advance", "USD", getImage("images/Lemonade.jpg", "lemonade"), 20, "Item",
                "LLA-74653", new BigDecimal("4"), "This is a classic lemonade that all ages will enjoy bringing back the days of summers surrounded by friends and family", "Original Lemonade", new BigDecimal("2"));
        createVendorWithProduct("SWT-5932126", "Sweetened beverages", "30 days End of Month", "USD", getImage("images/drink.jpg", "vanilla_coca"), 50, "Pack of 32",
                "YHA-92764", new BigDecimal("10"), "Yoo-hoo is the sweet, easy-to-sip chocolate drink that goes perfectly with all your favorite snacks", "Yoo-hoo Chocolate Drink", new BigDecimal("15"));
    }

    private void createCustomers(){
        createCustomer("GAM-73622", "Garms", "End of month");
        createCustomer("MTN-18625", "Mations", "21st of the month following invoice date");
        createCustomer("FLM-81846", "Flimonade", "Payment in advance");
    }

    private void createCustomer(String accountNumber, String companyName, String contractTerms){
        Customer customer = dataManager.create(Customer.class);
        customer.setAccountNumber(accountNumber);
        customer.setCompanyName(companyName);
        customer.setContractTerms(contractTerms);
        dataManager.save(customer);
    }
    private void createVendorWithProduct(String accountNumber, String companyName, String contractTerms,
                                   String currency, FileRef image, Integer minimumStock, String unit,
                                   String sku, BigDecimal retailPrice, String description,
                                   String productName, BigDecimal price)
    {
        Vendor vendor = dataManager.create(Vendor.class);
        vendor.setAccountNumber(accountNumber);
        vendor.setCompanyName(companyName);
        vendor.setContractTerms(contractTerms);
        Product product = dataManager.create(Product.class);
        product.setCurrency(currency);
        product.setImage(image);
        product.setMinimumStock(minimumStock);
        product.setVendor(vendor);
        product.setUnit(unit);
        product.setSku(sku);
        product.setRetailPrice(retailPrice);
        product.setProductDescription(description);
        product.setProductName(productName);
        product.setPricePerUnit(price);

        dataManager.save(product, vendor);
    }

    private void createStocks(){
        List<Product> products = list(Product.class);
        for (Product entity : products) {
            for (int i = 0; i < 5; i++)
            {
                createStock(entity,random().nextInt(100));
            }
        }
    }

    private void createStock(Product product, Integer inStock){
        Stock stock = dataManager.create(Stock.class);
        stock.setProduct(product);
        stock.setInStock(inStock);
        dataManager.save(stock);
    }

    private void createPurchaseOrders(){
        List<Vendor> vendors = list(Vendor.class);
        for (Vendor entity : vendors) {
            for (int i = 0; i < 5; i++)
            {
                createPurchaseOrder(entity);
            }
        }
    }

    private void createPurchaseOrder(Vendor vendor){
        PurchaseOrderDetails details = dataManager.create(PurchaseOrderDetails.class);
        List<Product> productList = list(Product.class);
        Product product = randomOfList(productList);
        details.setProduct(product);
        details.setQuantity(random().nextInt(100));
        details.setDiscount(random().nextInt(100));
        PurchaseOrder order = dataManager.create(PurchaseOrder.class);
        order.setVendor(vendor);
        order.setStatus(randomStatus());
        order.setDetails(details);
        order.setOrderNumber(String.valueOf(sequences.createNextValue(Sequence.withName("purchase_orders"))));
        dataManager.save(order,details);
    }

    private void createOrders(){
        List<Customer> customers = list(Customer.class);
        for (Customer entity : customers) {
            for (int i = 0; i < 5; i++)
            {
                createOrder(entity);
            }
        }
    }

    private void createOrder(Customer customer){
        OrderDetails details = dataManager.create(OrderDetails.class);
        List<Product> productList = list(Product.class);
        Product product = randomOfList(productList);
        details.setProduct(product);
        details.setQuantity(random().nextInt(100));
        details.setDiscount(random().nextInt(100));
        Order order = dataManager.create(Order.class);
        order.setCustomer(customer);
        order.setStatus(randomStatus());
        order.setOrderDetails(details);
        order.setOrderNumber(String.valueOf(sequences.createNextValue(Sequence.withName("orders"))));
        dataManager.save(order,details);
    }

    private Status randomStatus() {
        int pick = random().nextInt(Status.values().length);
        return Status.values()[pick];
    }

    private <T> T randomOfList(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(random().nextInt(list.size()));
    }

    private <T> List<T> list(Class<T> entityClass) {
        return dataManager.load(entityClass).all().list();
    }

    FileRef getImage(String path, String name){
        FileStorage fileStorage = fileStorageLocator.getDefault();
        InputStream resourceAsStream = resources.getResourceAsStream(path);
        FileRef fileRef = fileStorage.saveStream(name, resourceAsStream);
        return fileRef;
    }

    private boolean productsExists() {
        return !list(Product.class).isEmpty();
    }

    private Random random() {
        return new Random();
    }
}
