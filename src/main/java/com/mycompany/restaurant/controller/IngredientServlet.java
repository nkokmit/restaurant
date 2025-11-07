package com.mycompany.restaurant.controller;

import com.mycompany.restaurant.dao.IngredientSupDAO;
import com.mycompany.restaurant.dto.IngredientViewDTO;
import com.mycompany.restaurant.dto.InvoiceDetailViewDTO;
import com.mycompany.restaurant.utils.SessionKeys;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IngredientServlet extends HttpServlet {

    private static final float EPS = 1e-4f; // so sánh float cho unitPrice
    private final IngredientSupDAO ingSupDAO = new IngredientSupDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo() == null ? "" : req.getPathInfo();
        HttpSession ses = req.getSession();

        if ("/start".equals(path)) {
            int supId = Integer.parseInt(req.getParameter("supplierId"));
            Integer oldSup = (Integer) ses.getAttribute(SessionKeys.CURRENT_SUPPLIER_ID);
        
            if (oldSup == null || oldSup != supId) {
                ses.setAttribute(SessionKeys.CURRENT_SUPPLIER_ID, supId);
                ses.removeAttribute(SessionKeys.CURRENT_CART);
                
            }
            resp.sendRedirect(req.getContextPath() + "/ingredient/search");
            return;
        }

        if ("/addLine".equals(path)) {
            Integer supId = (Integer) ses.getAttribute(SessionKeys.CURRENT_SUPPLIER_ID);
            if (supId == null) {
                resp.sendRedirect(req.getContextPath() + "/supplier/search");
                return;
            }
            int ingSupId     = Integer.parseInt(req.getParameter("ingSupId"));
            float qty        = Float.parseFloat(req.getParameter("qty"));
            float unitPrice  = Float.parseFloat(req.getParameter("price"));
            String ingName = req.getParameter("ingName");
            if (ingSupId <= 0 || qty <= 0f || unitPrice < 0f) {
                resp.sendRedirect(req.getContextPath() + "/ingredient/search");
                return;
            }
         
            @SuppressWarnings("unchecked")
            List<InvoiceDetailViewDTO> cart =
                    (List<InvoiceDetailViewDTO>) ses.getAttribute(SessionKeys.CURRENT_CART);
            if (cart == null) cart = new ArrayList<>();

            InvoiceDetailViewDTO found = null;
            for (InvoiceDetailViewDTO d : cart) {
                if (d.getIngredientSupId() == ingSupId
                        && Math.abs(d.getUnitPrice() - unitPrice) < EPS) {
                    found = d;
                    break;
                }
            }
            if (found != null) {
                float newQty = found.getQuantity() + qty;
                found.setQuantity(newQty);
                found.setLineTotal(newQty * found.getUnitPrice());
            } else {
                InvoiceDetailViewDTO line = new InvoiceDetailViewDTO();
                line.setDetailId(0);                 
                line.setInvoiceId(0);                 
                line.setIngredientSupId(ingSupId); 
                line.setIngredientName(ingName);
                line.setQuantity(qty);                  
                line.setUnitPrice(unitPrice);
                line.setLineTotal(qty * unitPrice);
                cart.add(line);
            }

            ses.setAttribute(SessionKeys.CURRENT_CART, cart);
            resp.sendRedirect(req.getContextPath() + "/ingredient/search");
            return;
        }

        if ("/removeLine".equals(path)) {
            int idx = Integer.parseInt(req.getParameter("idx"));
            @SuppressWarnings("unchecked")
            List<InvoiceDetailViewDTO> cart =
                    (List<InvoiceDetailViewDTO>) ses.getAttribute(SessionKeys.CURRENT_CART);

            if (cart != null && idx >= 0 && idx < cart.size()) {
                cart.remove(idx);
                ses.setAttribute(SessionKeys.CURRENT_CART, cart);
            }
           
            resp.sendRedirect(req.getContextPath() + "/ingredient/search");
            return;
        }

        if ("/addNewIngredient".equals(path)) {
            IngredientViewDTO i = new IngredientViewDTO();
            Integer supId = (Integer) ses.getAttribute(SessionKeys.CURRENT_SUPPLIER_ID);
            if (supId == null) {
                resp.sendRedirect(req.getContextPath() + "/supplier/search");
                return;
            }
            i.setIngredientSupId(supId);
            i.setName(req.getParameter("name"));
            i.setType(req.getParameter("type"));
            i.setUnit(req.getParameter("unit"));
            i.setPrice(Float.parseFloat(req.getParameter("price")));
            try {
                ingSupDAO.addIngredientWithMapping(i);
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

        @SuppressWarnings("unchecked")
        List<InvoiceDetailViewDTO> cart =
                (List<InvoiceDetailViewDTO>) ses.getAttribute(SessionKeys.CURRENT_CART);
        req.setAttribute("lines", cart);

        req.getRequestDispatcher("/SearchIngredient.jsp").forward(req, resp);
    }
}
