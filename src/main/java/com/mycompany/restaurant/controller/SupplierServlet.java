/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.controller;

import com.mycompany.restaurant.dao.SupplierDAO; 
import com.mycompany.restaurant.models.Supplier;


import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class SupplierServlet extends HttpServlet {

    private final SupplierDAO dao = new SupplierDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo() == null ? "" : req.getPathInfo();

        if ("/addView".equals(path)) {
            req.getRequestDispatcher("/AddSupplierView.jsp").forward(req, resp);
            return;
        }

        // default: /search
        String q = req.getParameter("q");
        List<Supplier> list = dao.searchSupplierByName(q);
        req.setAttribute("suppliers", list);
        req.getRequestDispatcher("/SearchSupplier.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo() == null ? "" : req.getPathInfo();

        if ("/add".equals(path)) {
            Supplier s = new Supplier();
            s.setName(req.getParameter("name"));
            s.setAddr(req.getParameter("addr"));
            s.setTel(req.getParameter("tel"));
            s.setEmail(req.getParameter("email"));
            try {
                dao.addSupplierDAO(s);
            } catch (Exception e) {
                throw new ServletException(e);
            }
            String back = req.getContextPath() + "/supplier/search?q=" +
                    URLEncoder.encode(s.getName() == null ? "" : s.getName(), "UTF-8");
            resp.sendRedirect(back);
        } else {
            doGet(req, resp);
        }
    }
}