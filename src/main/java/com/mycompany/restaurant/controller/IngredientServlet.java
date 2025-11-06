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
            // đổi supplier => reset cart
            if (oldSup == null || oldSup != supId) {
                ses.setAttribute(SessionKeys.CURRENT_SUPPLIER_ID, supId);
                ses.removeAttribute(SessionKeys.CURRENT_CART);
                // bỏ hẳn list ids song song (không còn dùng)
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

            // Lấy tham số
            int ingSupId     = Integer.parseInt(req.getParameter("ingSupId"));
            float qty        = Float.parseFloat(req.getParameter("qty"));
            float unitPrice  = Float.parseFloat(req.getParameter("price"));

            // Validate cơ bản
            if (ingSupId <= 0 || qty <= 0f || unitPrice < 0f) {
                resp.sendRedirect(req.getContextPath() + "/ingredient/search");
                return;
            }

            // Lấy tên NL để hiển thị
            IngredientSupDAO.BasicIngSup info = ingSupDAO.getBasic(ingSupId);
            String ingName = (info != null ? info.name : "Nguyên liệu #" + ingSupId);

            @SuppressWarnings("unchecked")
            List<InvoiceDetailViewDTO> cart =
                    (List<InvoiceDetailViewDTO>) ses.getAttribute(SessionKeys.CURRENT_CART);
            if (cart == null) cart = new ArrayList<>();

            // Tìm xem đã có dòng trùng (ingredientSupId + unitPrice) để gộp
            InvoiceDetailViewDTO found = null;
            for (InvoiceDetailViewDTO d : cart) {
                if (d.getIngredientSupId() == ingSupId
                        && Math.abs(d.getUnitPrice() - unitPrice) < EPS) {
                    found = d;
                    break;
                }
            }

            if (found != null) {
                // Gộp số lượng
                float newQty = found.getQuantity() + qty;
                found.setQuantity(newQty);
                found.setLineTotal(newQty * found.getUnitPrice());
            } else {
                // Tạo dòng mới
                InvoiceDetailViewDTO line = new InvoiceDetailViewDTO();
                line.setDetailId(0);                  // id tạm cho view (nếu cần)
                line.setInvoiceId(0);                 // chưa xác nhận -> 0 (hoặc để khi confirm mới set)
                line.setIngredientSupId(ingSupId);    // set khóa thật
                line.setIngredientName(ingName);
                line.setQuantity(qty);                     // setter name theo DTO: setQty(...)
                line.setUnitPrice(unitPrice);
                line.setLineTotal(qty * unitPrice);
                cart.add(line);
            }

            // set lại session
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
            // không còn list song song
            ses.removeAttribute("CART_INGSUP_IDS");

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
            // giữ nguyên theo cấu trúc hiện có của bạn
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
