/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.devices;
import entity.sensor;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author manga
 */
@WebServlet(name = "LoadHome", urlPatterns = {"/LoadHome"})
public class LoadHome extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        
        Criteria criteria1 = session.createCriteria(sensor.class);
        List<sensor> sensorList = criteria1.list();
        for(sensor s : sensorList){
            JsonObject temp = new JsonObject();
            temp.addProperty("value",  s.getValue());
            jsonObject.add(s.getName(), gson.toJsonTree(temp));
        }
        
        Criteria criteria2 = session.createCriteria(devices.class);
        List<devices> devicesList = criteria2.list();
        for(devices d : devicesList){
            JsonObject dev = new JsonObject();
            dev.addProperty("mode", d.getMode().getName());
            dev.addProperty("status", d.getStatus());
            jsonObject.add(d.getName(), gson.toJsonTree(dev));
        }
                
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(jsonObject));
    } 
}
