/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vinhnq.tblaccount.TblAccountDTO;
import vinhnq.tblhistory.TblHistoryDAO;
import vinhnq.tblhistory.TblHistoryDTO;
import vinhnq.utilities.PagingModel;

/**
 *
 * @author Vinh
 */
@WebServlet(name = "SearchHistoryServlet", urlPatterns = {"/SearchHistoryServlet"})
public class SearchHistoryServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String HISTORY_PAGE = "history.jsp";

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
            String txtSubject = request.getParameter("txtSubject");
            String txtIndex = request.getParameter("txtIndex");
            boolean isValid = (txtHistoryId != null && txtSubject != null && txtIndex != null) ? true : false;
            if (isValid) {
                HttpSession session = request.getSession(false);
                String email = ((TblAccountDTO)session.getAttribute("ACCOUNT")).getEmail();
                int index = Integer.parseInt(txtIndex);
                TblHistoryDAO historyDAO = new TblHistoryDAO();
                int size = historyDAO.getDataForList(email,txtHistoryId, txtSubject,10,index);
                List<TblHistoryDTO> list = historyDAO.getList();
                request.setAttribute("LIST", list);
                int page = PagingModel.getNumberOfPage(size, 10);
                request.setAttribute("PAGE", page);
                url = HISTORY_PAGE;
            } else {
                request.setAttribute("MESSAGE", "Some input field was lost");
            }
        }catch(NumberFormatException e)
        {
            log("Number_SearchHistory: " + e.getMessage());
            request.setAttribute("MESSAGE", "Some field is not a number !!!");
        }
        catch (NamingException ex) {
            log("Naming_SearchHistory: " + ex.getMessage());
            request.setAttribute("MESSAGE", "System have error, try again !!!");
        } catch (SQLException ex) {
            log("SQL_SearchHistory: " + ex.getMessage());
            request.setAttribute("MESSAGE", "Data conflict, try again !!!");
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
