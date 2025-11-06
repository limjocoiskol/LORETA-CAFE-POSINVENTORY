package com.loretacafe.pos;

import java.io.Serializable;

public class InventoryItem implements Serializable {
    private static final long serialVersionUID = 1L;
    public enum StockStatus {
        IN_STOCK("In Stock"),
        RUNNING_LOW("Running Low"),
        LOW_STOCK("Low Stock");

        private final String displayName;

        StockStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private String id;
    private String name;
    private double quantity;
    private String unit; // g, ml, pcs
    private double cost;
    private StockStatus status;

    public InventoryItem(String id, String name, double quantity, String unit, double cost) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.cost = cost;
        this.status = calculateStatus(quantity);
    }

    public InventoryItem(String name, double quantity, String unit, double cost) {
        this(java.util.UUID.randomUUID().toString(), name, quantity, unit, cost);
    }

    private StockStatus calculateStatus(double quantity) {
        // Simple logic: adjust thresholds as needed
        if (quantity <= 0) {
            return StockStatus.LOW_STOCK;
        } else if (quantity < 100) {
            return StockStatus.RUNNING_LOW;
        } else {
            return StockStatus.IN_STOCK;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
        this.status = calculateStatus(quantity);
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public StockStatus getStatus() {
        return status;
    }

    public void setStatus(StockStatus status) {
        this.status = status;
    }

    public String getFormattedQuantity() {
        return String.format("%.0f", quantity) + " " + unit;
    }

    public String getFormattedCost() {
        return "PHP " + String.format("%.2f", cost);
    }

    public int getStatusColor() {
        switch (status) {
            case IN_STOCK:
                return 0xFF4CAF50; // Green
            case RUNNING_LOW:
                return 0xFFFF9800; // Orange
            case LOW_STOCK:
                return 0xFFF44336; // Red
            default:
                return 0xFF757575; // Gray
        }
    }
}

