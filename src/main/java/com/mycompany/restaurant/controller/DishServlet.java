/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurant.controller;

import com.mycompany.restaurant.dao.DishDAO;
import com.mycompany.restaurant.models.Dish;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class DishServlet extends HttpServlet {
    private final DishDAO dao= new DishDAO();
    
    @Override
    protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo() == null ? "" : req.getPathInfo();
        switch (path) {
        case "/search":
            String q = req.getParameter("q");
            List<Dish> list = dao.searchDishByName(q);
            req.setAttribute("dishes", list);
            req.getRequestDispatcher("/SearchDish.jsp").forward(req, res);
            break;
        case "/view":
            int id = Integer.parseInt(req.getParameter("dishId"));
            Dish dish = dao.getById(id);
            req.setAttribute("dish", dish);
            req.getRequestDispatcher("/DishView.jsp").forward(req, res);
            break;
        default:
       
    }
    
    }
    
}
