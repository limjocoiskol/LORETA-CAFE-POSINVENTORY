package com.loretacafe.pos;

import java.io.Serializable;

public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private MenuItem menuItem;
    private String selectedSize;
    private int quantity;
    private double unitPrice;
    private double totalPrice;

    public CartItem(MenuItem menuItem, String selectedSize, int quantity, double unitPrice) {
        this.id = java.util.UUID.randomUUID().toString();
        this.menuItem = menuItem;
        this.selectedSize = selectedSize;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice * quantity;
    }

    public String getId() {
        return id;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public String getSelectedSize() {
        return selectedSize;
    }

    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = unitPrice * quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice * quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getFormattedTotalPrice() {
        return "₱ " + String.format("%.2f", totalPrice);
    }

    public String getFormattedUnitPrice() {
        return "₱ " + String.format("%.2f", unitPrice);
    }
}

