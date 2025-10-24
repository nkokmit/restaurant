// src/main/java/com/mycompany/restaurant/dto/IngredientViewDTO.java
package com.mycompany.restaurant.dto;

public class IngredientViewDTO {
    private int ingredientSupId;
    private int ingredientId;
    private String name;
    private String type;
    private String unit;     
    private float price;    

    public IngredientViewDTO() {}

    public int getIngredientSupId() { return ingredientSupId; }
    public void setIngredientSupId(int ingredientSupId) { this.ingredientSupId = ingredientSupId; }

    public int getIngredientId() { return ingredientId; }
    public void setIngredientId(int ingredientId) { this.ingredientId = ingredientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
}
