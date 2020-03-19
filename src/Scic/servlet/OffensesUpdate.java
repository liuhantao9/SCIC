package Scic.servlet;

import Scic.model.*;
import Scic.model.Offenses.OffenseType;
import Scic.dal.*;

import javax.servlet.annotation.WebServlet;

import javax.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/offensesupdate")
public class OffensesUpdate extends HttpServlet {
	
	protected OffensesDao offensesDao;
	
	@Override
	public void init() throws ServletException {
		offensesDao = OffensesDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/OffensesUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        Integer offenseId = Integer.valueOf(req.getParameter("offenseid"));
        if (offenseId == null || offenseId == 0) {
            messages.put("success", "Please enter a valid offenseId.");
        } else { // Get the offense by ID
        	
            
            try {
            	Offenses offense = offensesDao.getOffensesByOffenseId(offenseId);
            	if (offense == null) {
            		messages.put("success", "Offense does not exist!");
            	} else {
            		String offenseDescription = req.getParameter("offensedescription");
            		if (offenseDescription == null || offenseDescription.equals("")) {
            			messages.put("success", "The description is empty");
            		} else {
            			offense = offensesDao.updateOffensesDescription(offense, offenseDescription);
            			messages.put("success", "Successfully update the description for offense: " + offenseId + ".\n" + "Offense detail: " + offense.toString());
            		}
            	}
            	req.setAttribute("offense", offense);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }
        
    	
        
        req.getRequestDispatcher("/OffensesUpdate.jsp").forward(req, resp);
    }
}
