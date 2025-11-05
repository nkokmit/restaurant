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
            // đổi supplier => reset cart
            if (oldSup == null || oldSup != supId) {
                ses.setAttribute(SessionKeys.CURRENT_SUPPLIER_ID, supId);
                ses.removeAttribute(SessionKeys.CURRENT_CART);
                ses.removeAttribute("CART_INGSUP_IDS");
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

            int ingSupId = Integer.parseInt(req.getParameter("ingSupId"));
            float qty       = Float.parseFloat(req.getParameter("qty"));
            float unitPrice = Float.parseFloat(req.getParameter("price"));

            // Lấy tên nguyên liệu (để hiển thị)
            IngredientSupDAO.BasicIngSup info = ingSupDAO.getBasic(ingSupId);
            String ingName = (info != null ? info.name : "Nguyên liệu #" + ingSupId);

            @SuppressWarnings("unchecked")
            List<InvoiceDetailViewDTO> cart =
                    (List<InvoiceDetailViewDTO>) ses.getAttribute(SessionKeys.CURRENT_CART);
            if (cart == null) cart = new ArrayList<>();

            @SuppressWarnings("unchecked")
            List<Integer> cartIngSupIds = (List<Integer>) ses.getAttribute("CART_INGSUP_IDS");
            if (cartIngSupIds == null) cartIngSupIds = new ArrayList<>();

            // --- Gộp nếu đã có cùng ingSupId & cùng unitPrice ---
            final float EPS = 1e-6f;
            boolean merged = false;
            for (int i = 0; i < cart.size(); i++) {
                if (cartIngSupIds.get(i) == ingSupId) {
                    InvoiceDetailViewDTO cur = cart.get(i);
                    if (Math.abs(cur.getUnitPrice() - unitPrice) < EPS) {
                        // cùng đơn giá -> cộng dồn số lượng
                        float newQty = cur.getQty() + qty;
                        cur.setQty(newQty);
                        cur.setLineTotal(newQty * cur.getUnitPrice());
                        merged = true;
                        break;
                    }
                }
            }

            if (!merged) {
                // thêm dòng mới
                InvoiceDetailViewDTO line = new InvoiceDetailViewDTO();
                line.setDetailId(0); // tạm (chỉ để hiển thị)
                line.setIngredientName(ingName);
                line.setQty(qty);
                line.setUnitPrice(unitPrice);
                line.setLineTotal(qty * unitPrice);

                cart.add(line);
                cartIngSupIds.add(ingSupId);
            }

            ses.setAttribute(SessionKeys.CURRENT_CART, cart);
            ses.setAttribute("CART_INGSUP_IDS", cartIngSupIds);

            resp.sendRedirect(req.getContextPath() + "/ingredient/search");
            return;
        }

        
        if ("/removeLine".equals(path)) {
            int idx = Integer.parseInt(req.getParameter("idx"));
            @SuppressWarnings("unchecked")
            List<InvoiceDetailViewDTO> cart =
                    (List<InvoiceDetailViewDTO>) ses.getAttribute(SessionKeys.CURRENT_CART);
            @SuppressWarnings("unchecked")
            List<Integer> cartIngSupIds = (List<Integer>) ses.getAttribute("CART_INGSUP_IDS");
            if (cart != null && idx >= 0 && idx < cart.size()) {
                cart.remove(idx);
                if (cartIngSupIds != null && idx < cartIngSupIds.size()) {
                    cartIngSupIds.remove(idx);
                }
            }
            ses.setAttribute(SessionKeys.CURRENT_CART, cart);
            ses.setAttribute("CART_INGSUP_IDS", cartIngSupIds);
            resp.sendRedirect(req.getContextPath() + "/ingredient/search");
            return;
        }
        
        if ("/addNewIngredient".equals(path)) {
            Integer supId = (Integer) ses.getAttribute(SessionKeys.CURRENT_SUPPLIER_ID);
            if (supId == null) {
                resp.sendRedirect(req.getContextPath() + "/supplier/search");
                return;
            }
            String name = req.getParameter("name");
            String type = req.getParameter("type");
            String unit = req.getParameter("unit");
            float price = Float.parseFloat(req.getParameter("price"));
            try {
      
                ingSupDAO.addIngredientWithMapping( name, type, unit, price,supId);
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
