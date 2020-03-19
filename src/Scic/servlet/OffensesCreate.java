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

@WebServlet("/offensescreate")
public class OffensesCreate extends HttpServlet {
	
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
        req.getRequestDispatcher("/OffensesCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Integer offenseId = -1;
        OffenseType offenseType = Offenses.toOffenseType(req.getParameter("offensetype"));
    	String offenseDescription = req.getParameter("offensedescription");
        Date reportDate = new Date(), offenseStartDate = new Date(), offenseEndDate = new Date();
		try {
			reportDate = dateFormat.parse(req.getParameter("reportdate"));
			offenseStartDate = dateFormat.parse(req.getParameter("offensestartdate"));
	        offenseEndDate = dateFormat.parse(req.getParameter("offenseenddate"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Integer positionId = Integer.valueOf(req.getParameter("positionid"));
        String userName = req.getParameter("username");
        Boolean authenticated = Boolean.parseBoolean(req.getParameter("authenticated"));
        Boolean archived = Boolean.parseBoolean(req.getParameter("archived"));
        Integer severity = Integer.valueOf(req.getParameter("severity"));
    	
        try {
        	// Exercise: parse the input for StatusLevel.
        	Offenses offense = new Offenses(offenseId, offenseType, offenseDescription, reportDate, offenseStartDate, offenseEndDate, positionId, userName, authenticated, archived, severity);
        	offense = offensesDao.create(offense);
        	messages.put("success", "Successfully filed an offense ID: " + offense.getOffenseId());
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        req.getRequestDispatcher("/OffensesCreate.jsp").forward(req, resp);
    }
}
