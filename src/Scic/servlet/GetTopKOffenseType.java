package Scic.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Scic.dal.*;
import Scic.model.*;

@WebServlet("/gettopkoffensetype")
public class GetTopKOffenseType extends HttpServlet {
	
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
        req.getRequestDispatcher("/GetTopKOffenseType.jsp").forward(req, resp);
    }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<String> topKOffenseType = new ArrayList<>();
        
        // Retrieve and validate name.
        // firstname is retrieved from the URL query string.
        Integer k = Integer.valueOf(req.getParameter("k"));
        if (k == null || k == 0) {
            messages.put("success", "Please enter a valid number.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
        		topKOffenseType = offensesDao.getTopKOffenseType(k);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Success!");
        }
        req.setAttribute("topKOffenseType", topKOffenseType);
        
        req.getRequestDispatcher("/GetTopKOffenseType.jsp").forward(req, resp);
	}
}
