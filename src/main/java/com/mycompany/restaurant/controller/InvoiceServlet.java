package com.mycompany.restaurant.controller;

import com.mycompany.restaurant.dao.ImportInvoiceDAO;
import com.mycompany.restaurant.dao.InvoiceDetailDAO;
import com.mycompany.restaurant.dto.InvoiceDetailViewDTO;
import com.mycompany.restaurant.models.ImportInvoice;
import com.mycompany.restaurant.utils.SessionKeys;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
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
            @SuppressWarnings("unchecked")
            List<Integer> cartIngSupIds =
                    (List<Integer>) ses.getAttribute("CART_INGSUP_IDS");

            if (staffId == null || supId == null || cart == null || cart.isEmpty()) {
                // thiếu dữ liệu -> quay lại
                resp.sendRedirect(req.getContextPath() + "/ingredient/search");
                return;
            }

            // 1) tạo hóa đơn (status=1)
            ImportInvoice iv = invoiceDAO.insertConfirmed(supId, staffId);

            // 2) insert detail
            detailDAO.insertMany(iv.getId(), cart, cartIngSupIds != null ? cartIngSupIds : new ArrayList<>());

            // 3) xóa session cart, supplier
            ses.removeAttribute(SessionKeys.CURRENT_CART);
            ses.removeAttribute("CART_INGSUP_IDS");
            ses.removeAttribute(SessionKeys.CURRENT_SUPPLIER_ID);

            // 4) chuyển sang trang in
            req.setAttribute("invoice", iv);
            req.setAttribute("lines", cart);
            float total = 0f;
            for (InvoiceDetailViewDTO d : cart) total += d.getLineTotal();
            req.setAttribute("total", total);
            req.getRequestDispatcher("/PrintInvoice.jsp").forward(req, resp);
            return;
        }

        // Cancel: clear cart & supplier
        if ("/cancel".equals(path)) {
            HttpSession ses = req.getSession();
            ses.removeAttribute(SessionKeys.CURRENT_CART);
            ses.removeAttribute("CART_INGSUP_IDS");
            ses.removeAttribute(SessionKeys.CURRENT_SUPPLIER_ID);
            resp.sendRedirect(req.getContextPath() + "/supplier/search");
            return;
        }

        resp.sendError(404);
    }
}
