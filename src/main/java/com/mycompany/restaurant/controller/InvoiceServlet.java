package com.mycompany.restaurant.controller;

import com.mycompany.restaurant.dao.ImportInvoiceDAO;
import com.mycompany.restaurant.dao.InvoiceDetailDAO;
import com.mycompany.restaurant.dto.InvoiceDetailViewDTO;
import com.mycompany.restaurant.models.ImportInvoice;
import com.mycompany.restaurant.models.Supplier;
import com.mycompany.restaurant.models.WarehouseStaff;
import com.mycompany.restaurant.utils.SessionKeys;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class InvoiceServlet extends HttpServlet {

    private final ImportInvoiceDAO invoiceDAO = new ImportInvoiceDAO();
    private final InvoiceDetailDAO detailDAO   = new InvoiceDetailDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo() == null ? "" : req.getPathInfo();

        if ("/confirm".equals(path)) {
            @SuppressWarnings("unchecked")
            List<InvoiceDetailViewDTO> cart =
                (List<InvoiceDetailViewDTO>) req.getSession().getAttribute(SessionKeys.CURRENT_CART);

            req.setAttribute("lines", cart);

            float total = 0f;
            if (cart != null) {
                for (InvoiceDetailViewDTO d : cart) total += d.getLineTotal();
            }
            req.setAttribute("total", total);

            req.getRequestDispatcher("/ConfirmInvoice.jsp").forward(req, resp);
            return;
        }
       
        resp.sendError(404);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo() == null ? "" : req.getPathInfo();

         if ("/confirm".equals(path)) {
            HttpSession ses = req.getSession();

            Integer staffId = (Integer) ses.getAttribute(SessionKeys.CURRENT_STAFF_ID);
            Integer supId   = (Integer) ses.getAttribute(SessionKeys.CURRENT_SUPPLIER_ID);
            @SuppressWarnings("unchecked")
            List<InvoiceDetailViewDTO> cart =
                    (List<InvoiceDetailViewDTO>) ses.getAttribute(SessionKeys.CURRENT_CART);

            if (staffId == null || supId == null || cart == null || cart.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/ingredient/search");
                return;
            }

            try {
                // Tạo hóa đơn
                ImportInvoice iv = new ImportInvoice();
                Supplier sup = new Supplier(); sup.setId(supId);
                WarehouseStaff st = new WarehouseStaff(); st.setId(staffId);
                iv.setSup(sup);
                iv.setStaff(st);

                boolean ok = invoiceDAO.addInvoice(iv);
                if (!ok || iv.getId() <= 0) {
                    resp.sendRedirect(req.getContextPath() + "/ingredient/search");
                    return;
                }

                // Gắn invoiceId cho các dòng & lưu
                for (InvoiceDetailViewDTO d : cart) d.setInvoiceId(iv.getId());
                detailDAO.addDetail(cart);

                // Tính tổng
                float total = 0f;
                for (InvoiceDetailViewDTO d : cart) total += d.getLineTotal();
                ses.setAttribute(SessionKeys.LAST_INVOICE, iv);
                ses.setAttribute(SessionKeys.LAST_LINES, cart);
                ses.setAttribute(SessionKeys.LAST_TOTAL, total);


                ses.removeAttribute(SessionKeys.CURRENT_CART);
                ses.removeAttribute(SessionKeys.CURRENT_SUPPLIER_ID);
                req.setAttribute("done", true);
                req.setAttribute("invoice", iv);
                req.setAttribute("lines", cart);
                req.setAttribute("total", total);
                req.getRequestDispatcher("/ConfirmInvoice.jsp").forward(req, resp);
                return;

            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        if ("/done".equals(path)) {
            HttpSession ses = req.getSession(false);
            if (ses != null) {
                ses.removeAttribute(SessionKeys.CURRENT_CART);
                ses.removeAttribute(SessionKeys.CURRENT_SUPPLIER_ID);
            }
            resp.sendRedirect(req.getContextPath() + "/ingredient/search");
            return;
        }
        if ("/print".equals(path)) {
            HttpSession ses = req.getSession(false);
            if (ses == null) {
                resp.sendRedirect(req.getContextPath() + "/ingredient/search");
                return;
            }
            ImportInvoice iv = (ImportInvoice) ses.getAttribute(SessionKeys.LAST_INVOICE);
            @SuppressWarnings("unchecked")
            List<InvoiceDetailViewDTO> lines =
                    (List<InvoiceDetailViewDTO>) ses.getAttribute(SessionKeys.LAST_LINES);
            Float total = (Float) ses.getAttribute(SessionKeys.LAST_TOTAL);

            if (iv == null || lines == null || total == null) {
                resp.sendRedirect(req.getContextPath() + "/ingredient/search");
                return;
            }

            req.setAttribute("invoice", iv);
            req.setAttribute("lines", lines);
            req.setAttribute("total", total);
            req.getRequestDispatcher("/PrintInvoice.jsp").forward(req, resp);
            return;
        }
        if ("/cancel".equals(path)) {
            HttpSession ses = req.getSession();
            ses.removeAttribute(SessionKeys.CURRENT_CART);
            ses.removeAttribute(SessionKeys.CURRENT_SUPPLIER_ID);
            resp.sendRedirect(req.getContextPath() + "/supplier/search");
            return;
        }
        resp.sendError(404);
    }
}
