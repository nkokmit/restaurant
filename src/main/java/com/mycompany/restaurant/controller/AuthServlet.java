// src/main/java/com/mycompany/restaurant/controller/AuthServlet.java
package com.mycompany.restaurant.controller;

import com.mycompany.restaurant.dao.StaffDAO;
import com.mycompany.restaurant.models.WarehouseStaff;
import com.mycompany.restaurant.utils.SessionKeys;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    private final StaffDAO staffDAO = new StaffDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String u = req.getParameter("u");
        String p = req.getParameter("p");

        WarehouseStaff st = staffDAO.findByUsernamePassword(u, p);
        if (st == null) {
            req.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        HttpSession ses = req.getSession(true);
        ses.setAttribute(SessionKeys.CURRENT_STAFF_ID, st.getId());
        // có thể lưu tên để hiển thị
        ses.setAttribute("CURRENT_STAFF_NAME", st.getName());

        resp.sendRedirect(req.getContextPath() + "/Import.jsp");
    }
}
