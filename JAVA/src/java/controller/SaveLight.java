/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.d_mode;
import entity.devices;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author manga
 */
@WebServlet(name = "SaveMode", urlPatterns = {"/SaveMode"})
public class SaveLight extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");

        d_mode dm = (d_mode) session.get(d_mode.class, id);
        
        Criteria criteria = session.createCriteria(devices.class);
        criteria.add(Restrictions.eq("name", name));
        
        devices d = (devices) criteria.uniqueResult();
        d.setMode(dm);
        
        session.update(d);
        session.beginTransaction().commit();
    }

}
