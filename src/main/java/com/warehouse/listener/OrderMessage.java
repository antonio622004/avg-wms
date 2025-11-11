package com.warehouse.listener;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMessage {

    private Order order;
    private String reservationMessage;
    private Payment payment;

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    public String getReservationMessage() {
        return reservationMessage;
    }
    public void setReservationMessage(String reservationMessage) {
        this.reservationMessage = reservationMessage;
    }

    public Payment getPayment() {
        return payment;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getOrderId() {
        return order != null ? order.getOrderId() : null;
    }

    public String getItemsList() {
        if (order == null || order.getItems() == null) {
            return "";
        }
        return order.getItems().stream()
                .map(item -> "[ProductId=" + item.getProductId() +
                        ", Quantity=" + item.getQuantity() +
                        ", Price=" + item.getPrice() + "]")
                .collect(Collectors.joining(", "));
    }

    public String getShippingAddressAsString() {
        if (order == null || order.getShippingAddress() == null) {
            return "";
        }
        ShippingAddress shippingAddress = order.getShippingAddress();
        return String.format("%s, %s, %s, %s",
                shippingAddress.getStreet(),
                shippingAddress.getCity(),
                shippingAddress.getZipCode(),
                shippingAddress.getCountry()
        );
    }

    public static class Order {
        private String orderId;
        private Customer customer;
        private List<Item> items;
        private BigDecimal totalAmount;
        private ShippingAddress shippingAddress;
        private String status;

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

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }
        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public ShippingAddress getShippingAddress() {
            return shippingAddress;
        }
        public void setShippingAddress(ShippingAddress shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class Customer {
        private String customerId;
        private String prename;
        private String name;

        public String getCustomerId() {
            return customerId;
        }
        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

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
        private BigDecimal price;

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

        public BigDecimal getPrice() {
            return price;
        }
        public void setPrice(BigDecimal price) {
            this.price = price;
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

    public static class Payment {
        private String orderId;
        private BigDecimal amount;
        private String currency;
        private String method;
        private String status;
        private String createdAt;
        private String updatedAt;

        public String getOrderId() {
            return orderId;
        }
        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public BigDecimal getAmount() {
            return amount;
        }
        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }
        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getMethod() {
            return method;
        }
        public void setMethod(String method) {
            this.method = method;
        }

        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }
        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
