/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.models;

/**
 *
 * @author hungmit
 */
public class InvoiceDetail {
    private int id;
    private IngredientSup ingSup;
    private float quantity;
    
    public InvoiceDetail() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public IngredientSup getIngSup() { return ingSup; }
    public void setIngSup(IngredientSup ingSup) { this.ingSup = ingSup; }

    public float getQuantity() { return quantity; }
    public void setQuantity(float quantity) { this.quantity = quantity; }

}