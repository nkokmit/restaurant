package com.mycompany.restaurant.models;

public class IngredientSup {
    private int id;
    private float price;
    private Ingredient ingredient;

    public IngredientSup() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }
}
