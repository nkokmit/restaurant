/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.dto;
import com.mycompany.restaurant.models.ImportInvoice;
import com.mycompany.restaurant.models.IngredientSup;
/**
 *
 * @author hungmit
 */
public class InvoiceDetailViewDTO {
    public int detailId;
    private int invoiceId;      
    private int ingredientSupId;
    public String ingredientName;
    public float quantity;
    public float unitPrice;
    public float lineTotal;

    // --- Getters ---

    public int getDetailId() {
        return detailId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public float getQuantity() {
        return quantity;
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

    public void setQuantity(float qty) {
        this.quantity = qty;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setLineTotal(float lineTotal) {
        this.lineTotal = lineTotal;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getIngredientSupId() {
        return ingredientSupId;
    }

    public void setIngredientSupId(int ingredientSupId) {
        this.ingredientSupId = ingredientSupId;
    }

    
    
}