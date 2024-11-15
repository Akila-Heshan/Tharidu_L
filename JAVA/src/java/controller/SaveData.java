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
@WebServlet(name = "SaveData", urlPatterns = {"/SaveData"})
public class SaveData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String temperatute = req.getParameter("temperature");
        String humidity = req.getParameter("humidity");
        String light = req.getParameter("light");
        String fan = req.getParameter("fan");
        
        System.out.println(light +" l : f " +fan );
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Criteria criteria = session.createCriteria(sensor.class);
        criteria.add(Restrictions.eq("name", "temperature"));
        sensor temp_sensor = (sensor) criteria.uniqueResult();
        temp_sensor.setValue(Math.round(Float.parseFloat(temperatute)));
        session.update(temp_sensor);

        Criteria criteria2 = session.createCriteria(sensor.class);
        criteria2.add(Restrictions.eq("name", "humidity"));
        sensor hum_sensor = (sensor) criteria2.uniqueResult();
        hum_sensor.setValue(Math.round(Float.parseFloat(humidity)));
        session.update(hum_sensor);
        
        String ds;
        if(light.equals("ON")){
            System.out.println("ON LIGHT sdjhdfbasuhbf");
          ds = "ON";
        }else{
            ds = "OFF";
        }
        devices d1 = (devices) session.get(devices.class, 1);
        d1.setStatus(ds);
        session.update(d1);
        
        String ds2;
        if(fan.equals("ON")){
            ds2 = "ON";
        }else{
            ds2 = "OFF";
        }
        devices d2 = (devices) session.get(devices.class, 2);
        d2.setStatus(ds2);
        session.update(d2);
        
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("light", d1.getMode().getName());
        jsonObject.addProperty("fan", d2.getMode().getName());
        
        resp.setContentType("applicatiom/json");
        resp.getWriter().write(new Gson().toJson(jsonObject));
        
        session.beginTransaction().commit();
        session.close();

    }

}
