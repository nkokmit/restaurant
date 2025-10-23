package com.mycompany.restaurant.controller;

import com.mycompany.restaurant.dao.ImportInvoiceDAO;
import com.mycompany.restaurant.dao.IngredientSupDAO;
import com.mycompany.restaurant.dao.InvoiceDetailDAO;
import com.mycompany.restaurant.dto.IngredientViewDTO;
import com.mycompany.restaurant.models.ImportInvoice;
import com.mycompany.restaurant.utils.SessionKeys;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class IngredientServlet extends HttpServlet {

    private final IngredientSupDAO ingSupDAO = new IngredientSupDAO();
    private final ImportInvoiceDAO invoiceDAO = new ImportInvoiceDAO();
    private final InvoiceDetailDAO detailDAO = new InvoiceDetailDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo() == null ? "" : req.getPathInfo();
        HttpSession ses = req.getSession();

        if ("/start".equals(path)) {
          
            int supId = Integer.parseInt(req.getParameter("supplierId"));
            ses.setAttribute(SessionKeys.CURRENT_SUPPLIER_ID, supId);

            Integer invoiceId = (Integer) ses.getAttribute(SessionKeys.CURRENT_INVOICE_ID);
            if (invoiceId == null) {
                Integer staffId = (Integer) ses.getAttribute(SessionKeys.CURRENT_STAFF_ID);
                if (staffId == null) {
                    resp.sendRedirect(req.getContextPath() + "/login.jsp");
                    return;
                }
                try {
                    ImportInvoice iv = invoiceDAO.createDraft(supId, staffId); // status=0
                    ses.setAttribute(SessionKeys.CURRENT_INVOICE_ID, iv.getId());
                } catch (Exception e) {
                    throw new ServletException(e);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/ingredient/search");
            return;
        }

        if ("/addLine".equals(path)) {
            Integer invoiceId = (Integer) ses.getAttribute(SessionKeys.CURRENT_INVOICE_ID);
            if (invoiceId == null) {
                resp.sendRedirect(req.getContextPath() + "/supplier/search");
                return;
            }
            int ingSupId = Integer.parseInt(req.getParameter("ingSupId"));
            float qty = Float.parseFloat(req.getParameter("qty"));
            float unitPrice = Float.parseFloat(req.getParameter("price"));

            try {
                detailDAO.addLine(invoiceId, ingSupId, qty, unitPrice); 
            } catch (Exception e) {
                throw new ServletException(e);
            }
            resp.sendRedirect(req.getContextPath() + "/ingredient/search");
            return;
        }

        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession ses = req.getSession();
        Integer supId = (Integer) ses.getAttribute(SessionKeys.CURRENT_SUPPLIER_ID);
        if (supId == null) {
            resp.sendRedirect(req.getContextPath() + "/supplier/search");
            return;
        }

        String q = req.getParameter("q");
        List<IngredientViewDTO> items = ingSupDAO.searchIngredientbyName(supId, q);
        req.setAttribute("items", items);

        Integer invoiceId = (Integer) ses.getAttribute(SessionKeys.CURRENT_INVOICE_ID);
        if (invoiceId != null) {
            req.setAttribute("lines", detailDAO.listByInvoice(invoiceId));
        }
        req.getRequestDispatcher("/SearchIngredient.jsp").forward(req, resp);
    }
}
