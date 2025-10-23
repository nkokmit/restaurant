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
    private ImportInvoice invoice;
    private IngredientSup ingSup;
    private float quantity;
    private float lineTotal;

    public InvoiceDetail() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public ImportInvoice getInvoice() { return invoice; }
    public void setInvoice(ImportInvoice invoice) { this.invoice = invoice; }

    public IngredientSup getIngSup() { return ingSup; }
    public void setIngSup(IngredientSup ingSup) { this.ingSup = ingSup; }

    public float getQuantity() { return quantity; }
    public void setQuantity(float quantity) { this.quantity = quantity; }

    public float getLineTotal() { return lineTotal; }
    public void setLineTotal(float lineTotal) { this.lineTotal = lineTotal; }
}