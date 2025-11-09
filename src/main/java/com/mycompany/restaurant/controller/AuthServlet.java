package com.mycompany.restaurant.controller;

import com.mycompany.restaurant.dao.StaffDAO;
import com.mycompany.restaurant.models.WarehouseStaff;
import com.mycompany.restaurant.utils.SessionKeys;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    private final StaffDAO staffDAO = new StaffDAO();

    // === LOGIN ===
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String u = req.getParameter("u");
        String p = req.getParameter("p");

        WarehouseStaff st = staffDAO.findByUsernamePassword(u, p);
        if (st == null) {
            req.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
            req.getRequestDispatcher("/Login.jsp").forward(req, resp);
            return;
        }

        HttpSession ses = req.getSession(true);
        ses.setAttribute(SessionKeys.CURRENT_STAFF_ID, st.getId());
        ses.setAttribute("CURRENT_STAFF_NAME", st.getName());

        resp.sendRedirect(req.getContextPath() + "/Home.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // chỉ khi gọi đúng /logout mới xử lý, còn lại error
        if (!req.getServletPath().equals("/logout")) {
            resp.sendError(404);
            return;
        }

        HttpSession ses = req.getSession(false);
        if (ses != null) {
            ses.removeAttribute(SessionKeys.CURRENT_STAFF_ID);
            ses.removeAttribute("CURRENT_STAFF_NAME");
            ses.invalidate();
        }

        resp.sendRedirect(req.getContextPath() + "/Login.jsp");
    }
}
