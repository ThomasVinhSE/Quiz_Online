/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vinhnq.tblquestion.TblQuestionDAO;

/**
 *
 * @author Vinh
 */
@WebServlet(name = "DeleteServlet", urlPatterns = {"/DeleteServlet"})
public class DeleteServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String SEARCH_SERVLET = "SearchServlet";
    private final String DELETE_PAGE = "delete.jsp";

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
        String txtQuestionId = request.getParameter("txtQuestionId");
        try {
            if (txtQuestionId != null) {
                int id = Integer.parseInt(txtQuestionId);
                TblQuestionDAO dao = new TblQuestionDAO();
                boolean result = dao.setInActiveQuestion(id);
                if (result) {
                    String isSearch = request.getParameter("isSearch");
                    if (isSearch == null) {
                        url = null;
                        response.sendRedirect(DELETE_PAGE);
                    } else {
                        url = SEARCH_SERVLET;
                    }
                } else {
                    request.setAttribute("MESSAGE", "Delete not finish, something conflict, try again !!!");
                }
            } else {
                request.setAttribute("MESSAGE", "QuestionId was lost, try input again !!!");
            }
        } catch (NumberFormatException e) {
            log("NumberFormat_Delete: " + e.getMessage());
            request.setAttribute("MESSAGE", "QuestionId must a number !!!");
        } catch (NamingException ex) {
            log("Naming_Delete: " + ex.getMessage());
            request.setAttribute("MESSAGE", "Something isn't running correctly !!!");
        } catch (SQLException ex) {
            log("SQL_Delete: " + ex.getMessage());
            request.setAttribute("MESSAGE", "Data conflict, try again !!!");
        } finally {

            if (url != null) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
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
