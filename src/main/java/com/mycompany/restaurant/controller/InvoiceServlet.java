/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.controller;

import com.mycompany.restaurant.dao.InvoiceDetailDAO;
import com.mycompany.restaurant.dao.ImportInvoiceDAO;
import com.mycompany.restaurant.utils.SessionKeys;
import javax.servlet.*; import javax.servlet.http.*; import java.io.IOException;

public class InvoiceServlet extends HttpServlet {
  private final ImportInvoiceDAO invoiceDAO = new ImportInvoiceDAO();
  private final InvoiceDetailDAO detailDAO = new InvoiceDetailDAO();

  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String path = req.getPathInfo()==null? "": req.getPathInfo();
    Integer invoiceId = (Integer) req.getSession().getAttribute(SessionKeys.CURRENT_INVOICE_ID);
    if (invoiceId == null) { resp.sendRedirect(req.getContextPath()+"/supplier/search"); return; }

    switch (path) {
      case "/confirm":
        req.setAttribute("sumTotal", detailDAO.sumTotal(invoiceId));
        req.setAttribute("lines", detailDAO.listByInvoice(invoiceId));
        req.getRequestDispatcher("/ConfirmInvoice.jsp").forward(req, resp); break;
      case "/print":
        req.setAttribute("invoice", invoiceDAO.getById(invoiceId));
        req.setAttribute("lines", detailDAO.listByInvoice(invoiceId));
        req.getRequestDispatcher("/PrintInvoice.jsp").forward(req, resp); break;
      default:
        resp.sendRedirect(req.getContextPath()+"/ingredient/search");
    }
  }

  @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String path = req.getPathInfo()==null? "": req.getPathInfo();
    Integer invoiceId = (Integer) req.getSession().getAttribute(SessionKeys.CURRENT_INVOICE_ID);
    if ("/commit".equals(path)) {
      try { invoiceDAO.commit(invoiceId); } catch(Exception e){ throw new ServletException(e); }
      // clear supplier? giữ invoiceId để in
      resp.sendRedirect(req.getContextPath()+"/invoice/print");
    } else {
      doGet(req, resp);
    }
  }
}