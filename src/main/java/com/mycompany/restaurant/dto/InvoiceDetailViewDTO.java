/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.dto;

/**
 *
 * @author hungmit
 */
public class InvoiceDetailViewDTO {
    public int detailId;
    public String ingredientName;
    public float qty;
    public float unitPrice;
    public float lineTotal;

    // --- Getters ---

    public int getDetailId() {
        return detailId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public float getQty() {
        return qty;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public float getLineTotal() {
        return lineTotal;
    }

    // --- Setters ---

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setLineTotal(float lineTotal) {
        this.lineTotal = lineTotal;
    }
}