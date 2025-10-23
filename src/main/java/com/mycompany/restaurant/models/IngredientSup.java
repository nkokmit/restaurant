package com.mycompany.restaurant.models;

public class IngredientSup {
    private int id;
    private float price;
    private Supplier supplier;
    private Ingredient ingredient;

    public IngredientSup() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }
}
