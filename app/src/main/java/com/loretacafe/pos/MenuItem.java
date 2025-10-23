package com.loretacafe.pos;

public class MenuItem {
    private String name;
    private double price;
    private String category;
    private boolean isFavorite;
    private boolean isNew;
    private String size; // For items with sizes like "8oz | 12oz"

    public MenuItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.isFavorite = false;
        this.isNew = false;
        this.size = "";
    }

    public MenuItem(String name, double price, String category, boolean isNew) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.isFavorite = false;
        this.isNew = isNew;
        this.size = "";
    }

    public MenuItem(String name, String size, String category) {
        this.name = name;
        this.size = size;
        this.category = category;
        this.price = 0;
        this.isFavorite = false;
        this.isNew = false;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String category() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFormattedPrice() {
        if (size != null && !size.isEmpty()) {
            return size;
        }
        return "₱ " + String.format("%.2f", price);
    }
}