package com.warehouse.listener;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMessage {
    private String orderId;
    private Customer customer;
    private List<Item> items;
    private ShippingAddress shippingAddress;

    public String getOrderId() { 
        return orderId; 
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId; 
    }

    public Customer getCustomer() {
        return customer; 
    }
    public void setCustomer(Customer customer) {
        this.customer = customer; 
    }

    public List<Item> getItems() {
        return items; 
    }
    public void setItems(List<Item> items) {
        this.items = items; 
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress; 
    }
    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress; 
    }
    
    public static class Customer {
        private String prename;
        private String name;

        public String getPrename() {
            return prename; 
        }
        public void setPrename(String prename) {
            this.prename = prename; 
        }

        public String getName() {
            return name; 
        }
        public void setName(String name) {
            this.name = name; 
        }
    }

    public static class Item {
        private String productId;
        private int quantity;

        public String getProductId() {
            return productId; 
        }
        public void setProductId(String productId) {
            this.productId = productId; 
        }

        public int getQuantity() {
            return quantity; 
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity; 
        }
    }

    public static class ShippingAddress {
        private String street;
        private String city;
        private String zipCode;
        private String country;

        public String getStreet() {
            return street; 
        }
        public void setStreet(String street) {
            this.street = street; 
        }

        public String getCity() {
            return city; 
        }
        public void setCity(String city) {
            this.city = city; 
        }

        public String getZipCode() {
            return zipCode; 
        }
        public void setZipCode(String zipCode) {
            this.zipCode = zipCode; 
        }

        public String getCountry() {
            return country; 
        }
        public void setCountry(String country) {
            this.country = country; 
        }
    }

    public String getItemsList() {
        return items.stream()
                    .map(item -> "[ProductId=" + item.getProductId() +
                             ", Quantity=" + item.getQuantity() + "]")
                    .collect(Collectors.joining(", "));
    }

    public String getShippingAddressAsString() {
        return String.format("%s, %s, %s, %s",
            shippingAddress.getStreet(),
            shippingAddress.getCity(),
            shippingAddress.getZipCode(),
            shippingAddress.getCountry()
        );
    }
}
