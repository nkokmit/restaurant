/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.models;

/**
 *
 * @author hungmit
 */
import java.util.Date;
import java.util.List;

public class ImportInvoice {
    private int id;
    private Date dateIn;
    private int status;
    private String note;
    private WarehouseStaff staff;
    private Supplier sup;
    private List<InvoiceDetail> details;

    public ImportInvoice() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDateIn() { return dateIn; }
    public void setDateIn(Date dateIn) { this.dateIn = dateIn; }
    
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public WarehouseStaff getStaff() { return staff; }
    public void setStaff(WarehouseStaff staff) { this.staff = staff; }

    public Supplier getSup() { return sup; }
    public void setSup(Supplier sup) { this.sup = sup; }

    public List<InvoiceDetail> getDetails() { return details; }
    public void setDetails(List<InvoiceDetail> details) { this.details = details; }
}
