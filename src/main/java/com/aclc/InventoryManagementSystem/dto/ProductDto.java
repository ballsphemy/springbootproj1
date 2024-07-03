package com.aclc.InventoryManagementSystem.dto;

public class ProductDto {
    private int id;
    private String name;
    private double quantity;
    private double price;
    private String image;
    private double buyPrice;

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public ProductDto() {
    }

    public ProductDto(String name, double quantity, double price, String image,  double buyPrice) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.buyPrice = buyPrice;
    }

    public ProductDto(int id, String name, double quantity, double price, String image, double buyPrice) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.buyPrice = buyPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
