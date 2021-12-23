package com.cengtel.reto2.modelos;

public class Producto {

    private int id;
    private String name;
    private String description;
    private String price;
    private String favorite;
    private byte[] image;

    public Producto(int id, String name, String description, String price, String favorite, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.favorite = favorite;
        this.image = image;
    }

    public Producto() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
