package divingEquip.controller;

import java.io.IOException;
import java.util.List;

import divingEquip.dao.EquipmentDAO;
import divingEquip.model.Equipment;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/checkEquipmentAvailability")  // Map the servlet to the same URL pattern
public class EquipmentListingServlet extends HttpServlet {
    // doGet method handles the GET request sent from the form
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the parameters from the URL (query string)
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        // Logic to fetch equipment based on the date range
        List<Equipment> equipmentList = EquipmentDAO.getAvailableEquipment(startDate, endDate);

        // Pass the list of available equipment to the JSP
        request.setAttribute("equipmentList", equipmentList);

        // Forward the request to the EquipmentListing.jsp page
        RequestDispatcher dispatcher = request.getRequestDispatcher("EquipmentListing.jsp");
        dispatcher.forward(request, response);
    }
}
