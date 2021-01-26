/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vinhnq.detaihistory.DetailHistoryDAO;
import vinhnq.detaihistory.DetailInfor;
import vinhnq.utilities.PagingModel;

/**
 *
 * @author Vinh
 */
@WebServlet(name = "ViewDetailServlet", urlPatterns = {"/ViewDetailServlet"})
public class ViewDetailServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String DETAIL_PAGE = "detail.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        try {
            String txtHistoryId = request.getParameter("txtHistoryId");
            String txtIndex = request.getParameter("txtIndex");
            if (txtHistoryId != null && txtIndex != null) {
                int index = Integer.parseInt(txtIndex);
                DetailHistoryDAO dao = new DetailHistoryDAO();
                int size = dao.getAllListDetail(txtHistoryId,10,index);
                List<DetailInfor> allListDetail = dao.getList();
                
                request.setAttribute("DETAIL", allListDetail);
                int page = PagingModel.getNumberOfPage(size, 10);
                request.setAttribute("PAGE", page);
                url = DETAIL_PAGE;
            } else {
                request.setAttribute("MESSAGE", "HistoryId was lost");
            }
        } catch (NamingException ex) {
            log("Naming_ViewDetail: " + ex.getMessage());
            request.setAttribute("MESSAGE", "System occured error,try again !!!");

        } catch (SQLException ex) {
            log("SQL_ViewDetail: " + ex.getMessage());
            request.setAttribute("MESSAGE", "Data conflict,try again !!!");
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
